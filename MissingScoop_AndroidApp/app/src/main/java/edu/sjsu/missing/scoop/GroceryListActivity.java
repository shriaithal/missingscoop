package edu.sjsu.missing.scoop;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.request.GroceryListRequest;
import edu.sjsu.missing.scoop.api.response.GrocerListResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;


import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity {
    ListView lvItems;
    ArrayAdapter<String> mAdapter;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;
    private GrocerListResponse response;
    private Gson gson;
    private List<String> groceryList = new ArrayList<>();
    private EditText groceryEditText;
    private TextView groceryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();

        lvItems=(ListView)findViewById(R.id.lvItems);
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
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    public void addGrocery(){
        UserInfo user =authenticationHandler.getCurrentUser();
        GroceryListRequest request = new GroceryListRequest();
        request.setGrocery(groceryEditText.getText().toString());
        request.setUserName(user.getEmail());
        try{
            JSONObject jsonObject =new JSONObject(gson.toJson(request));
            restApiClient.executePostAPI(getApplicationContext(), "/add/grocery", jsonObject, new VolleyAPICallback() {
                @Override
                public void onSuccess(JSONObject jsonResponse) {
                    GrocerListResponse response = gson.fromJson(jsonResponse.toString(),GrocerListResponse.class);
                    Log.i("GroceryListActivity",response.toString());
                }

                @Override
                public void onError(String message) {
                    Log.i("GroceryListActivity",message);

                }
            });

        }catch (JSONException e){
            Log.e("RestApiClient", e.getMessage());
        }
    }
    public void removeGrocery(){
        UserInfo user =authenticationHandler.getCurrentUser();
        GroceryListRequest request = new GroceryListRequest();
        request.setGrocery(groceryTextView.getText().toString());
        request.setUserName(user.getEmail());
        try{
            JSONObject jsonObject =new JSONObject(gson.toJson(request));
            restApiClient.executePostAPI(getApplicationContext(), "/remove/grocery", jsonObject, new VolleyAPICallback() {
                @Override
                public void onSuccess(JSONObject jsonResponse) {
                    GrocerListResponse response = gson.fromJson(jsonResponse.toString(),GrocerListResponse.class);
                    Log.i("GroceryListActivity",response.toString());
                }

                @Override
                public void onError(String message) {
                    Log.i("GroceryListActivity",message);

                }
            });

        }catch (JSONException e){
            Log.e("RestApiClient", e.getMessage());
        }
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu){

            getMenuInflater().inflate(R.menu.menu, menu);

            Drawable icon =menu.getItem(0) .getIcon();
            icon.mutate();
            icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            return super.onCreateOptionsMenu(menu);

        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()) {
                case R.id.action_add_grocery:
                    groceryEditText = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Add New Grocery")
                            .setMessage("what is your shopping plan?")
                            .setView(groceryEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String grocery = String.valueOf(groceryEditText.getText());
                                    //POST Method here
                                    addGrocery();
                                    loadGroceryList();
                                }
                            })
                          .setNegativeButton("Cancel",null).create();
                            dialog.show();
                            return true;

            }
            return super.onOptionsItemSelected(item);
        }
    public void deleteGrocery(View view){
        View parent =(View) view.getParent();
        groceryTextView = (TextView)findViewById(R.id.grocery_name);
        String grocery =String.valueOf(groceryTextView.getText());
        //post delete here
        removeGrocery();
        loadGroceryList();
    }

}
