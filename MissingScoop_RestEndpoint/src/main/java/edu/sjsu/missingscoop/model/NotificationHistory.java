package edu.sjsu.missingscoop.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "NotificationHistory")
public class NotificationHistory {

	String id;
	String deviceId;
	String timestamp;

	public NotificationHistory() {
	}

	public NotificationHistory(String id, String deviceId, String timestamp) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.timestamp = timestamp;
	}

	@DynamoDBHashKey(attributeName = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBIndexHashKey(attributeName = "deviceId", globalSecondaryIndexName = "deviceId-timestamp-index")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@DynamoDBRangeKey(attributeName = "timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
