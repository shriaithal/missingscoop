package edu.sjsu.missingscoop.response;

import java.util.List;

public class NutritionHistoryResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	List<UserNutritionResponse> userNutritionResponse;

	public NutritionHistoryResponse() {
	}

	public NutritionHistoryResponse(List<UserNutritionResponse> userNutritionResponse) {
		super();
		this.userNutritionResponse = userNutritionResponse;
	}

	public List<UserNutritionResponse> getUserNutritionResponse() {
		return userNutritionResponse;
	}

	public void setUserNutritionResponse(List<UserNutritionResponse> userNutritionResponse) {
		this.userNutritionResponse = userNutritionResponse;
	}

	@Override
	public String toString() {
		return "NutritionHistoryResponse [userNutritionResponse=" + userNutritionResponse + "]";
	}
}
