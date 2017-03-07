package com.techolution;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@SpringBootApplication
@EnableConfigurationProperties({ ClientProperties.class })
public class HashMapClientApplication {

	@Autowired
	HashService hashService;

	public static void main(String[] args) {
		SpringApplication.run(HashMapClientApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			hashService.init();

			hashService.put("key", "2");
			String value = hashService.get("key");
			System.out.println(value);

			value = hashService.delete("key");
			System.out.println("");
			System.out.println(value + "&&");
		};
	}

	@SuppressWarnings("serial")
	public static HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
}
