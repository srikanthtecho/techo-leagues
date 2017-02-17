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
public class RestTemplateService {
	@Autowired
	ClientProperties clientProperties;
	String uri = "http://techolution-service-broker.apps.techolution.com/TechoHash/}";
	String username = "dinesh";
	String password = "dinesh";
	String bindingId = "techohashclinet";
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers;

	public void init() {
		 ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		    username=clientProperties.getUsername();
	        password=clientProperties.getPassword();
	        bindingId=clientProperties.getBindingid();
	        uri=clientProperties.getUri()+bindingId+"/";
		    headers = HashclientApplication.createHeaders(username, password);
	}
	public void put(String key,String value) {
		String requestJson = "{\"value\":"+value+"}";
		HttpEntity<String> entity = new HttpEntity<String>(value,headers);
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("bindingId", bindingId);
//	    params.put("key", key);
		System.out.println("line 46 : "+uri+key);
	    restTemplate.postForObject(uri+key, entity, String.class);
//		restTemplate.put(uri, entity,params);
	}
	
	public String get(String key) {
		 ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		HttpEntity<String> entity = new HttpEntity<String>(headers);
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("bindingId", bindingId);
//	    params.put("key", key);
		return restTemplate.exchange(uri+key ,
			    HttpMethod.GET, entity, ptr).getBody(); 
	}
	
	public String delete(String key) {
		 ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		HttpEntity<String> entity = new HttpEntity<String>(headers);
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("bindingId", bindingId);
//	    params.put("key", key);
	    return restTemplate.exchange(uri+key ,
			    HttpMethod.GET, entity, ptr).getBody();  
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
