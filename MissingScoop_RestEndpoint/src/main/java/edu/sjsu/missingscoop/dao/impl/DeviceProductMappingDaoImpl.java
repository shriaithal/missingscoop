package edu.sjsu.missingscoop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

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

	@Override
	public List<DeviceProductMapping> getDeviceProductMappingByUserName(String userName) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":userName", new AttributeValue().withS(userName));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("userName = :userName").withExpressionAttributeValues(values);

		List<DeviceProductMapping> deviceProductMappingList = mapper.scan(DeviceProductMapping.class, scanExpression);
		return deviceProductMappingList;
	}

	@Override
	public List<DeviceProductMapping> findAllDevices() {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		List<DeviceProductMapping> deviceProductMap = mapper.scan(DeviceProductMapping.class,
				new DynamoDBScanExpression());
		return deviceProductMap;
	}

}
