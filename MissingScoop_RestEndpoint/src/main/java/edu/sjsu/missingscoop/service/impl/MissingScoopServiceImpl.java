package edu.sjsu.missingscoop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;
import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;
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

	@Autowired
	DeviceProductMappingDao userGroceryMappingDao;

	@Override
	public DeviceProductMappingResponse saveDeviceProductMapping(DeviceProductMappingRequest request) {
		DeviceProductMapping deviceProductMap = new DeviceProductMapping();
		deviceProductMap.setDeviceId(request.getDeviceId());
		deviceProductMap.setLabel(request.getLabel());
		deviceProductMap.setThreshold(request.getThreshold());
		deviceProductMap.setUserName(request.getUserName());

		userGroceryMappingDao.save(deviceProductMap);

		DeviceProductMappingResponse response = new DeviceProductMappingResponse(deviceProductMap.getDeviceId(),
				deviceProductMap.getLabel(), deviceProductMap.getThreshold());
		response.setMessage(SUCCESS);
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}
}
