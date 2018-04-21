package edu.sjsu.missingscoop.service;

import edu.sjsu.missingscoop.request.UserRequest;
import edu.sjsu.missingscoop.response.UserResponse;

/**
 * Interface for all services
 * @author Anushri Srinath Aithal
 *
 */
public interface MissingScoopService {

	/**
	 * add user
	 * @param request
	 * @return
	 */
	UserResponse addUser(UserRequest request);

}
