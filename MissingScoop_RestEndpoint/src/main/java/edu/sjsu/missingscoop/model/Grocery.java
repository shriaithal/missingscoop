package edu.sjsu.missingscoop.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "GroceryList")
public class Grocery {
	private String userName;
	private List<String> groceryName;

	public Grocery() {
	}

	public Grocery(String userName, List<String> groceryName) {
		super();
		this.userName = userName;
		this.groceryName = groceryName;
	}

	@DynamoDBHashKey(attributeName = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@DynamoDBAttribute(attributeName = "groceryName")
	public List<String> getGroceryName() {
		return groceryName;
	}

	public void setGroceryName(List<String> groceryName) {
		this.groceryName = groceryName;
	}

}
