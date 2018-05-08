package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONObject;

import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.response.NutritionHistoryResponse;
import edu.sjsu.missing.scoop.api.response.UserNutritionResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class NutritionTrackerActivity extends AppCompatActivity {

    private String[] mNutrtions = {"Carbs", "Proteins", "Fats", "Fiber", "Sugar", "Sodium"};
    private Gson gson;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);
        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();
        getDailyNutritionValue();
    }

    private void getDailyNutritionValue() {
        UserInfo user = authenticationHandler.getCurrentUser();
        String uri = "/nutrition/daily?userName=" + user.getEmail();
        restApiClient.executeGetAPI(getApplicationContext(), uri, new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                UserNutritionResponse response = gson.fromJson(jsonResponse.toString(), UserNutritionResponse.class);
                openChart(response);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void openChart(UserNutritionResponse response) {

        if (null == response) {
            return;
        }

        double[] nutritionValues = new double[7];
        nutritionValues[0] = response.getCarbohydrate();
        nutritionValues[1] = response.getProtein();
        nutritionValues[2] = response.getFat();
        nutritionValues[3] = response.getFiber();
        nutritionValues[4] = response.getSugar();
        nutritionValues[5] = response.getSodium();

        XYSeries nutritionValueSeries = new XYSeries("Nutrition Values");
        for (int i = 0; i < mNutrtions.length; i++) {
            nutritionValueSeries.add(i, nutritionValues[i]);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(nutritionValueSeries);

        XYSeriesRenderer nutritionValueRenderer = new XYSeriesRenderer();
        nutritionValueRenderer.setColor(Color.rgb(130, 130, 230));
        nutritionValueRenderer.setFillPoints(true);
        nutritionValueRenderer.setLineWidth(2);
        nutritionValueRenderer.setDisplayChartValues(true);

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setXTitle("Nutrients");
        multiRenderer.setYTitle("Nutrition Values");

        multiRenderer.setChartTitleTextSize(28);
        multiRenderer.setAxisTitleTextSize(28);
        multiRenderer.setLabelsTextSize(28);
        multiRenderer.setZoomButtonsVisible(false);
        multiRenderer.setPanEnabled(false, false);
        multiRenderer.setClickEnabled(false);
        multiRenderer.setZoomEnabled(false, false);
        multiRenderer.setShowGridY(true);
        multiRenderer.setShowGridX(true);
        multiRenderer.setFitLegend(true);
        multiRenderer.setShowGrid(true);
        multiRenderer.setZoomEnabled(false);
        multiRenderer.setExternalZoomEnabled(false);
        multiRenderer.setAntialiasing(true);
        multiRenderer.setInScroll(false);
        multiRenderer.setLegendHeight(30);
        multiRenderer.setYLabels(6);
        multiRenderer.setXAxisMin(-0.75);
        multiRenderer.setXAxisMax(7);
        multiRenderer.setBarSpacing(2);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(4f);
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabelsPadding(4f);
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        multiRenderer.setPointSize(4f);
        multiRenderer.setMargins(new int[]{30, 70, 30, 30});

        for (int i = 0; i < mNutrtions.length; i++) {
            multiRenderer.addXTextLabel(i, mNutrtions[i]);
        }

        multiRenderer.addSeriesRenderer(nutritionValueRenderer);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart2);
        chartContainer.removeAllViews();
        View mChart = ChartFactory.getBarChartView(getBaseContext(), dataset, multiRenderer, BarChart.Type.DEFAULT);
        chartContainer.addView(mChart);
    }
}
