package edu.sjsu.missingscoop.dao;

import java.util.List;

import edu.sjsu.missingscoop.model.TestIotData;
import edu.sjsu.missingscoop.response.DeviceWeightResponse;

public interface DeviceWeightDao {

	public DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId);

	public List<Double> getWeight(String deviceId, Long fromTimestamp, Long toTimestamp, boolean sort);

	public void save(TestIotData data);

}
