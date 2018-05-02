package edu.sjsu.missingscoop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.dao.DeviceWeightDao;
import edu.sjsu.missingscoop.dao.NutritionFactsDao;
import edu.sjsu.missingscoop.dao.UserNutritionDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;
import edu.sjsu.missingscoop.model.Nutrition;
import edu.sjsu.missingscoop.model.NutritionFacts;
import edu.sjsu.missingscoop.model.UserDevicesMap;
import edu.sjsu.missingscoop.model.UserNutritionMap;
import edu.sjsu.missingscoop.response.NutritionFactsListResponse;
import edu.sjsu.missingscoop.response.NutritionFactsResponse;
import edu.sjsu.missingscoop.response.NutritionHistoryResponse;
import edu.sjsu.missingscoop.response.UserNutritionResponse;
import edu.sjsu.missingscoop.service.NutritionService;

@Service
public class NutritionServiceImpl implements NutritionService {

	@Autowired
	NutritionFactsDao nutritionFactsDao;

	@Autowired
	DeviceProductMappingDao deviceProductDao;

	@Autowired
	DeviceWeightDao deviceWeightDao;

	@Autowired
	UserNutritionDao userNutritionDao;

	@Override
	public NutritionFactsListResponse getAllNutritionFacts() {
		NutritionFactsListResponse response = new NutritionFactsListResponse();
		List<NutritionFactsResponse> nutritionFacts = new ArrayList<>();

		List<NutritionFacts> dbNutritionFacts = nutritionFactsDao.getAllFromNutritionFacts();
		if (!CollectionUtils.isNullOrEmpty(dbNutritionFacts)) {
			for (NutritionFacts record : dbNutritionFacts) {
				NutritionFactsResponse nutritionFactResponse = new NutritionFactsResponse(record.getId(),
						record.getProductName(), record.getCarbohydrate(), record.getFat(), record.getProtein(),
						record.getFiber(), record.getSodium(), record.getSugar());

				nutritionFacts.add(nutritionFactResponse);
			}
		}
		response.setMessage("SUCCESS");
		response.setStatus(HttpStatus.OK.toString());
		response.setNutritionFacts(nutritionFacts);
		return response;
	}

	@Override
	public Map<String, List<UserDevicesMap>> findAllDevices() {
		List<DeviceProductMapping> deviceProductMap = deviceProductDao.findAllDevices();

		if (CollectionUtils.isNullOrEmpty(deviceProductMap)) {
			return null;
		}
		Map<String, List<UserDevicesMap>> userDevicesMap = new HashMap<>();

		for (DeviceProductMapping deviceProduct : deviceProductMap) {
			UserDevicesMap userDevice = new UserDevicesMap();
			userDevice.setDeviceId(deviceProduct.getDeviceId());
			userDevice.setProductName(deviceProduct.getLabel());

			List<UserDevicesMap> userDevices = new ArrayList<>();
			if (userDevicesMap.containsKey(deviceProduct.getUserName())) {
				userDevices = userDevicesMap.get(deviceProduct.getUserName());
			}
			userDevices.add(userDevice);
			userDevicesMap.put(deviceProduct.getUserName(), userDevices);
		}

		return userDevicesMap;
	}

	@Override
	public UserNutritionMap calculateDailyNutrition(String userName, List<UserDevicesMap> userDevicesMap) {
		UserNutritionMap userNutritionMap = new UserNutritionMap();
		userNutritionMap.setUserName(userName);

		double carbohydrate = 0;
		double fat = 0;
		double protein = 0;
		double fiber = 0;
		double sodium = 0;
		double sugar = 0;

		for (UserDevicesMap userDevice : userDevicesMap) {
			double totalConsumption = calculateTotalConsumption(userDevice.getDeviceId());
			Nutrition nutrition = calculateProductNutrition(totalConsumption, userDevice.getProductName());
			carbohydrate += nutrition.getCarbohydrate();
			fat += nutrition.getFat();
			protein += nutrition.getProtein();
			fiber += nutrition.getFiber();
			sodium += nutrition.getSodium();
			sugar += nutrition.getSugar();
		}

		userNutritionMap.setCarbohydrate(carbohydrate);
		userNutritionMap.setFat(fat);
		userNutritionMap.setFiber(fiber);
		userNutritionMap.setProtein(protein);
		userNutritionMap.setSodium(sodium);
		userNutritionMap.setSugar(sugar);

		return userNutritionMap;
	}

	@Override
	public Nutrition calculateProductNutrition(double totalConsumption, String productName) {
		NutritionFacts nutritionFacts = nutritionFactsDao.getNutritionFacts(productName);
		//TODO check for null pointer exception here

		Nutrition nutrition = new Nutrition();
		nutrition.setCarbohydrate(calculateNutrition(totalConsumption, nutritionFacts.getCarbohydrate()));
		nutrition.setFat(calculateNutrition(totalConsumption, nutritionFacts.getFat()));
		nutrition.setFiber(calculateNutrition(totalConsumption, nutritionFacts.getFiber()));
		nutrition.setProtein(calculateNutrition(totalConsumption, nutritionFacts.getProtein()));
		nutrition.setSodium(calculateNutrition(totalConsumption, nutritionFacts.getSodium()));
		nutrition.setSugar(calculateNutrition(totalConsumption, nutritionFacts.getSugar()));
		return nutrition;
	}

