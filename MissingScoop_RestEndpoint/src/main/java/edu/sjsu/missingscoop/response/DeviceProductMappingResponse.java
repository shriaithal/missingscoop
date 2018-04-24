package edu.sjsu.missingscoop.response;

public class DeviceProductMappingResponse extends GenericResponse {

	private static final long serialVersionUID = -3784041992343989270L;
	private String deviceId;
	private String label;
	private Integer threshold;

	public DeviceProductMappingResponse() {
	}

	public DeviceProductMappingResponse(String deviceId, String label, Integer threshold) {
		super();
		this.deviceId = deviceId;
		this.label = label;
		this.threshold = threshold;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return "DeviceProductMappingResponse [deviceId=" + deviceId + ", label=" + label + ", threshold=" + threshold
				+ "]";
	}

}
