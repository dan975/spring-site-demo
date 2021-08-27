package product.service;

import lombok.Data;
import lombok.SneakyThrows;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;
import product.service.model.product.ProductImage;
import product.service.model.product.ProductImageRepository;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@PropertySource(value = "file:${user.dir}/dbProperties.properties", ignoreResourceNotFound = true)
public class Application {
	private static final Object[][] PRODUCT_IMAGE_UPDATES = {
			{1L, "/images/product1image1.png", "red", true},
			{1L, "/images/product1image2.png", "black", false},
			{1L, "/images/product1image3.png", "green", false},
			{2L, "/images/product2image1.png", "black", true},
			{3L, "/images/product3image1.png", "purple", false},
			{3L, "/images/product3image2.png", "white", true},
			{4L, "/images/product4image1.png", "red", true},
			{4L, "/images/product4image2.png", "green", false},
			{5L, "/images/product5image1.png", "black", false},
			{5L, "/images/product5image2.png", "green", true},
			{6L, "/images/product6image1.png", "green", false},
			{6L, "/images/product6image2.png", "white", false},
			{6L, "/images/product6image3.png", "black", true},
			{6L, "/images/product6image4.png", "red", false}
	};

	@Value("${db.username}")
	private String dbUsername;

	@Value("${db.password}")
	private String dbPassword;

	@Value("${db.host}")
	private String dbHost;

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@SneakyThrows
	@EventListener(ContextRefreshedEvent.class)
	public void addImages() {
		var productImageRepository = context.getBean(ProductImageRepository.class);

		var productImages = new LinkedList<ProductImage>();
		for (int i = 0; i < PRODUCT_IMAGE_UPDATES.length; i++) {
			Object[] productImageDetails = PRODUCT_IMAGE_UPDATES[i];

			var productImage = new ProductImage(i + 1L,
					(long) productImageDetails[0],
					Files.readAllBytes(new File((String) productImageDetails[1]).toPath()),
					(String) productImageDetails[2],
					(boolean) productImageDetails[3]);
			productImages.add(productImage);
		}

		productImageRepository.saveAll(productImages);
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
	public DataSource getMySQLDataSource(Properties dbCredentialResolver) {
		String dbUrl = String.format("jdbc:mysql://%s/products", dbHost);

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
