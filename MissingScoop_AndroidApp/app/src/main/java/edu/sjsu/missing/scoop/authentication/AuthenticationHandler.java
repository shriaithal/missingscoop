package edu.sjsu.missing.scoop.authentication;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import edu.sjsu.missing.scoop.MainActivity;
import edu.sjsu.missing.scoop.SignUpActivity;

/**
 * Created by Shriaithal on 4/23/2018.
 */

public class AuthenticationHandler {

    private FirebaseAuth firebaseAuth;

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
}
