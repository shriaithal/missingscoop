package edu.sjsu.missingscoop.service;

import java.util.List;
import java.util.Map;

import edu.sjsu.missingscoop.model.Nutrition;
import edu.sjsu.missingscoop.model.UserDevicesMap;
import edu.sjsu.missingscoop.model.UserNutritionMap;
import edu.sjsu.missingscoop.response.NutritionFactsListResponse;
import edu.sjsu.missingscoop.response.NutritionHistoryResponse;
import edu.sjsu.missingscoop.response.UserNutritionResponse;

public interface NutritionService {

	NutritionFactsListResponse getAllNutritionFacts();

	Double calculateTotalConsumption(String deviceId);

	Nutrition calculateProductNutrition(double totalConsumption, String productName);

	Map<String, List<UserDevicesMap>> findAllDevices();

	UserNutritionMap calculateDailyNutrition(String userName, List<UserDevicesMap> userDevicesMap);

	NutritionHistoryResponse getNutritionHistory(String userName);

	UserNutritionResponse getDailyNutritionFacts(String userName);

}
