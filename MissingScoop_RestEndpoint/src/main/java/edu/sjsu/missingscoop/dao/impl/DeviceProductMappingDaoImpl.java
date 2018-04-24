package edu.sjsu.missingscoop.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;

@Repository
public class DeviceProductMappingDaoImpl implements DeviceProductMappingDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void save(DeviceProductMapping deviceProductMapping) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(deviceProductMapping);
	}

}
