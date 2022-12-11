package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
		"com.services",
		"com.controllers"
})
@EntityScan({
		"com.data"
})
@SpringBootApplication
public class QrCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeApplication.class, args);
	}

}
