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
import edu.sjsu.missingscoop.model.DeviceWeight;
import edu.sjsu.missingscoop.response.DeviceWeightResponse;

@Repository
public class DeviceWeightDaoImpl implements DeviceWeightDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId) {

		DeviceWeightResponse finalResponse = new DeviceWeightResponse();
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#deviceId", "deviceId");
		// expressionAttributesNames.put("#timestamp","timestamp");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":deviceIdValue", new AttributeValue().withS(deviceId));
		// expressionAttributeValues.put(":to",new
		// AttributeValue().withS("1524806300"));
		// expressionAttributeValues.put(":from",new
		// AttributeValue().withS("1524805852"));

		QueryRequest queryRequest = new QueryRequest().withTableName("TestIotData")
				.withIndexName("deviceId-timestamp-index")
				// .withKeyConditionExpression("#deviceId = :deviceIdValue and #timestamp
				// BETWEEN :from AND :to ")
				.withKeyConditionExpression("#deviceId = :deviceIdValue")
				.withExpressionAttributeNames(expressionAttributesNames)
				// .withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(false).withLimit(1);
				.withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(false);

		QueryResult queryResult = dynamoDB.query(queryRequest);
		List<Map<String, AttributeValue>> results = queryResult.getItems();

		if (results.isEmpty())
			return null;

		// Current Weight
		double currentWeight = Double.parseDouble((results.get(0)).get("weight").getS());
		finalResponse.setCurrentWeight(currentWeight);

		double summ = 0;
		int i = 0;
		double entry, preventry, temp;
		int estimatedCompletionDays = 0;

		// Consumption Rate
		for (i = 0; i < results.size() - 1; i++) {

			entry = Double.parseDouble((results.get(i)).get("weight").getS());
			preventry = Double.parseDouble((results.get(i + 1)).get("weight").getS());
			if (entry < 0)
				entry = 0;
			if (preventry < 0)
				preventry = 0;
			temp = entry - preventry;
			if (temp > 10)
				break;
			if (temp > 0)
				continue;
			summ += preventry - entry;
		}
		if (summ > 0) {
			int lastdate = Integer.parseInt((results.get(0)).get("timestamp").getS());
			int refilledDate = Integer.parseInt((results.get(i)).get("timestamp").getS());
			int divisor = 1;
			if (refilledDate - lastdate < 86400)
				divisor = 1;
			else
				divisor = (refilledDate - lastdate) / 86400;
			double consumptionrate = summ / divisor;

			// Estimated Completion Days
			double estimatedCompletionDays_temp =  currentWeight / consumptionrate;
			
			if(estimatedCompletionDays_temp>0 && estimatedCompletionDays_temp<1)
				estimatedCompletionDays = 1;

			finalResponse.setConsumptionRate(consumptionrate);
			finalResponse.setEstimatedCompletion(Math.round(estimatedCompletionDays));
		}

		System.out.println(finalResponse);

		return finalResponse;

	}

	@Override
	public List<Double> getWeight(String deviceId, Long fromTimestamp, Long toTimestamp, boolean sort) {
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
				.withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(sort);

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
