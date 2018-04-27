package edu.sjsu.missingscoop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.request.GroceryListRequest;
import edu.sjsu.missingscoop.response.DeviceProductListResponse;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;

import edu.sjsu.missingscoop.response.GrocerListResponse;

import edu.sjsu.missingscoop.response.DeviceWeightResponse;

import edu.sjsu.missingscoop.service.MissingScoopService;

/**
 * API Controller for all rest APIs
 * 
 * @author Anushri Srinath Aithal
 *
 */
@RestController
public class MissingScoopAPIController {

	@Autowired
	MissingScoopService service;

	@PostMapping("/map/device/product")
	@ResponseBody
	public DeviceProductMappingResponse saveDeviceProductMapping(@RequestBody DeviceProductMappingRequest request) {
		return service.saveDeviceProductMapping(request);
	}

	@GetMapping("fetch/device/product")
	public DeviceProductListResponse getDeviceProductMapping(String userName) {
		return service.getDeviceProductMappingByUserName(userName);
	}

	@PostMapping("/add/grocery")
	@ResponseBody
	public GrocerListResponse addGrocery(@RequestBody GroceryListRequest request) {
		return service.saveGroceryList(request);
	}

	@GetMapping("/grocery")
	public GrocerListResponse getGroceryList(String userName) {
		return service.getGroceryListByUserName(userName);
	}

	@PostMapping("/remove/grocery")
	@ResponseBody
	public GrocerListResponse removeGrocery(@RequestBody GroceryListRequest request) {
		return service.removeGrocery(request);
	}

	@GetMapping("fetch/device/weight")
	public DeviceWeightResponse getDeviceWeight(String deviceId) {
		return service.getDeviceWeightByDeviceId(deviceId);

	}

	/*
	 * @GetMapping("/test") public void test() { testDao.test(); }
	 */

}
