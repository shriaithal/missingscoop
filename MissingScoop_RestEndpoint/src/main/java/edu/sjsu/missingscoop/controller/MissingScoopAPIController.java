package edu.sjsu.missingscoop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.response.DeviceProductListResponse;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;
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

}
