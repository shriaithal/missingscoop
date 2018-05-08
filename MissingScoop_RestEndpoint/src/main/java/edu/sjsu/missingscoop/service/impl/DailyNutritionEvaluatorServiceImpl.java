package edu.sjsu.missingscoop.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import edu.sjsu.missingscoop.dao.UserNutritionDao;
import edu.sjsu.missingscoop.model.UserDevicesMap;
import edu.sjsu.missingscoop.model.UserNutritionMap;
import edu.sjsu.missingscoop.service.DailyNutritionEvaluatorService;

@Configuration
@EnableScheduling
public class DailyNutritionEvaluatorServiceImpl implements DailyNutritionEvaluatorService {

	@Autowired
	NutritionServiceImpl nutritionServiceImpl;

	@Autowired
	UserNutritionDao userNutritionDao;

	@Scheduled(cron = "0 55 23 * * ?")
	@Override
	public void evaluateDailyNutrition() {
		Map<String, List<UserDevicesMap>> userDevicesMap = nutritionServiceImpl.findAllDevices();

		if (null == userDevicesMap || userDevicesMap.isEmpty()) {
			return;
		}

		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 55);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		String timestamp = String.valueOf(currentDate.getTimeInMillis());

		for (String userName : userDevicesMap.keySet()) {
			UserNutritionMap userNutritionMap = nutritionServiceImpl.calculateDailyNutrition(userName,
					userDevicesMap.get(userName));
			userNutritionMap.setId(UUID.randomUUID().toString());
			userNutritionMap.setTimestamp(timestamp);
			userNutritionDao.save(userNutritionMap);
		}
	}
}
