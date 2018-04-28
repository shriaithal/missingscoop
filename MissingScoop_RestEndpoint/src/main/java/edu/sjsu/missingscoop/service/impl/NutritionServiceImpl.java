package edu.sjsu.missingscoop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.NutritionFactsDao;
import edu.sjsu.missingscoop.model.NutritionFacts;
import edu.sjsu.missingscoop.response.NutritionFactsListResponse;
import edu.sjsu.missingscoop.response.NutritionFactsResponse;
import edu.sjsu.missingscoop.service.NutritionService;

@Service
public class NutritionServiceImpl implements NutritionService {

	@Autowired
	NutritionFactsDao nutritionFactsDao;

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
}
