package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void list(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, ListProductActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }

    public void todo(View view) {
    }
}
