package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@PropertySource(value = "file:${user.dir}/secret.properties", ignoreResourceNotFound = true)
public class Application {
	private static final String[] SERVICE_URLS = {
			"http://price-service-d:8082/product-prices",
			"http://product-service-d:8083/products",
			"http://stock-service-d:8084/stock"
	};

	@SneakyThrows
	public static void main(String[] args) {
		if (isDockerEnv()) {
			System.out.println("Waiting for services");
			waitForServices();
			Thread.sleep(10_000);
		}

		SpringApplication.run(Application.class, args);
	}

	private static boolean isDockerEnv() {
		try (var stream = Files.lines(Paths.get("/proc/1/cgroup"))) {
			return stream.anyMatch(line -> line.contains("/docker"));
		} catch (IOException e) {
			return false;
		}
	}

	@SneakyThrows
	private static void waitForServices() {
		long start = System.currentTimeMillis();
		do {
			boolean isReady = true;
			for (String serviceUrl : SERVICE_URLS) {
				boolean ready = isReady(serviceUrl);

				if (!ready) {
					isReady = false;
					break;
				}
			}

			if (isReady) {
				System.out.println("Response received for all services");
				System.out.println(System.currentTimeMillis() - start + " milliseconds after start");
				return;
			} else {
				//noinspection BusyWait
				Thread.sleep(1_000);
			}
		} while (System.currentTimeMillis() - start < 120_000);

		System.err.println("Failed establishing connection to docker services");
	}

	private static boolean isReady(String url) {
 		try {
			new URL(url)
					.openConnection()
					.getInputStream()
					.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Bean
	public String googleApiKey(@Value("${google.api.key}") String property) {
		return resolvePropertyValue(property);
	}

	@SneakyThrows
	private String resolvePropertyValue(String property) {
		if (property.startsWith("/run/secrets/")) {
			return Files.readString(Paths.get(property), Charset.defaultCharset());
		} else {
			return property;
		}
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
				.codecs(configurer -> configurer
						.defaultCodecs()
						.maxInMemorySize(16 * 1024 * 1024))
				.build());
	}
}
