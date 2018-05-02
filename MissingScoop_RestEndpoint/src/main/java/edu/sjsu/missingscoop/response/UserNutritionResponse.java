package edu.sjsu.missingscoop.response;

public class UserNutritionResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;
	private double carbohydrate;
	private double fat;
	private double protein;
	private double fiber;
	private double sodium;
	private double sugar;
	private String dayOfWeek;

	public UserNutritionResponse() {
	}

	public UserNutritionResponse(double carbohydrate, double fat, double protein, double fiber, double sodium,
			double sugar, String dayOfWeek) {
		super();
		this.carbohydrate = carbohydrate;
		this.fat = fat;
		this.protein = protein;
		this.fiber = fiber;
		this.sodium = sodium;
		this.sugar = sugar;
		this.dayOfWeek = dayOfWeek;
	}

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFiber() {
		return fiber;
	}

	public void setFiber(double fiber) {
		this.fiber = fiber;
	}

	public double getSodium() {
		return sodium;
	}

	public void setSodium(double sodium) {
		this.sodium = sodium;
	}

	public double getSugar() {
		return sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public String toString() {
		return "UserNutritionResponse [carbohydrate=" + carbohydrate + ", fat=" + fat + ", protein=" + protein
				+ ", fiber=" + fiber + ", sodium=" + sodium + ", sugar=" + sugar + ", dayOfWeek=" + dayOfWeek + "]";
	}

}
