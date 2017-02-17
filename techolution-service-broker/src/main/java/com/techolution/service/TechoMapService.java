package com.techolution.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.techolution.TecholutionMap;

@Component
public class TechoMapService {
	 private Map<String, TecholutionMap<Object, Object>> hashMaps = new TecholutionMap<String, TecholutionMap<Object, Object>>();
	 @PostConstruct
	 public void init() {
		 //default is for backup
		 hashMaps.put("techohashclinet", new TecholutionMap<Object,Object>());
	 }
	 public void create(String id) {
	        hashMaps.put(id, new TecholutionMap<Object, Object>());
	    }

	    public void delete(String id) {
	        hashMaps.remove(id);
	    }

	    public void put(String id, Object key, Object value) {
	        Map<Object, Object> mapInstance = hashMaps.get(id);
	        mapInstance.put(key, value);
	    }

	    public Object get(String id, Object key) {
	        Map<Object, Object> mapInstance = hashMaps.get(id);
	        return mapInstance.get(key);
	    }

	    public void delete(String id, Object key) {
	        Map<Object, Object> mapInstance = hashMaps.get(id);
	        mapInstance.remove(key);
	    }
	 
}
