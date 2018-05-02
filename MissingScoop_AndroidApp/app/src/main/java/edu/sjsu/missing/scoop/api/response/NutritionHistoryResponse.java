package edu.sjsu.missing.scoop.api.response;

import java.util.List;

import edu.sjsu.missing.scoop.api.response.GenericResponse;

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
