package com.techolution;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HashService {
	@Autowired
	ClientProperties clientProperties;

	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers;

	String uri = "http://custom-service-broker.cfapps.io/CustomMap/";
	String username = "fahad";
	String password = "fahad";
	String bindingId = "custommapservice";

	public void init() {
		 ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		    username=clientProperties.getUsername();
	        password=clientProperties.getPassword();
	        bindingId=clientProperties.getBindingId();
	        uri=clientProperties.getUri()+bindingId+"/";
		    headers = HashMapClientApplication.createHeaders(username, password);
	}
	
	public void put(String key, String value) {
		HttpEntity<String> entity = new HttpEntity<String>(value, headers);
		restTemplate.postForObject(uri + key, entity, String.class);
	}

	public String get(String key) {
		ParameterizedTypeReference<String> ptr = new ParameterizedTypeReference<String>() {
		};
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(uri + key, HttpMethod.GET, entity, ptr).getBody();
	}

	public String delete(String key) {
		ParameterizedTypeReference<String> ptr = new ParameterizedTypeReference<String>() {
		};
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(uri + key, HttpMethod.GET, entity, ptr).getBody();
	}

	public static HttpHeaders createHeaders(String username, String password) {
		HttpHeaders headers = new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}

