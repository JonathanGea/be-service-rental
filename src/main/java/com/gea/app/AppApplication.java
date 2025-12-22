package com.gea.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
	
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@ComponentScan(basePackages = "com.gea.app")
@EnableJpaRepositories(basePackages = "com.gea.app")
@EntityScan(basePackages = "com.gea.app")
public class AppApplication {

    public static void main(String[] args) {
        // Muat dotenv
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Penting: Set variabel lingkungan sebagai properti sistem
        dotenv.entries().forEach(e -> 
            System.setProperty(e.getKey(), e.getValue())
        );

        String appName = dotenv.get("SPRING_APP_NAME");
        System.out.println("SPRING_APP_NAME: " + appName);

        // Buat aplikasi Spring Boot dengan properti tambahan
        SpringApplication application = new SpringApplication(AppApplication.class);

        // Jalankan aplikasi
        application.run(args);
    }
}