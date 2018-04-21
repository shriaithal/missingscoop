package edu.sjsu.missingscoop.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.sjsu.missingscoop.request.UserRequest;
import edu.sjsu.missingscoop.response.UserResponse;
import edu.sjsu.missingscoop.service.MissingScoopService;

/**
 * Business service class for all APIs
 * 
 * @author Anushri Srinath Aithal
 *
 */
@Service
public class MissingScoopServiceImpl implements MissingScoopService {

	private final String SUCCESS = "SUCCESS";
	private final String ERROR = "ERROR";

	@Override
	public UserResponse addUser(UserRequest request) {
		UserResponse response = new UserResponse(request.getUserName());
		response.setMessage(SUCCESS);
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

}
