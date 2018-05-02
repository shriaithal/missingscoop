package edu.sjsu.missing.scoop.api.response;

public class DeviceWeightResponse extends GenericResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String deviceId;
	private String weight;
	private String timestamp;
	
	public DeviceWeightResponse() {
		
	}
	
	public DeviceWeightResponse(String deviceId, String weight, String time) {
		super();
		this.deviceId = deviceId;
		this.weight = weight;
		this.timestamp = time;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "DeviceWeightResponse [id=" + id + ", deviceId=" + deviceId + ", weight=" + weight + ", timestamp="
				+ timestamp + "]";
	}
	
}
