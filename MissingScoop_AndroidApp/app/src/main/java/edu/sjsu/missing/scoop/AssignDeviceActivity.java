package edu.sjsu.missing.scoop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;

import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class AssignDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_device);

        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        UserInfo user = authenticationHandler.getCurrentUser();
        TextView tx = findViewById(R.id.textView2);
        tx.setText(user.getEmail());
        Log.i("App", user.getEmail());
    }
}
