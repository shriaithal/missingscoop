package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        TextView itemText = findViewById(R.id.item);
        TextView deviceText = findViewById(R.id.device);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            // double lat = valueOf(bd.get("lat");
            String item = bd.getString("itemSelected");
            String device = bd.getString("deviceSelected");
            itemText.setText(item);
            deviceText.setText(device);
        }
    }
}
