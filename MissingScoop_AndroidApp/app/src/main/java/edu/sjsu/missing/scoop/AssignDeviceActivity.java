package edu.sjsu.missing.scoop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.request.DeviceProductMappingRequest;
import edu.sjsu.missing.scoop.api.response.DeviceProductListResponse;
import edu.sjsu.missing.scoop.api.response.DeviceProductMappingResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class AssignDeviceActivity extends AppCompatActivity {

    private Gson gson;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_device);

        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();

        // saveDeviceProductMapping();
        getDeviceProductMapping();
    }

    public void saveDeviceProductMapping() {
        UserInfo user = authenticationHandler.getCurrentUser();
        DeviceProductMappingRequest request = new DeviceProductMappingRequest();
        request.setDeviceId("00021");
        request.setLabel("Wheat");
        request.setThreshold(5);
        request.setUserName(user.getEmail());

        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(request));
            restApiClient.executePostAPI(getApplicationContext(), "/map/device/product", jsonObject, new VolleyAPICallback() {
                @Override
                public void onSuccess(JSONObject jsonResponse) {
                    DeviceProductMappingResponse response = gson.fromJson(jsonResponse.toString(), DeviceProductMappingResponse.class);
                    Log.i("AssignDeviceActivity", response.toString());
                }

                @Override
                public void onError(String message) {
                    Log.i("AssignDeviceActivity", message);
                }
            });

        } catch (JSONException e) {
            Log.e("RestApiClient", e.getMessage());
        }
    }

    public void getDeviceProductMapping() {
        UserInfo user = authenticationHandler.getCurrentUser();
        String uri = "/fetch/device/product?userName=" + user.getEmail();
        restApiClient.executeGetAPI(getApplicationContext(), uri, new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                DeviceProductListResponse response = gson.fromJson(jsonResponse.toString(), DeviceProductListResponse.class);
                Log.i("AssignDeviceActivity", response.toString());
            }

            @Override
            public void onError(String message) {
                Log.i("AssignDeviceActivity", message);
            }
        });
    }
}
