package com.techolution.hari.servicebroker.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardClient {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	/**
	 * The client ID of the dashboard OAuth2 client that the service intends to use. The name must be unique within a
	 * Cloud Foundry UAA deployment. If the name is already in use, Cloud Foundry will return an error to the
	 * operator when the service is registered.
	 */
	@JsonSerialize
	@JsonProperty("id")
	private String id;

	/**
	 * The client secret for the dashboard OAuth2 client.
	 */
	@JsonSerialize
	@JsonProperty("secret")
	private String secret;

	/**
	 * A domain for the service dashboard that will be whitelisted by the UAA to enable dashboard SSO.
	 */
	@JsonSerialize
	@JsonProperty("redirect_uri")
	private String redirectUri;

	public DashboardClient() {
	}

	public DashboardClient(String id, String secret, String redirectUri) {
		this.id = id;
		this.secret = secret;
		this.redirectUri = redirectUri;
	}
}
