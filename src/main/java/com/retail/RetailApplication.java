package com.retail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableFeignClients
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class RetailApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailApplication.class, args);
	}

}
