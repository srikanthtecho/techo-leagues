package com.techolution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techolution.service.CustomMapService;

@RestController
public class CustomMapController {

	@Autowired
	CustomMapService customMapService;

	@RequestMapping(value = "/CustomMap/{bindingId}/{key}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable("bindingId") String bindingId, @PathVariable("key") String key) {
		Object result = customMapService.get(bindingId, key);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("{}", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/CustomMap/{bindingId}/{key}", method = RequestMethod.POST)
	public ResponseEntity<String> put(@PathVariable("bindingId") String bindingId, @PathVariable("key") String key,
			@RequestBody String value) {
		customMapService.put(bindingId, key, value);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/CustomMap/{bindingId}/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("bindingId") String bindingId, @PathVariable("key") String key) {
		Object result = customMapService.get(bindingId, key);
		if (result != null) {
			customMapService.remove(bindingId, key);
			return new ResponseEntity<>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{}", HttpStatus.GONE);
		}
	}
}
