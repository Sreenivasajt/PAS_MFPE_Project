package com.cts.pas.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QuotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotesApplication.class, args);
		System.out.println("Welcome To Quotes");
		
	}

}
