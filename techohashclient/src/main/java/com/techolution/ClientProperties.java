package com.techolution;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hashbroker", ignoreUnknownFields = false)
public class ClientProperties {
	public String uri;
	public String password;
	public String username;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBindingid() {
		return bindingid;
	}
	public void setBindingid(String bindingid) {
		this.bindingid = bindingid;
	}
	public String bindingid;

}
