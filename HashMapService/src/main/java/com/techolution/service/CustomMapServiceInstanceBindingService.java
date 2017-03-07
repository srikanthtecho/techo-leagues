package com.techolution.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
@Service
public class CustomMapServiceInstanceBindingService implements ServiceInstanceBindingService {

	@Autowired
	public CustomMapService customMapService;

	@Autowired
	public AvailableBindings availableBindings;

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(
			CreateServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();
		String id = serviceInstanceId + bindingId;

		System.out.println("serviceInstanceId---->>"+serviceInstanceId);
		System.out.println("bindingId---->>"+bindingId);
		System.out.println("ID---->>"+id);
		if (availableBindings.bindingExists(id)) {
			throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
		}

		Map<String, Object> credentials = new HashMap<String, Object>();
		credentials.put("uri", "custom-service-broker.cfapps.io/CustomMap/");
		credentials.put("username", "fahad");
		credentials.put("password", "fahad");
		credentials.put("bindingId", id);

		ServiceInstanceBindings binding = new ServiceInstanceBindings(bindingId, serviceInstanceId, credentials, null,
				request.getBoundAppGuid());
		availableBindings.addBinding(binding);
		customMapService.create(id);
		return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();
		String id = serviceInstanceId + bindingId;
		if (!availableBindings.bindingExists(serviceInstanceId + bindingId)) {
			throw new ServiceInstanceBindingDoesNotExistException(bindingId);
		}
		availableBindings.deleteBinding(id);
		customMapService.remove(id);
	}

}
