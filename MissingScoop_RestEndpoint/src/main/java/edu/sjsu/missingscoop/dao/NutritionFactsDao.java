package edu.sjsu.missingscoop.dao;

import java.util.List;

import edu.sjsu.missingscoop.model.NutritionFacts;

public interface NutritionFactsDao {

	List<NutritionFacts> getAllFromNutritionFacts();

	NutritionFacts getNutritionFacts(String productName);
}
