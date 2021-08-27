package price.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.util.Properties;

@SpringBootApplication
@PropertySource(value = "file:${user.dir}/dbProperties.properties", ignoreResourceNotFound = true)
public class Application {

	@Value("${db.username}")
	private String dbUsername;

	@Value("${db.password}")
	private String dbPassword;

	@Value("${db.host}")
	private String dbHost;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Profile("h2")
	public DataSource getH2DataSource() {
		var dataSourceBuilder = DataSourceBuilder.create();

		dataSourceBuilder.driverClassName("org.h2.Driver");
		dataSourceBuilder.url("jdbc:h2:mem:testdb");
		dataSourceBuilder.username("sa");
		dataSourceBuilder.password("password");

		return dataSourceBuilder.build();
	}

	@SneakyThrows
	@Bean
	@Profile("mysql")
	public DataSource getDataSource(Properties dbCredentialResolver) {
		String dbUrl = String.format("jdbc:mysql://%s/product_prices", dbHost);

		long start = System.currentTimeMillis();
		do {
			try {
				var connection = DriverManager.getConnection(dbUrl,
						dbCredentialResolver.getProperty("dbUsername"),
						dbCredentialResolver.getProperty("dbPassword"));
				connection.close();
				break;
			} catch (Throwable e) {
				System.err.println("Database server host connection unsuccessful");

				//noinspection BusyWait
				Thread.sleep(5_000);
			}
		} while (System.currentTimeMillis() - start < 30_000);

		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.url(dbUrl)
				.username(dbCredentialResolver.getProperty("dbUsername"))
				.password(dbCredentialResolver.getProperty("dbPassword"))
				.build();
	}

	@SneakyThrows
	@Bean
	@Profile("mysql")
	public Properties dbCredentialResolver() {
		var res = new Properties();
		res.put("dbUsername", resolvePropertyValue(dbUsername));
		res.put("dbPassword", resolvePropertyValue(dbPassword));

		return res;
	}

	@SneakyThrows
	private String resolvePropertyValue(String property) {
		if (property.startsWith("/run/secrets/")) {
			return Files.readString(Paths.get(property), Charset.defaultCharset());
		} else {
			return property;
		}
	}
}
