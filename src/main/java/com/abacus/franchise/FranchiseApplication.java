package com.abacus.franchise;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FranchiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FranchiseApplication.class, args);
		System.out.println("WELCOME TO ABACUS FRANCHISE......!");
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}



}
