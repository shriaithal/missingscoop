package edu.sjsu.missingscoop.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

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

}
