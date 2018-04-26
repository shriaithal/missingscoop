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

import edu.sjsu.missingscoop.dao.GroceryListDao;
import edu.sjsu.missingscoop.model.GroceryList;

@Repository
public class GroceryListDaoImpl implements GroceryListDao {
	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void save(GroceryList groceryList) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		GroceryList dbGrocery = getGroceriesByUserName(groceryList.getUserName());

		if (null == dbGrocery) {
			dbGrocery = groceryList;
		} else {
			List<String> dbGroceryList = dbGrocery.getGrocery();
			dbGroceryList.addAll(groceryList.getGrocery());
			dbGrocery.setGrocery(dbGroceryList);
		}
		mapper.save(dbGrocery);
	}

	@Override
	public GroceryList getGroceriesByUserName(String userName) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":userName", new AttributeValue().withS(userName));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("userName = :userName").withExpressionAttributeValues(values);

		List<GroceryList> groceryList = mapper.scan(GroceryList.class, scanExpression);
		if (!CollectionUtils.isNullOrEmpty(groceryList)) {
			return groceryList.get(0);
		}
		return null;
	}

	@Override
	public GroceryList removeGrocery(String userName, String grocery) {
		GroceryList dbGrocery = getGroceriesByUserName(userName);
		if (null != dbGrocery) {
			List<String> groceryList = dbGrocery.getGrocery();
			groceryList.remove(grocery);
			dbGrocery.setGrocery(groceryList);
			AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
			DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
			mapper.save(dbGrocery);
		}
		return dbGrocery;
	}

}
