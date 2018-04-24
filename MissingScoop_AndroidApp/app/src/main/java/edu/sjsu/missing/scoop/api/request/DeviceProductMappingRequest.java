package edu.sjsu.missing.scoop.api.request;

import java.io.Serializable;

/**
 * Created by Shriaithal on 4/23/2018.
 */

public class DeviceProductMappingRequest implements Serializable {
    private String userName;
    private String deviceId;
    private String label;
    private Integer threshold;

    public DeviceProductMappingRequest() {
    }

    public DeviceProductMappingRequest(String userName, String deviceId, String label, Integer threshold) {
        super();
        this.userName = userName;
        this.deviceId = deviceId;
        this.label = label;
        this.threshold = threshold;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "DeviceProductMappingRequest [userName=" + userName + ", deviceId=" + deviceId + ", label=" + label
                + ", threshold=" + threshold + "]";
    }

}
