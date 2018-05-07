package edu.sjsu.missingscoop.response;

public class DeviceWeightResponse extends GenericResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double currentWeight;
	private double consumptionRate;
	private double estimatedCompletion;
	
	public DeviceWeightResponse() {
		
	}

	public DeviceWeightResponse(double currentWeight, double consumptionRate, double estimatedCompletion) {
		super();
		this.currentWeight = currentWeight;
		this.consumptionRate = consumptionRate;
		this.estimatedCompletion = estimatedCompletion;
	}
	
	public double getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(double currentWeight) {
		this.currentWeight = currentWeight;
	}

	public double getConsumptionRate() {
		return consumptionRate;
	}

	public void setConsumptionRate(double consumptionRate) {
		this.consumptionRate = consumptionRate;
	}

	public double getEstimatedCompletion() {
		return estimatedCompletion;
	}

	public void setEstimatedCompletion(double estimatedCompletion) {
		this.estimatedCompletion = estimatedCompletion;
	}

	@Override
	public String toString() {
		return "DeviceWeightResponse [currentWeight=" + currentWeight + ", consumptionRate=" + consumptionRate
				+ ", estimatedCompletion=" + estimatedCompletion + "]";
	}
	
	
}
