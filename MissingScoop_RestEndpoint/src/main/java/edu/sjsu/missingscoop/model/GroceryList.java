package edu.sjsu.missingscoop.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName = "GroceryList")
public class GroceryList {
	private String userName;
	private List<String> grocery;

	public GroceryList() {
	}

	public GroceryList(String userName, List<String> grocery) {
		super();
		this.userName = userName;
		this.grocery = grocery;
	}

	@DynamoDBHashKey(attributeName = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@DynamoDBTyped(DynamoDBAttributeType.L)
	@DynamoDBAttribute(attributeName = "grocery")
	public List<String> getGrocery() {
		return grocery;
	}

	public void setGrocery(List<String> grocery) {
		this.grocery = grocery;
	}

}
