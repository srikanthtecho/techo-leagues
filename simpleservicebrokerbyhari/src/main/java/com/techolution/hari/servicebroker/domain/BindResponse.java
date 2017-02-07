package com.techolution.hari.servicebroker.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Lenovo
 *
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BindResponse {
	
	@JsonProperty("metadata")
	Credentials credentials;
	
	
	@JsonSerialize
	@JsonProperty("syslog_drain_url")
	private String sysLogDrainUrl;
	
	
	public Credentials getCredentials() {
		return credentials;
	}


	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}


	public String getSysLogDrainUrl() {
		return sysLogDrainUrl;
	}


	public void setSysLogDrainUrl(String sysLogDrainUrl) {
		this.sysLogDrainUrl = sysLogDrainUrl;
	}


	public String getRouteServiceURL() {
		return routeServiceURL;
	}


	public void setRouteServiceURL(String routeServiceURL) {
		this.routeServiceURL = routeServiceURL;
	}


	public Object getVolumeMounts() {
		return volumeMounts;
	}


	public void setVolumeMounts(Object volumeMounts) {
		this.volumeMounts = volumeMounts;
	}


	@JsonSerialize
	@JsonProperty("route_service_url")
	private String routeServiceURL;
	
	
	@JsonSerialize
	@JsonProperty("volume_mounts")
	private Object volumeMounts;

}
