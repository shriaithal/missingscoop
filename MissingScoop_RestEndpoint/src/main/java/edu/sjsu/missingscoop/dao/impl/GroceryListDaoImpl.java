package edu.sjsu.missingscoop.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.GroceryListDao;
import edu.sjsu.missingscoop.model.Grocery;

public class GroceryListDaoImpl implements GroceryListDao {
	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void save(Grocery grocery) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(grocery);
	}
	
	@Override
	public Grocery getGroceriesByUserName(String userName) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		
		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":userName", new AttributeValue().withS(userName));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("userName = :userName").withExpressionAttributeValues(values);

		List<Grocery> groceryList = mapper.scan(Grocery.class, scanExpression);
		if(!CollectionUtils.isNullOrEmpty(groceryList)) {
			return groceryList.get(0);
		}
		return null;
	}
	
	public Boolean delete (Grocery grocery){
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		if(grocery !=null){
			mapper.delete(grocery);
		} else{
			return false;
		}
		return true;
		
	}
	public void deleteGrocery(String userName, String groceryName){
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		
		try {  
			 /*DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withTableName("GroceryList").withKey(hashKey, rangeKey)
	         DeleteItemResult outcome = dynamoDB.deleteItem(deleteItemSpec);  */
	         		 
		
	}catch (Exception e ){
		
	}
	}

}
