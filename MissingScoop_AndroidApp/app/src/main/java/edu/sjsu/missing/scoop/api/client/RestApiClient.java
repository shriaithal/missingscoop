package edu.sjsu.missing.scoop.api.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import edu.sjsu.missing.scoop.api.request.DeviceProductMappingRequest;

/**
 * Created by Shriaithal on 4/24/2018.
 */

public class RestApiClient {

    private final String BASE_URL = "http://54.177.122.80:8080";//"http://192.168.1.109:8080"; //"http://192.168.0.33:8080";//"http://18.221.192.106:8080";

    public void executePostAPI(Context context, String uri, JSONObject jsonObject, final VolleyAPICallback callback) {
        APIRequestQueue queue =  APIRequestQueue.getInstance(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + uri, jsonObject, new Response.Listener<JSONObject> () {

            @Override
            public void onResponse(JSONObject jsonResponse) {
                callback.onSuccess(jsonResponse);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("RestApiClient", "Error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
        queue.getRequestQueue().add(jsonObjectRequest);
    }

    public void executeGetAPI(Context context, String uri, final VolleyAPICallback callback) {
        APIRequestQueue queue =  APIRequestQueue.getInstance(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  BASE_URL + uri, null, new Response.Listener<JSONObject> () {

            @Override
            public void onResponse(JSONObject jsonResponse) {
                callback.onSuccess(jsonResponse);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("RestApiClient", "Error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
        queue.getRequestQueue().add(jsonObjectRequest);
    }
}
