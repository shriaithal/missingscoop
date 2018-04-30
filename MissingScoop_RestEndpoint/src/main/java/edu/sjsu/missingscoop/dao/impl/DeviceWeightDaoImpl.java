package edu.sjsu.missingscoop.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.DeviceWeightDao;
import edu.sjsu.missingscoop.model.TestIotData;
import edu.sjsu.missingscoop.response.DeviceWeightResponse;

@Repository
public class DeviceWeightDaoImpl implements DeviceWeightDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId) {
		// TODO Auto-generated method stub

		DeviceWeightResponse data = new DeviceWeightResponse();
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#deviceId", "deviceId");
		expressionAttributesNames.put("#timestamp", "timestamp");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":deviceIdValue", new AttributeValue().withS("001"));
		expressionAttributeValues.put(":to", new AttributeValue().withS("1524806300"));
		expressionAttributeValues.put(":from", new AttributeValue().withS("1524805852"));

		QueryRequest queryRequest = new QueryRequest().withTableName("TestIotData")
				.withIndexName("deviceId-timestamp-index")
				.withKeyConditionExpression("#deviceId = :deviceIdValue and #timestamp BETWEEN :from AND :to ")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(false).withLimit(1);

		QueryResult queryResult = dynamoDB.query(queryRequest);
		List<Map<String, AttributeValue>> results = queryResult.getItems();
		for (Map<String, AttributeValue> result : results) {
			System.out.println(result);
			data.setId(result.get("id").getS());
			data.setWeight(result.get("weight").getS());
			data.setDeviceId(result.get("deviceId").getS());
			data.setTimestamp(result.get("timestamp").getS());
			System.out.println(data);
		}
		return data;
	}

	@Override
	public List<Double> getWeight(String deviceId, Long fromTimestamp, Long toTimestamp) {
		List<Double> returnList = new ArrayList<>();
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#deviceId", "deviceId");
		expressionAttributesNames.put("#timestamp", "timestamp");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":deviceIdValue", new AttributeValue().withS(deviceId));
		expressionAttributeValues.put(":to", new AttributeValue().withS(String.valueOf(toTimestamp)));
		expressionAttributeValues.put(":from", new AttributeValue().withS(String.valueOf(fromTimestamp)));

		QueryRequest queryRequest = new QueryRequest().withTableName("TestIotData")
				.withIndexName("deviceId-timestamp-index")
				.withKeyConditionExpression("#deviceId = :deviceIdValue and #timestamp BETWEEN :from AND :to ")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(true);

		QueryResult queryResult = dynamoDB.query(queryRequest);
		List<Map<String, AttributeValue>> results = queryResult.getItems();

		if (CollectionUtils.isNullOrEmpty(results)) {
			return null;
		}
		for (Map<String, AttributeValue> result : results) {
			returnList.add(Double.parseDouble(result.get("weight").getS()));
		}
		return returnList;
	}

	@Override
	public void save(TestIotData data) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(data);
		
	}
}
