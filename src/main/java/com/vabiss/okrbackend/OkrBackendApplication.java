package com.vabiss.okrbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OkrBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkrBackendApplication.class, args);
	}

}
