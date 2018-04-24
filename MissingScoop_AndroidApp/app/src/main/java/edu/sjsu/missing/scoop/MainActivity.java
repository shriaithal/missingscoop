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

public class MainActivity extends AppCompatActivity {

    AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticationHandler = new AuthenticationHandler();
    }

    public void login(View view) {
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        authenticationHandler.signInWithEmailAndPassword(email, password, this, new AuthenticationListener() {
            @Override
            public void onSuccess(String message) {
                startActivity(new Intent(getApplicationContext(), AssignDeviceActivity.class));
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), "Invalid username/password", Toast.LENGTH_LONG).show();
                return;
            }
        });

    }
}
