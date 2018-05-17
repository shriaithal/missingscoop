package edu.sjsu.missingscoop.dao;

import edu.sjsu.missingscoop.model.NotificationHistory;

public interface NotificationHistoryDao {

	void save(NotificationHistory notification);

	boolean isNotificationSent(String deviceId, Long toTimestamp, Long fromTimestamp);

}
