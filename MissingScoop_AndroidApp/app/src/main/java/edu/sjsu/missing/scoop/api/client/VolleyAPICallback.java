package edu.sjsu.missing.scoop.api.client;

import org.json.JSONObject;

import edu.sjsu.missing.scoop.api.request.DeviceProductMappingRequest;
import edu.sjsu.missing.scoop.api.response.DeviceProductMappingResponse;

/**
 * Created by Shriaithal on 4/24/2018.
 */

public interface VolleyAPICallback {

    void onSuccess(JSONObject jsonResponse);
    void onError(String message);
}
