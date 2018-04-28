package edu.sjsu.missingscoop.response;

import java.util.List;

public class NutritionFactsListResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	List<NutritionFactsResponse> nutritionFacts;

	public NutritionFactsListResponse() {
	}

	public NutritionFactsListResponse(List<NutritionFactsResponse> nutritionFacts) {
		super();
		this.nutritionFacts = nutritionFacts;
	}

	public List<NutritionFactsResponse> getNutritionFacts() {
		return nutritionFacts;
	}

	public void setNutritionFacts(List<NutritionFactsResponse> nutritionFacts) {
		this.nutritionFacts = nutritionFacts;
	}

	@Override
	public String toString() {
		return "NutritionFactsListResponse [nutritionFacts=" + nutritionFacts + "]";
	}

}
