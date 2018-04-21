package edu.sjsu.missingscoop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.missingscoop.request.UserRequest;
import edu.sjsu.missingscoop.response.UserResponse;
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

	/**
	 * API to add user
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/add/user")
	@ResponseBody
	public UserResponse addUser(@RequestBody UserRequest request) {
		return service.addUser(request);
	}

}
