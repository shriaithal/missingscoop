package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.response.DeviceProductListResponse;
import edu.sjsu.missing.scoop.api.response.DeviceProductMappingResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;
import edu.sjsu.missing.scoop.utilities.RVAdapter;
import edu.sjsu.missing.scoop.utilities.RecyclerTouchListener;


public class ListProductActivity extends AppCompatActivity {

    private Gson gson;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;
    private DeviceProductListResponse response;
    private TextView product;
    private List<String> labelsList = new ArrayList<>();
    private HashMap<String,String> labelsDeviceMap = new HashMap<>();
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        product = findViewById(R.id.product);

        rv = findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();
        getDeviceProductMapping();

        getSupportActionBar().setTitle("Grocery Tracker");
    }

    public void getDeviceProductMapping() {
        UserInfo user = authenticationHandler.getCurrentUser();
        String uri = "/fetch/device/product?userName=" + user.getEmail();
        restApiClient.executeGetAPI(getApplicationContext(), uri, new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                response = gson.fromJson(jsonResponse.toString(), DeviceProductListResponse.class);
                Log.i("ListProductActivity", response.toString());

                List<DeviceProductMappingResponse> responseList = response.getResponse();
                for (DeviceProductMappingResponse record : responseList) {
                    labelsList.add(record.getLabel());
                    labelsDeviceMap.put(record.getDeviceId(),record.getLabel());
                }
                Log.i("Product List", labelsList.toString());


                final RVAdapter adapter = new RVAdapter(labelsList);
                rv.setAdapter(adapter);

                rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        String deviceSelected;
                        String itemSelected = labelsList.get(position);
                        //Call intent to show selected details
                        Intent detailsIntent = new Intent(ListProductActivity.this, ProductDetailsActivity.class);
                        detailsIntent.putExtra("itemSelected", itemSelected);

                        for(Map.Entry<String,String> entry:labelsDeviceMap.entrySet())
                        {
                            if(entry.getValue() == itemSelected) {
                                deviceSelected = entry.getKey();
                                detailsIntent.putExtra("deviceSelected", deviceSelected);
                            }
                        }

                        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ListProductActivity.this.startActivity(detailsIntent);
                        //Toast.makeText(getApplicationContext(),  position+ " selected!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLongClick(View view, final int position) {
                    }
                }));
            }

            @Override
            public void onError(String message) {
                Log.i("ListProductActivity", message);
            }
        });
    }
}
