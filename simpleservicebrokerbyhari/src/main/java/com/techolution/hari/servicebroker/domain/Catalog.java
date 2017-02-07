package com.techolution.hari.servicebroker.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The catalog of services offered by the service broker.
 * 
 * @author sgreenberg@pivotal.io
 * @author Scott Frederick
 */

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Catalog {

	/**
	 * A list of service offerings provided by the service broker.
	 */
	@NotEmpty

	@JsonProperty("services")
	private final List<ServiceDefinition> serviceDefinitions;

	public List<ServiceDefinition> getServiceDefinitions() {
		return serviceDefinitions;
	}

	public Catalog() {
		this.serviceDefinitions = new ArrayList<>();
	}

	public Catalog(List<ServiceDefinition> serviceDefinitions) {
		this.serviceDefinitions = serviceDefinitions;
	}
}

