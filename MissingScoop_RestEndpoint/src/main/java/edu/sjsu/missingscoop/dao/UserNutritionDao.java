package edu.sjsu.missingscoop.dao;

import java.util.List;

import edu.sjsu.missingscoop.model.UserNutritionMap;

public interface UserNutritionDao {

	void save(UserNutritionMap userNutritionMap);

	List<UserNutritionMap> getNutritionHistory(String userName, Long fromTimestamp, Long toTimestamp);

}
