package edu.sjsu.missingscoop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

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

}
