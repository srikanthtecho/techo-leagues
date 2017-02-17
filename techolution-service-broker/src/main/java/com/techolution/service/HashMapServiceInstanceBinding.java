package com.techolution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

@Service

public class HashMapServiceInstanceBinding implements ServiceInstanceBindingService {
	@Autowired
	public TechoMapService techoMapService;
	@Autowired
	public AvailableBindings availableBindings;
	/*@Autowired
	Cloud cloud;*/

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(
			CreateServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();
		String id=serviceInstanceId+bindingId;
		if (availableBindings.bindingExists(id) ) {
			throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
		}
		
		Map<String, Object> credentials = new HashMap<String,Object>();
		credentials.put("uri",  myUri() + "/TechoHash/"+id+"/");
		credentials.put("username", "dinesh");
		credentials.put("password", "dinesh");
		credentials.put("bindingId", id);
		ServiceInstanceBinding	binding = new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());
		availableBindings.addBinding(binding);
		techoMapService.create(id);
		return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();
		String id = serviceInstanceId+bindingId;
		if (!availableBindings.bindingExists(serviceInstanceId+bindingId)) {
			throw new ServiceInstanceBindingDoesNotExistException(bindingId);
		}
		availableBindings.deleteBinding(id);
		techoMapService.delete(id);
	}

	private String myUri() {
		return "http://techolution-service-broker.apps.techolution.com";
		/*if(cloud==null)
			return "localhost:8080";
		ApplicationInstanceInfo applicationInstanceInfo = cloud.getApplicationInstanceInfo();
		List<Object> uris = (List<Object>) applicationInstanceInfo.getProperties().get("uris");
		return uris.get(0).toString();*/
	}
}
