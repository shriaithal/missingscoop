package edu.sjsu.missing.scoop.api.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shriaithal on 4/24/2018.
 */

public class DeviceProductListResponse extends GenericResponse{

    private static final long serialVersionUID = 1L;

    List<DeviceProductMappingResponse> response = new ArrayList<DeviceProductMappingResponse>();

    public DeviceProductListResponse() {
    }

    public DeviceProductListResponse(List<DeviceProductMappingResponse> response) {
        super();
        this.response = response;
    }

    public List<DeviceProductMappingResponse> getResponse() {
        return response;
    }

    public void setResponse(List<DeviceProductMappingResponse> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "DeviceProductListResponse [response=" + response + "]";
    };
}
