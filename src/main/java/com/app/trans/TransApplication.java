package com.app.trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@OpenAPIDefinition
public class TransApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(TransApplication.class, args);
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TransApplication.class);
	}
}
