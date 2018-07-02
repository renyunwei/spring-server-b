package com.ryw.server.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringServerBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringServerBApplication.class, args);
	}
}
