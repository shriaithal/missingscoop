package edu.sjsu.missingscoop.dao;

import edu.sjsu.missingscoop.response.DeviceWeightResponse;

public interface DeviceWeightDao {

	public DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId);

}
