package com.techolution.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.cloud.servicebroker.model.BrokerApiVersion;
import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashMapServiceConfig {
	@Bean
	public Catalog catalog() {
		return new Catalog(Collections.singletonList(new ServiceDefinition("techohashmap-service-broker1feb22017",
				"techohashmap1feb22017", "A simple techohashmap service broker implementation", true, true,
				Collections.singletonList(new Plan("hashmap-plan2105952", "default",
						"This is a default hashmap plan.",null,true)),
				null, null, Arrays.asList("syslog_drain"), null)));
	}

	@Bean
	public BrokerApiVersion brokerApiVersion() {
		return new BrokerApiVersion();
	}
}
