package edu.sjsu.missing.scoop.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.missing.scoop.R;
import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.request.DeviceProductMappingRequest;
import edu.sjsu.missing.scoop.api.request.UserTokenRequest;
import edu.sjsu.missing.scoop.api.response.DeviceProductMappingResponse;
import edu.sjsu.missing.scoop.api.response.GenericResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class UserTokenService extends FirebaseInstanceIdService {


    public UserTokenService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    }

}
