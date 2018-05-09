package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.request.DeviceProductMappingRequest;
import edu.sjsu.missing.scoop.api.response.DeviceProductListResponse;
import edu.sjsu.missing.scoop.api.response.DeviceProductMappingResponse;
import edu.sjsu.missing.scoop.api.response.NutritionFactsListResponse;
import edu.sjsu.missing.scoop.api.response.NutritionFactsResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class AssignDeviceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Gson gson;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_device);
        getSupportActionBar().setTitle("Add Devices");
        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();
        getProductList();
    }

    private void showProductDropDown(List<String> products) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssignDeviceActivity.this, android.R.layout.simple_spinner_item, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void getProductList() {
        restApiClient.executeGetAPI(getApplicationContext(), "/fetch/nutrition/all", new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                NutritionFactsListResponse response = gson.fromJson(jsonResponse.toString(), NutritionFactsListResponse.class);
                List<String> products = new ArrayList<>();
                for (NutritionFactsResponse record : response.getNutritionFacts()) {
                    products.add(record.getProductName());
                }
                showProductDropDown(products);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        productName = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void scanQRCode(View view) {
        Intent intent = new Intent(getApplicationContext(), QRCodeScanActivity.class);
        startActivityForResult(intent, RESULT_OK);
    }

    public void saveDeviceProductMap(View view) {
        UserInfo user = authenticationHandler.getCurrentUser();

        TextView policyTextView = findViewById(R.id.deviceId);
        EditText threshold = findViewById(R.id.threshold);

        DeviceProductMappingRequest request = new DeviceProductMappingRequest();
        request.setDeviceId(policyTextView.getText().toString());
        request.setThreshold(Integer.parseInt(threshold.getText().toString()));
        request.setLabel(productName);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_OK) {
            String qrCode = intent.getStringExtra("QRCode");
            Log.i("AssignDeviceActivity", qrCode);

            TextView qrCodeTextView = findViewById(R.id.deviceId);
            qrCodeTextView.setText(qrCode);

        }
    }
}
