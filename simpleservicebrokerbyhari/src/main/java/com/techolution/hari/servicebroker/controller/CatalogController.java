package com.techolution.hari.servicebroker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techolution.hari.servicebroker.domain.BindResponse;
import com.techolution.hari.servicebroker.domain.Catalog;
import com.techolution.hari.servicebroker.domain.Plan;
import com.techolution.hari.servicebroker.domain.ProvisionResponse;
import com.techolution.hari.servicebroker.domain.ServiceDefinition;

@RestController
public class CatalogController {
	
	@RequestMapping(value = {
			"/v2/catalog",
			"{cfInstanceId}/v2/catalog"
	}, method = RequestMethod.GET)
	public Catalog getCatalog() {
		//log.debug("getCatalog()");
		return createDummyCatalog();
	}
	
	private Catalog createDummyCatalog(){
		
		Plan freePlan=new Plan("haritest001","HariTest001","TestPlan For Map Service Provided By Hari",null,true,true);
		List<Plan> planList=new ArrayList<Plan>();
		planList.add(freePlan);
		List<String> requires=new ArrayList<String>(1);
		requires.add("route_forwarding");
		List<String> tags=new ArrayList<String>(1);
		tags.add("key-value");
		ServiceDefinition definition=new ServiceDefinition("haritesthashmapservice001", "haritesthashmapservice", "Test Service", true, false, planList, tags, null, requires, null);
		List<ServiceDefinition> serviceDefinitionList=new ArrayList<ServiceDefinition>();
		serviceDefinitionList.add(definition);
		Catalog dummyCatalog=new Catalog(serviceDefinitionList);
		return dummyCatalog;
		
	}
	
	@RequestMapping(value = {
			"//v2/service_instances/{instance_id}"
	}, method = RequestMethod.PUT)
	public ProvisionResponse doProvision() {
		//log.debug("getCatalog()");
		ProvisionResponse provisionResponse=new ProvisionResponse();
		provisionResponse.setStatus("success");
		return provisionResponse;
		
	}
	
	@RequestMapping(value = {
			"/v2/service_instances/:{instance_id}/service_bindings/:{binding_id}"
	}, method = RequestMethod.PUT)
	public BindResponse doBind(@PathVariable String instanceId,@PathVariable String bindingId) {
		//log.debug("getCatalog()");
		BindResponse bindResponse=new BindResponse();
		bindResponse.setRouteServiceURL("https://route-service-example-nourishable-instiller.cfapps.io");
		return bindResponse;
	}

}
