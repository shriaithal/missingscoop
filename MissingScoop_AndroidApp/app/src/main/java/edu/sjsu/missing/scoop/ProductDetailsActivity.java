package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.response.DeviceProductListResponse;
import edu.sjsu.missing.scoop.api.response.DeviceProductMappingResponse;
import edu.sjsu.missing.scoop.api.response.DeviceWeightResponse;
import com.squareup.picasso.Picasso;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;
import edu.sjsu.missing.scoop.utilities.RVAdapter;
import edu.sjsu.missing.scoop.utilities.RecyclerTouchListener;

public class ProductDetailsActivity extends AppCompatActivity {

    private Gson gson;
    private RestApiClient restApiClient;
    private DeviceWeightResponse response;
    private TextView currentWeight;
    private TextView consumptionRateTextView;
    private TextView deviceTextView;
    private TextView estimatedCompletionTextView;
    private ImageView weightStatusImage;
    private double weight;
    private double consumptionRate;
    private double estimatedCompletion;
    private String item;
    private String device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        TextView itemText = findViewById(R.id.item);
        deviceTextView = findViewById(R.id.device);
        currentWeight = findViewById(R.id.currentWeight);
        weightStatusImage = findViewById(R.id.imageView);
        consumptionRateTextView = findViewById(R.id.consumptionRate);
        estimatedCompletionTextView = findViewById(R.id.estimatedCompletion);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            item = bd.getString("itemSelected");
            device = bd.getString("deviceSelected");

            restApiClient = new RestApiClient();
            gson = new Gson();
            getDeviceWeight(device);
        }
    }



    public void getDeviceWeight(final String device) {
        String uri = "/fetch/device/weight?deviceId=" + device;
        restApiClient.executeGetAPI(getApplicationContext(), uri, new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                response = gson.fromJson(jsonResponse.toString(), DeviceWeightResponse.class);
                Log.i("ProductDetailsActivity", response.toString());

                if(response==null)
                    deviceTextView.setText("No data found for the device");

                weight = response.getCurrentWeight();
                consumptionRate = response.getConsumptionRate();
                estimatedCompletion = response.getEstimatedCompletion();
                deviceTextView.setText("Device: "+device);
                currentWeight.setText("Remaining "+ item + ": " + weight + " grams");
                consumptionRateTextView.setText("Consumption Rate: "+consumptionRate +" grams per day");
                estimatedCompletionTextView.setText("Estimated Completion in "+estimatedCompletion +" days");

                if(weight<200)
                    weightStatusImage.setImageResource(R.drawable.empty);
                else if(weight>200 && weight<500)
                    weightStatusImage.setImageResource(R.drawable.medium2);
                else if(weight>500 && weight<700)
                    weightStatusImage.setImageResource(R.drawable.medium1);
                else if(weight>700)
                    weightStatusImage.setImageResource(R.drawable.full);

            }
            @Override
            public void onError(String message) {
                Log.i("ProductDetailsActivity", message);
            }
        });
    }

    public void refresh(View view) {
        getDeviceWeight(device);
    }
}
