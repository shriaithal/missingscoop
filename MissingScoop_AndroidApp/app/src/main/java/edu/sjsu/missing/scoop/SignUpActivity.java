package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;
import edu.sjsu.missing.scoop.authentication.AuthenticationListener;

public class SignUpActivity extends AppCompatActivity {

    AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authenticationHandler = new AuthenticationHandler();
    }

    public void signUp(View view) {
        String email = ((EditText) findViewById(R.id.signUpEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.signUpPwd)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.signUpConfirmPwd)).getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
            return;
        }

        authenticationHandler.registerWithEmailAndPassword(email, password, this, new AuthenticationListener() {

            @Override
            public void onSuccess(String message) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
