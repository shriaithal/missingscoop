package edu.sjsu.missingscoop.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.amazonaws.util.StringUtils;

import edu.sjsu.missingscoop.dao.DeviceWeightDao;
import edu.sjsu.missingscoop.dao.NotificationHistoryDao;
import edu.sjsu.missingscoop.dao.UserTokenDao;
import edu.sjsu.missingscoop.helper.AndroidPushNotificationsService;
import edu.sjsu.missingscoop.model.Notification;
import edu.sjsu.missingscoop.model.NotificationHistory;
import edu.sjsu.missingscoop.model.UserDevicesMap;
import edu.sjsu.missingscoop.response.DeviceWeightResponse;

@Configuration
@EnableScheduling
public class NotificationServiceImpl {

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	@Autowired
	NutritionServiceImpl nutritionServiceImpl;

	@Autowired
	DeviceWeightDao deviceWeightDao;

	@Autowired
	UserTokenDao userTokenDao;

	@Autowired
	NotificationHistoryDao notificationHistoryDao;

	@Scheduled(cron = "0 0/2 * * * ?")
	public void sendNotification() {

		Map<String, List<UserDevicesMap>> userDevicesMap = nutritionServiceImpl.findAllDevices();

		if (null == userDevicesMap || userDevicesMap.isEmpty()) {
			return;
		}

		Calendar currentDate = Calendar.getInstance();

		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 55);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);

		Long toTimestamp = currentDate.getTimeInMillis();

		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 1);
		currentDate.set(Calendar.MILLISECOND, 0);

		Long fromTimestamp = currentDate.getTimeInMillis();

		for (String userName : userDevicesMap.keySet()) {
			String tokenId = userTokenDao.getToken(userName);
			if (StringUtils.isNullOrEmpty(tokenId)) {
				continue;
			}
			for (UserDevicesMap device : userDevicesMap.get(userName)) {

				boolean notificationSent = notificationHistoryDao.isNotificationSent(device.getDeviceId(), toTimestamp,
						fromTimestamp);

				if (notificationSent) {
					continue;
				}
				DeviceWeightResponse deviceWeightResp = deviceWeightDao.getDeviceWeightByDeviceId(device.getDeviceId());
				if (null != deviceWeightResp && deviceWeightResp.getCurrentWeight() != 0 && deviceWeightResp.getCurrentWeight() <= device.getThreshold()) {

					Notification notification = new Notification(tokenId, device.getProductName());
					notifyUser(notification);

					NotificationHistory history = new NotificationHistory(UUID.randomUUID().toString(),
							device.getDeviceId(), String.valueOf(System.currentTimeMillis()));
					notificationHistoryDao.save(history);
				}
			}
		}
	}

	private void notifyUser(Notification notification) {
		try {
			JSONObject body = new JSONObject();
			body.put("to", notification.getTokenId());
			body.put("priority", "high");

			JSONObject notificationToBeSent = new JSONObject();
			notificationToBeSent.put("body",
					"Product " + notification.getProductName() + " weight is below threshold.");
			notificationToBeSent.put("title", "Missing Scoop Alert");

			body.put("notification", notificationToBeSent);

			HttpEntity<String> request = new HttpEntity<>(body.toString());
			androidPushNotificationsService.send(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
