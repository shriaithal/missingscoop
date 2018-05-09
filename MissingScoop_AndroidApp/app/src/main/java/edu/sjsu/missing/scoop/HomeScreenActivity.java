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

        getSupportActionBar().setTitle("Missing Scoop");
    }

    public void addDevice(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, AssignDeviceActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }

    public void groceryList(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, GroceryListActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }

    public void groceryTracker(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, ListProductActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }

    public void nutritionTracker(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, NutritionHistoryTrackerActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }

    public void nutritionDiary(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, NutritionTrackerActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }
}
