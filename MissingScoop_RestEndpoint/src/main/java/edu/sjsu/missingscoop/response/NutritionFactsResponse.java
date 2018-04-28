package edu.sjsu.missingscoop.response;

public class NutritionFactsResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;
	private String id;
	private String productName;
	private double carbohydrate;
	private double fat;
	private double protein;
	private double fiber;
	private double sodium;
	private double sugar;

	public NutritionFactsResponse() {
	}

	public NutritionFactsResponse(String id, String productName, double carbohydrate, double fat, double protein,
			double fiber, double sodium, double sugar) {
		super();
		this.id = id;
		this.productName = productName;
		this.carbohydrate = carbohydrate;
		this.fat = fat;
		this.protein = protein;
		this.fiber = fiber;
		this.sodium = sodium;
		this.sugar = sugar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	@Override
	public String toString() {
		return "NutritionFactsResponse [id=" + id + ", productName=" + productName + ", carbohydrate=" + carbohydrate
				+ ", fat=" + fat + ", protein=" + protein + ", fiber=" + fiber + ", sodium=" + sodium + ", sugar="
				+ sugar + "]";
	}

}
