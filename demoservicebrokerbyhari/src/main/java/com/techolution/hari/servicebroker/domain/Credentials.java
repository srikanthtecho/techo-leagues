package com.techolution.hari.servicebroker.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {
	
	
	@JsonSerialize
	@JsonProperty("uri")
	private String uri;
	
	@JsonSerialize
	@JsonProperty("username")
	private String username;
	
	@JsonSerialize
	@JsonProperty("password")
	private String password;
	
	@JsonSerialize
	@JsonProperty("host")
	private String host;
	
	@JsonSerialize
	@JsonProperty("port")
	private String port;
	
	@JsonSerialize
	@JsonProperty("instancename")
	private String instancename;

}
