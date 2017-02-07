package com.techolution.hari.servicebroker.domain;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A service plan available for a ServiceDefinition
 *
 * @author sgreenberg@pivotal.io
 * @author Scott Frederick
 */

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public Boolean getBindable() {
		return bindable;
	}

	public void setBindable(Boolean bindable) {
		this.bindable = bindable;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	/**
	 * An identifier used to correlate this plan in future requests to the catalog. This must be unique within
	 * a Cloud Foundry deployment. Using a GUID is recommended.
	 */
	@NotEmpty
	@JsonSerialize
	@JsonProperty("id")
	private String id;

	/**
	 * A CLI-friendly name of the plan that will appear in the catalog. The value should be all lowercase,
	 * with no spaces.
	 */
	@NotEmpty
	@JsonSerialize
	@JsonProperty("name")
	private String name;

	/**
	 * A user-friendly short description of the plan that will appear in the catalog.
	 */
	@NotEmpty
	@JsonSerialize
	@JsonProperty("description")
	private String description;

	/**
	 * A map of metadata to further describe a service plan.
	 */
	
	@JsonProperty("metadata")
	private Map<String, Object> metadata;

	/**
	 * Indicates whether the service with this plan can be bound to applications. This is an optional field. If the
	 * value is <code>null</code>, the field will be omitted from the serialized JSON.
	 */
	
	@JsonSerialize
	@JsonProperty("bindable")
	@JsonInclude(Include.NON_NULL)
	private Boolean bindable;

	/**
	 * Indicates whether the plan can be limited by the non_basic_services_allowed field in a Cloud Foundry Quota.
	 */
	@JsonSerialize
	@JsonProperty("free")
	private boolean free = true;

	public Plan() {
	}

	public Plan(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Plan(String id, String name, String description, Map<String, Object> metadata) {
		this(id, name, description);
		this.metadata = metadata;
	}

	public Plan(String id, String name, String description, Map<String, Object> metadata, boolean free) {
		this(id, name, description, metadata);
		this.free = free;
	}

	public Plan(String id, String name, String description, Map<String, Object> metadata, boolean free, boolean bindable) {
		this(id, name, description, metadata, free);
		this.bindable = bindable;
	}

	public Boolean isBindable() {
		return bindable;
	}
}
