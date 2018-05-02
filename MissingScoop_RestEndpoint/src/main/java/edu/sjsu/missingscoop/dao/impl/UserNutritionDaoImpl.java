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

import edu.sjsu.missingscoop.dao.UserNutritionDao;
import edu.sjsu.missingscoop.model.UserNutritionMap;

@Repository
public class UserNutritionDaoImpl implements UserNutritionDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void save(UserNutritionMap userNutritionMap) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(userNutritionMap);

	}

	@Override
	public List<UserNutritionMap> getNutritionHistory(String userName, Long fromTimestamp, Long toTimestamp) {
		List<UserNutritionMap> returnList = new ArrayList<>();
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#userName", "userName");
		expressionAttributesNames.put("#timestamp", "timestamp");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":userNameValue", new AttributeValue().withS(userName));
		expressionAttributeValues.put(":to", new AttributeValue().withS(String.valueOf(toTimestamp)));
		expressionAttributeValues.put(":from", new AttributeValue().withS(String.valueOf(fromTimestamp)));

		QueryRequest queryRequest = new QueryRequest().withTableName("UserNutritionMap")
				.withIndexName("userName-timestamp-index")
				.withKeyConditionExpression("#userName = :userNameValue and #timestamp BETWEEN :from AND :to ")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues).withScanIndexForward(true);

		QueryResult queryResult = dynamoDB.query(queryRequest);
		List<Map<String, AttributeValue>> results = queryResult.getItems();

		if (CollectionUtils.isNullOrEmpty(results)) {
			return null;
		}
		for (Map<String, AttributeValue> result : results) {
			UserNutritionMap userNutritionMap = new UserNutritionMap();
			userNutritionMap.setCarbohydrate(Double.parseDouble(result.get("carbohydrate").getN()));
			userNutritionMap.setProtein(Double.parseDouble(result.get("protein").getN()));
			userNutritionMap.setFat(Double.parseDouble(result.get("fat").getN()));
			userNutritionMap.setFiber(Double.parseDouble(result.get("fiber").getN()));
			userNutritionMap.setSugar(Double.parseDouble(result.get("sugar").getN()));
			userNutritionMap.setSodium(Double.parseDouble(result.get("sodium").getN()));
			userNutritionMap.setUserName(result.get("userName").getS());
			userNutritionMap.setTimestamp(result.get("timestamp").getS());
			returnList.add(userNutritionMap);
		}
		return returnList;
	}

}
