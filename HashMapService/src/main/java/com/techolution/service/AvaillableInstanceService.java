package com.techolution.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
@Service
public class AvaillableInstanceService {
	private HashMap<String, ServiceInstances> all = new HashMap<String, ServiceInstances>();

	public boolean addService(ServiceInstances instance) {
		if (all.containsKey(instance.getServiceInstanceId()))
			return false;
		all.put(instance.getServiceInstanceId(), instance);
		return true;
	}

	public boolean updateService(ServiceInstances instance) {
		all.put(instance.getServiceInstanceId(), instance);
		return true;
	}

	public boolean deleteService(String instanceId) {
		if (!all.containsKey(instanceId))
			return false;
		all.remove(instanceId);
		return true;
	}
}
