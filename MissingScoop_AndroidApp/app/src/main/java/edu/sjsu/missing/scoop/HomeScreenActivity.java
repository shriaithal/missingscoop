package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            AuthenticationHandler authenticationHandler = new AuthenticationHandler();
            authenticationHandler.logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
