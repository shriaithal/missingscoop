package edu.sjsu.missing.scoop.authentication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.missing.scoop.MainActivity;
import edu.sjsu.missing.scoop.SignUpActivity;
import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.request.UserTokenRequest;
import edu.sjsu.missing.scoop.api.response.GenericResponse;

/**
 * Created by Shriaithal on 4/23/2018.
 */

public class AuthenticationHandler {

    private FirebaseAuth firebaseAuth;
    private Gson gson;
    private RestApiClient restApiClient;

    public AuthenticationHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signInWithEmailAndPassword(String email, String password, final MainActivity activity, final AuthenticationListener authenticationListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authenticationListener.onSuccess("Login Success!!");
                } else {
                    authenticationListener.onFailure("Login Failed!!");
                }
            }
        });
    }

    public UserInfo getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public void registerWithEmailAndPassword(String email, String password, final SignUpActivity activity, final AuthenticationListener authenticationListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authenticationListener.onSuccess("Login Success!!");
                } else {
                    authenticationListener.onFailure("Login Failed!!");
                }
            }
        });
    }

    public void sendRegistrationToServer(String userName, Context context) {

        restApiClient = new RestApiClient();
        gson = new Gson();

        UserTokenRequest request = new UserTokenRequest();
        request.setUserName(userName);
        request.setTokenId(FirebaseInstanceId.getInstance().getToken());

        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(request));
            restApiClient.executePostAPI(context, "/user/token", jsonObject, new VolleyAPICallback() {
                @Override
                public void onSuccess(JSONObject jsonResponse) {
                    GenericResponse response = gson.fromJson(jsonResponse.toString(), GenericResponse.class);
                    Log.i("UserTokenService", response.toString());
                }

                @Override
                public void onError(String message) {
                    Log.i("UserTokenService", message);
                }
            });

        } catch (JSONException e) {
            Log.e("UserTokenService", e.getMessage());
        }
    }
}
