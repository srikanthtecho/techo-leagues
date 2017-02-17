package com.techolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HashservicebrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HashservicebrokerApplication.class, args);
	}
	
	/*@Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }*/
}
