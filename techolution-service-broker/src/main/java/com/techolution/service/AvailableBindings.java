package com.techolution.service;
	import java.util.HashMap;
	import org.springframework.stereotype.Service;

	@Service
	public class AvailableBindings {
		private HashMap<String,ServiceInstanceBinding> all = new HashMap<String,ServiceInstanceBinding>();
		
		public boolean addBinding(ServiceInstanceBinding instance) {
			if(all.containsKey(instance.getServiceInstanceId()))
				return false;
			all.put(instance.getServiceInstanceId(), instance);
			return true;
		}
		
		public boolean updateBinding(ServiceInstanceBinding instance) {
			all.put(instance.getServiceInstanceId(), instance);
			return true;
		}
		
		public boolean deleteBinding(String instanceId) {
			if(!all.containsKey(instanceId))
				return false;
			all.remove(instanceId);
			return true;
		}
		
		public boolean bindingExists(String key) {
			return all.containsKey(key);
		}
}
