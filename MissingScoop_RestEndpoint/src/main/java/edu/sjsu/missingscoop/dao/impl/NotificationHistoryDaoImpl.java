package edu.sjsu.missingscoop.dao.impl;

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

import edu.sjsu.missingscoop.dao.NotificationHistoryDao;
import edu.sjsu.missingscoop.model.NotificationHistory;

@Repository
public class NotificationHistoryDaoImpl implements NotificationHistoryDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void save(NotificationHistory notification) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(notification);
	}

	@Override
	public boolean isNotificationSent(String deviceId, Long toTimestamp, Long fromTimestamp) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#deviceId", "deviceId");
		expressionAttributesNames.put("#timestamp", "timestamp");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":deviceIdValue", new AttributeValue().withS(deviceId));
		expressionAttributeValues.put(":to", new AttributeValue().withS(String.valueOf(toTimestamp)));
		expressionAttributeValues.put(":from", new AttributeValue().withS(String.valueOf(fromTimestamp)));

		QueryRequest queryRequest = new QueryRequest().withTableName("NotificationHistory")
				.withIndexName("deviceId-timestamp-index")
				.withKeyConditionExpression("#deviceId = :deviceIdValue and #timestamp BETWEEN :from AND :to ")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(true).withLimit(1);

		QueryResult queryResult = dynamoDB.query(queryRequest);
		List<Map<String, AttributeValue>> results = queryResult.getItems();

		if (null != results && !results.isEmpty()) {
			return true;
		}

		return false;
	}
}
