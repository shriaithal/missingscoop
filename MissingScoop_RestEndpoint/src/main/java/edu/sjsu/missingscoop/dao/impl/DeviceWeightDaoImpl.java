package edu.sjsu.missingscoop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import edu.sjsu.missingscoop.dao.DeviceWeightDao;
import edu.sjsu.missingscoop.model.DeviceWeight;
import edu.sjsu.missingscoop.response.DeviceWeightResponse;

@Repository
public class DeviceWeightDaoImpl implements DeviceWeightDao{
	
	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId) {
		// TODO Auto-generated method stub
		
		DeviceWeightResponse data = new DeviceWeightResponse();
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		 		Map<String, String> expressionAttributesNames = new HashMap<>();
		 		expressionAttributesNames.put("#deviceId", "deviceId");
		 		expressionAttributesNames.put("#timestamp","timestamp");
		 
		 		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		 		expressionAttributeValues.put(":deviceIdValue", new AttributeValue().withS("001"));
		 		expressionAttributeValues.put(":to",new AttributeValue().withS("1524806300"));  
		         expressionAttributeValues.put(":from",new AttributeValue().withS("1524805852"));
		 
		 		QueryRequest queryRequest = new QueryRequest().withTableName("TestIotData").withIndexName("deviceId-timestamp-index")
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
}
