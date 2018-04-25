package edu.sjsu.missing.scoop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class NutritionTrackerActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    private int lastX =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);

        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(10);
        viewport.setScalable(true);

    }
    @Override
    protected void onResume(){
        super.onResume();
        //simulate real time with thread that append data to graph
        new Thread(new Runnable() {
            @Override
            public void run() {
                //add 100 new enteries
                for (int i=0; i<100;i++){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try{
                        Thread.sleep(600);
                    }catch (InterruptedException e){

                    }
                }

            }
        }).start();
    }
    public void addEntry(){
        Random r = new Random();

        //display max 10 points on viewport and scrol
        series.appendData(new DataPoint(lastX++, r.nextDouble()*10),true ,10);
    }
}
