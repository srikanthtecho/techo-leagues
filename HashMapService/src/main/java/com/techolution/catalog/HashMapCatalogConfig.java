package com.techolution.catalog;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashMapCatalogConfig {

	@Bean
	public Catalog catalog() {
		return new Catalog(Collections.singletonList(new ServiceDefinition(UUID.randomUUID().toString(),
				"hashmap-service-broker-fahad", "Custom Service Broker for HashMap", true, false,
				Collections.singletonList(
						new Plan("hash-map-plan", "default", "This is a default hash map plan.", getPlanMetadata())),
				Arrays.asList("Java", "none"), getServiceDefinitionMetadata(), null, null)));
	}

	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> sdMetadata = new HashMap<>();
		sdMetadata.put("displayName", "CustomMapService");
		sdMetadata.put("imageUrl", "");
		sdMetadata.put("longDescription", "Custom HashMap Service");
		sdMetadata.put("providerDisplayName", "Techolution");
		sdMetadata.put("documentationUrl", "https://github.com/Techolution/techo-leagues/blob/master/README.md");
		sdMetadata.put("supportUrl", "https://github.com/Techolution/techo-leagues/blob/master/README.md");
		return sdMetadata;
	}

	private Map<String, Object> getPlanMetadata() {
		Map<String, Object> planMetadata = new HashMap<>();
		planMetadata.put("costs", getCosts());
		planMetadata.put("bullets", getBullets());
		return planMetadata;
	}

	private List<Map<String, Object>> getCosts() {
		Map<String, Object> costsMap = new HashMap<>();
		Map<String, Object> amount = new HashMap<>();
		amount.put("inr", 0.0);
		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");
		return Collections.singletonList(costsMap);
	}

	private List<String> getBullets() {
		return Arrays.asList("HashMap Service Broker. Custom Created", "100 MB Storage", "10 concurrent connections");
	}

}
