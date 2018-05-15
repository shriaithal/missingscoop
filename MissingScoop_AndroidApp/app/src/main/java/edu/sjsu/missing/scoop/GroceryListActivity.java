package edu.sjsu.missing.scoop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.request.GroceryListRequest;
import edu.sjsu.missing.scoop.api.response.GrocerListResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class GroceryListActivity extends AppCompatActivity {
    ListView lvItems;
    ArrayAdapter<String> mAdapter;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;
    private GrocerListResponse response;
    private Gson gson;
    private EditText groceryEditText;
    private TextView groceryTextView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        getSupportActionBar().setTitle("Grocery List");
        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();

        lvItems = (ListView) findViewById(R.id.lvItems);
        loadGroceryList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGroceryList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadGroceryList();
    }

    public void loadGroceryList() {
        UserInfo user = authenticationHandler.getCurrentUser();
        String uri = "/grocery?userName=" + user.getEmail();
        restApiClient.executeGetAPI(getApplicationContext(), uri, new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                GrocerListResponse response = gson.fromJson(jsonResponse.toString(), GrocerListResponse.class);
                Log.i("GroceryListActivity", response.toString());

                addToListView(response.getGroceryList());
            }

            @Override
            public void onError(String message) {

                Log.i("GroceryListActivity", message);

            }
        });
    }

    public void addGrocery() {
        UserInfo user = authenticationHandler.getCurrentUser();
        GroceryListRequest request = new GroceryListRequest();
        request.setGrocery(groceryEditText.getText().toString());
        request.setUserName(user.getEmail());
        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(request));
            restApiClient.executePostAPI(getApplicationContext(), "/add/grocery", jsonObject, new VolleyAPICallback() {
                @Override
                public void onSuccess(JSONObject jsonResponse) {
                    GrocerListResponse response = gson.fromJson(jsonResponse.toString(), GrocerListResponse.class);
                    Log.i("GroceryListActivity", response.toString());
                    //loadGroceryList();
                    addToListView(response.getGroceryList());
                }

                @Override
                public void onError(String message) {
                    Log.i("GroceryListActivity", message);
                }
            });

        } catch (JSONException e) {
            Log.e("GroceryListActivity", e.getMessage());
        }
    }

    public void removeGrocery(String grocery) {
        UserInfo user = authenticationHandler.getCurrentUser();
        GroceryListRequest request = new GroceryListRequest();
        request.setUserName(user.getEmail());
        request.setGrocery(grocery);
        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(request));
            restApiClient.executePostAPI(getApplicationContext(), "/remove/grocery", jsonObject, new VolleyAPICallback() {
                @Override
                public void onSuccess(JSONObject jsonResponse) {
                    GrocerListResponse response = gson.fromJson(jsonResponse.toString(), GrocerListResponse.class);
                    Log.i("GroceryListActivity", response.toString());
                    //loadGroceryList();
                    addToListView(response.getGroceryList());
                }

                @Override
                public void onError(String message) {
                    Log.i("GroceryListActivity", message);
                }
            });
        } catch (JSONException e) {
            Log.e("GroceryListActivity", e.getMessage());
        }
    }

    private void addToListView(List<String> groceryList) {
        if (groceryList == null) {
            groceryList = new ArrayList<String>();
        }
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(GroceryListActivity.this, R.layout.row, R.id.grocery_name, groceryList);
            lvItems.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(groceryList);
            adapter.notifyDataSetChanged();

        }
    }

    public void openGroceryDialog(View view) {
        groceryEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add New Item")
                .setView(groceryEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String grocery = String.valueOf(groceryEditText.getText());
                        addGrocery();
                        loadGroceryList();
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    public void deleteGrocery(View view) {
        View parent = (View) view.getParent();
        int pos = lvItems.getPositionForView(parent);
        String grocery = adapter.getItem(pos);
        removeGrocery(grocery);
        loadGroceryList();
    }

    public void openMaps(View view) {
        Intent intent = new Intent(GroceryListActivity.this, MapsActivity.class);
        GroceryListActivity.this.startActivity(intent);
    }
}