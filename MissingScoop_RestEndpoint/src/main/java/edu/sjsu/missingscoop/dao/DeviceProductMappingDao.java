package edu.sjsu.missingscoop.dao;

import java.util.List;

import edu.sjsu.missingscoop.model.DeviceProductMapping;

public interface DeviceProductMappingDao {

	public void save(DeviceProductMapping deviceProductMapping);

	public List<DeviceProductMapping> getDeviceProductMappingByUserName(String userName);
}
