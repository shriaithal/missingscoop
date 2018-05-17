package edu.sjsu.missingscoop.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "UserToken")
public class UserToken {

	String userName;
	String tokenId;
	
	public UserToken() {}
	
	public UserToken(String userName, String tokenId) {
		super();
		this.userName = userName;
		this.tokenId = tokenId;
	}

	@DynamoDBHashKey(attributeName = "userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@DynamoDBAttribute(attributeName = "tokenId")
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
	
}
