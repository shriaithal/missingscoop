package edu.sjsu.missingscoop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;
import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
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

	@Autowired
	DeviceProductMappingDao userGroceryMappingDao;

	@Override
	public UserResponse addUser(UserRequest request) {
		UserResponse response = new UserResponse(request.getUserName());
		response.setMessage(SUCCESS);
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@Override
	public void saveUserGroceryMapping(DeviceProductMappingRequest request) {
		DeviceProductMapping deviceProductMap = new DeviceProductMapping();
		deviceProductMap.setDeviceId(request.getDeviceId());
		deviceProductMap.setLabel(request.getLabel());
		deviceProductMap.setThreshold(request.getThreshold());
		deviceProductMap.setUserName(request.getUserName());

		userGroceryMappingDao.save(deviceProductMap);
	}
}
