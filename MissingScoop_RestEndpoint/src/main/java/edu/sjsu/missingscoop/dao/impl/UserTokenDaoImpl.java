package edu.sjsu.missingscoop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import edu.sjsu.missingscoop.dao.UserTokenDao;
import edu.sjsu.missingscoop.model.UserToken;

@Repository
public class UserTokenDaoImpl implements UserTokenDao {

	@Autowired
	DynamoDbClient dynamodbClient;

	@Override
	public void saveUserToken(UserToken userToken) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(userToken);
	}

	@Override
	public String getToken(String userName) {
		AmazonDynamoDB dynamoDB = dynamodbClient.getDynamoDB();
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":userName", new AttributeValue().withS(userName));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("userName = :userName").withExpressionAttributeValues(values);

		List<UserToken> userToken = mapper.scan(UserToken.class, scanExpression);
		if (CollectionUtils.isEmpty(userToken)) {
			return null;
		}
		return userToken.get(0).getTokenId();
	}

}