	private double calculateNutrition(double totalConsumption, double nutritionValue) {
		double value = (totalConsumption * nutritionValue) / 100;

		long factor = (long) Math.pow(10, 2);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@Override
	public Double calculateTotalConsumption(String deviceId) {
		Double totalConsumption = new Double(0);
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

		List<Double> weights = deviceWeightDao.getWeight(deviceId, fromTimestamp, toTimestamp, true);
		if (CollectionUtils.isNullOrEmpty(weights)) {
			return totalConsumption;
		}

		for (int index = 0; index < weights.size() - 1; index++) {
			if (weights.get(index) > weights.get(index + 1)) {
				totalConsumption = totalConsumption + (weights.get(index) - weights.get(index + 1));
			}
		}
		return totalConsumption;
	}

	@Override
	public NutritionHistoryResponse getNutritionHistory(String userName) {
		NutritionHistoryResponse nutritionHistoryResponse = new NutritionHistoryResponse();

		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 55);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);

		Long toTimestamp = currentDate.getTimeInMillis();

		currentDate.set(Calendar.DAY_OF_WEEK, currentDate.getFirstDayOfWeek());

		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 1);
		currentDate.set(Calendar.MILLISECOND, 0);

		Long fromTimestamp = currentDate.getTimeInMillis();

		List<UserNutritionMap> userNutritionMapList = userNutritionDao.getNutritionHistory(userName, fromTimestamp,
				toTimestamp);

		if (CollectionUtils.isNullOrEmpty(userNutritionMapList)) {
			nutritionHistoryResponse.setMessage("No data found");
			nutritionHistoryResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			return nutritionHistoryResponse;
		}

		List<UserNutritionResponse> userNutritionResponses = new ArrayList<>();

		Map<Integer, String> daysOfWeekMap = new HashMap<>();
		daysOfWeekMap.put(1, "Sunday");
		daysOfWeekMap.put(2, "Monday");
		daysOfWeekMap.put(3, "Tuesday");
		daysOfWeekMap.put(4, "Wednesday");
		daysOfWeekMap.put(5, "Thursday");
		daysOfWeekMap.put(6, "Friday");
		daysOfWeekMap.put(7, "Saturday");

		Calendar calendar = Calendar.getInstance();
		for (UserNutritionMap userNutritionMap : userNutritionMapList) {
			UserNutritionResponse response = new UserNutritionResponse();

			calendar.setTimeInMillis(Long.parseLong(userNutritionMap.getTimestamp()));
			response.setDayOfWeek(daysOfWeekMap.get(calendar.get(Calendar.DAY_OF_WEEK)));
			response.setCarbohydrate(userNutritionMap.getCarbohydrate());
			response.setFat(userNutritionMap.getFat());
			response.setFiber(userNutritionMap.getFiber());
			response.setProtein(userNutritionMap.getProtein());
			response.setSugar(userNutritionMap.getSugar());
			response.setSodium(userNutritionMap.getSodium());
			response.setMessage("SUCCESS");
			response.setStatus(HttpStatus.OK.toString());

			userNutritionResponses.add(response);
		}
		nutritionHistoryResponse.setUserNutritionResponse(userNutritionResponses);
		nutritionHistoryResponse.setMessage("SUCCESS");
		nutritionHistoryResponse.setStatus(HttpStatus.OK.toString());
		return nutritionHistoryResponse;
	}

	@Override
	public UserNutritionResponse getDailyNutritionFacts(String userName) {
		UserNutritionResponse response = new UserNutritionResponse();
		List<DeviceProductMapping> deviceProductMap = deviceProductDao.getDeviceProductMappingByUserName(userName);

		if (CollectionUtils.isNullOrEmpty(deviceProductMap)) {
			response.setMessage("No data found");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			return response;
		}

		List<UserDevicesMap> userDevices = new ArrayList<>();
		for (DeviceProductMapping deviceProduct : deviceProductMap) {
			UserDevicesMap userDevice = new UserDevicesMap();
			userDevice.setDeviceId(deviceProduct.getDeviceId());
			userDevice.setProductName(deviceProduct.getLabel());
			userDevices.add(userDevice);
		}

		UserNutritionMap userNutritionMap = calculateDailyNutrition(userName, userDevices);

		response.setCarbohydrate(userNutritionMap.getCarbohydrate());
		response.setFat(userNutritionMap.getFat());
		response.setFiber(userNutritionMap.getFiber());
		response.setProtein(userNutritionMap.getProtein());
		response.setSugar(userNutritionMap.getSugar());
		response.setSodium(userNutritionMap.getSodium());
		response.setMessage("SUCCESS");
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}
}
