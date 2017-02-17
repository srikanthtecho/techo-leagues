package com.techolution.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class AvaillableInstanceService {
	private HashMap<String,ServiceInstance> all = new HashMap<String,ServiceInstance>();
	
	public boolean addService(ServiceInstance instance) {
		if(all.containsKey(instance.getServiceInstanceId()))
			return false;
		all.put(instance.getServiceInstanceId(), instance);
		return true;
	}
	
	public boolean updateService(ServiceInstance instance) {
		all.put(instance.getServiceInstanceId(), instance);
		return true;
	}
	
	public boolean deleteService(String instanceId) {
		if(!all.containsKey(instanceId))
			return false;
		all.remove(instanceId);
		return true;
	}
}
