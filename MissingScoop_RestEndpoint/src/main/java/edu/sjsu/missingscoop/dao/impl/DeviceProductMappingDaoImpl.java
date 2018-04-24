package edu.sjsu.missingscoop.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;

@Repository
public class DeviceProductMappingDaoImpl implements DeviceProductMappingDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void save(DeviceProductMapping part) {
		// TODO Auto-generated method stub

	}

}
