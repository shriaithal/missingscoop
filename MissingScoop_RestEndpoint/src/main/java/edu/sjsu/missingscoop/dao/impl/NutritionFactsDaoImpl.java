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
import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.NutritionFactsDao;
import edu.sjsu.missingscoop.model.NutritionFacts;

@Repository
public class NutritionFactsDaoImpl implements NutritionFactsDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public List<NutritionFacts> getAllFromNutritionFacts() {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		List<NutritionFacts> nutritionFacts = mapper.scan(NutritionFacts.class, new DynamoDBScanExpression());
		return nutritionFacts;
	}

	@Override
	public NutritionFacts getNutritionFacts(String productName) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":productName", new AttributeValue().withS(productName));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("productName = :productName").withExpressionAttributeValues(values);
		List<NutritionFacts> nutritionFacts = mapper.scan(NutritionFacts.class, scanExpression);
		if (!CollectionUtils.isNullOrEmpty(nutritionFacts)) {
			return nutritionFacts.get(0);
		}
		return null;
	}

}
