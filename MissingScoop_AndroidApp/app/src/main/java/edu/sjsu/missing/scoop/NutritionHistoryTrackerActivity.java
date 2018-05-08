package edu.sjsu.missing.scoop;

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
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.missing.scoop.api.client.RestApiClient;
import edu.sjsu.missing.scoop.api.client.VolleyAPICallback;
import edu.sjsu.missing.scoop.api.response.NutritionFactsListResponse;
import edu.sjsu.missing.scoop.api.response.NutritionFactsResponse;
import edu.sjsu.missing.scoop.api.response.NutritionHistoryResponse;
import edu.sjsu.missing.scoop.api.response.UserNutritionResponse;
import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;

public class NutritionHistoryTrackerActivity extends AppCompatActivity {

    private View mChart;
    private String[] mDaysOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private Gson gson;
    private RestApiClient restApiClient;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_history_tracker);

        authenticationHandler = new AuthenticationHandler();
        restApiClient = new RestApiClient();
        gson = new Gson();
        getNutritionHistory();
    }

    private void getNutritionHistory() {
        UserInfo user = authenticationHandler.getCurrentUser();
        String uri = "/nutrition/history?userName=" + user.getEmail();
        restApiClient.executeGetAPI(getApplicationContext(), uri, new VolleyAPICallback() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                NutritionHistoryResponse response = gson.fromJson(jsonResponse.toString(), NutritionHistoryResponse.class);
                openChart(response.getUserNutritionResponse());
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void openChart(List<UserNutritionResponse> response) {

        if (null == response || response.isEmpty()) {
            return;
        }

        double[] carb = new double[7];
        double[] protein = new double[7];
        double[] fat = new double[7];
        double[] sugar = new double[7];
        double[] sodium = new double[7];
        double[] fiber = new double[7];

        for (int index = 0; index < response.size(); index++) {
            carb[index] = response.get(index).getCarbohydrate();
            protein[index] = response.get(index).getCarbohydrate();
            sugar[index] = response.get(index).getProtein();
            fat[index] = response.get(index).getFat();
            sodium[index] = response.get(index).getSodium();
            fiber[index] = response.get(index).getFiber();
        }

        XYSeries carbSeries = new XYSeries("Carbohydrates");
        XYSeries proteinSeries = new XYSeries("Proteins");
        XYSeries fatSeries = new XYSeries("Fats");
        XYSeries sugarSeries = new XYSeries("Sugar");
        XYSeries sodiumSeries = new XYSeries("Sodium");
        XYSeries fiberSeries = new XYSeries("Fiber");
        for (int i = 0; i < carb.length; i++) {
            carbSeries.add(i, carb[i]);
            proteinSeries.add(i, protein[i]);
            fatSeries.add(i, fat[i]);
            /*sugarSeries.add(i, sugar[i]);
            sodiumSeries.add(i, sodium[i]);
            fiberSeries.add(i, fiber[i]);*/

        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(carbSeries);
        dataset.addSeries(proteinSeries);
        dataset.addSeries(fatSeries);
        /* dataset.addSeries(sugarSeries);
        dataset.addSeries(sodiumSeries);
        dataset.addSeries(fiberSeries);*/

        XYSeriesRenderer carbRenderer = new XYSeriesRenderer();
        carbRenderer.setColor(Color.parseColor("#1CD3A2"));
        carbRenderer.setFillPoints(true);
        carbRenderer.setLineWidth(2f);
        carbRenderer.setDisplayChartValues(true);
        carbRenderer.setDisplayChartValuesDistance(10);
        carbRenderer.setPointStyle(PointStyle.CIRCLE);
        carbRenderer.setStroke(BasicStroke.SOLID);

        XYSeriesRenderer proteinRenderer = new XYSeriesRenderer();
        proteinRenderer.setColor(Color.parseColor("#ADADD6"));
        proteinRenderer.setFillPoints(true);
        proteinRenderer.setLineWidth(2f);
        proteinRenderer.setDisplayChartValues(true);
        proteinRenderer.setPointStyle(PointStyle.CIRCLE);
        proteinRenderer.setStroke(BasicStroke.SOLID);

        XYSeriesRenderer fatRenderer = new XYSeriesRenderer();
        fatRenderer.setColor(Color.parseColor("#81D4FA"));
        fatRenderer.setFillPoints(true);
        fatRenderer.setLineWidth(2f);
        fatRenderer.setDisplayChartValues(true);
        fatRenderer.setPointStyle(PointStyle.CIRCLE);
        fatRenderer.setStroke(BasicStroke.SOLID);

        /*XYSeriesRenderer fiberRenderer = new XYSeriesRenderer();
        fiberRenderer.setColor(Color.parseColor("#0288D1"));
        fiberRenderer.setFillPoints(true);
        fiberRenderer.setLineWidth(2f);
        fiberRenderer.setDisplayChartValues(true);
        fiberRenderer.setPointStyle(PointStyle.CIRCLE);
        fiberRenderer.setStroke(BasicStroke.SOLID);

        XYSeriesRenderer sugarRenderer = new XYSeriesRenderer();
        sugarRenderer.setColor(Color.parseColor("#29B6F6"));
        sugarRenderer.setFillPoints(true);
        sugarRenderer.setLineWidth(2f);
        sugarRenderer.setDisplayChartValues(true);
        sugarRenderer.setPointStyle(PointStyle.CIRCLE);
        sugarRenderer.setStroke(BasicStroke.SOLID);

        XYSeriesRenderer sodiumRenderer = new XYSeriesRenderer();
        sodiumRenderer.setColor(Color.parseColor("#B3E5FC"));
        sodiumRenderer.setFillPoints(true);
        sodiumRenderer.setLineWidth(2f);
        sodiumRenderer.setDisplayChartValues(true);
        sodiumRenderer.setPointStyle(PointStyle.CIRCLE);
        sodiumRenderer.setStroke(BasicStroke.SOLID);*/

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setXTitle("Days of Week");
        multiRenderer.setYTitle("Nutrition Values");

        multiRenderer.setChartTitleTextSize(28);
        multiRenderer.setAxisTitleTextSize(24);
        multiRenderer.setLabelsTextSize(24);
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
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        multiRenderer.setYLabels(7);
        multiRenderer.setXAxisMin(-0.75);
        multiRenderer.setXAxisMax(7);
        multiRenderer.setBarSpacing(1);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(2f);
        multiRenderer.setPointSize(4f);
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabelsPadding(4f);
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        multiRenderer.setPointSize(4f);
        multiRenderer.setMargins(new int[]{30, 70, 30, 30});

        //multiRenderer.setYAxisMax(40000);

        for (int i = 0; i < mDaysOfWeek.length; i++) {
            multiRenderer.addXTextLabel(i, mDaysOfWeek[i]);
        }

        multiRenderer.addSeriesRenderer(carbRenderer);
        multiRenderer.addSeriesRenderer(proteinRenderer);
        multiRenderer.addSeriesRenderer(fatRenderer);
        /*multiRenderer.addSeriesRenderer(fiberRenderer);
        multiRenderer.addSeriesRenderer(sugarRenderer);
        multiRenderer.addSeriesRenderer(sodiumRenderer);*/

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        chartContainer.removeAllViews();
        mChart = ChartFactory.getBarChartView(NutritionHistoryTrackerActivity.this, dataset, multiRenderer, BarChart.Type.DEFAULT);
        chartContainer.addView(mChart);

    }
}
