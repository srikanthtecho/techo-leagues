package com.techolution.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.techolution.map.CustomMap;

@Component
public class CustomMapService {
	private CustomMap<String, CustomMap<Object, Object>> hashMaps = new CustomMap<String, CustomMap<Object, Object>>();

	@PostConstruct
	public void init() {
		hashMaps.put("custommapservice", new CustomMap<Object, Object>());
	}

	public void create(String mapId) {
		hashMaps.put(mapId, new CustomMap<Object, Object>());
	}

	public void remove(String mapId) {
		hashMaps.remove(mapId);
	}

	public void put(String id, Object key, Object value) {
		CustomMap<Object, Object> map = hashMaps.get(id);
		map.put(key, value);
	}

	public Object get(String id, Object key) {
		CustomMap<Object, Object> mapInstance = hashMaps.get(id);
		Object val = mapInstance.get(key);
		return val;
	}

	public void remove(String id, Object key) {
		CustomMap<Object, Object> mapInstance = hashMaps.get(id);
		mapInstance.remove(key);
	}
}
