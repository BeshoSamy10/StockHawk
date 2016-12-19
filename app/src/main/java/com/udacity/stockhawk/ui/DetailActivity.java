package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    LineChart stockChart;

    ArrayList<String> historyVal;
    //ArrayList<String> historyDat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String symbol = getIntent().getStringExtra("symbol");
        actionBar.setTitle(symbol+" History");

        String history = getIntent().getStringExtra("history");
        createValDatArrayLists(history);

        stockChart.setBackgroundColor(Color.WHITE);
        stockChart.setDrawGridBackground(false);
        stockChart.setBorderWidth(16f);


        XAxis xAxis = stockChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = stockChart.getAxisLeft();
        YAxis rightAxis = stockChart.getAxisRight();
        rightAxis.setEnabled(false);


        List<Entry> entries = new ArrayList<>();
        int x = 0;
        for(String val : historyVal)
        {
            x++;
            entries.add(new Entry(x,Integer.parseInt(val)));
        }

        //you can style these dataset
        LineDataSet dataSet = new LineDataSet(entries,symbol+" History");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineData lineData = new LineData(dataSet);
        stockChart.setData(lineData);
        stockChart.invalidate(); // refresh

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createValDatArrayLists(String history){

        historyVal = new ArrayList<>();
        //historyDat = new ArrayList<>();

        while (!history.isEmpty())
        {
            int index = history.indexOf("\n");
            String stock = history.substring(0,index);
            //historyDat.add(stock.substring(0,7));
            String valS = stock.substring(9,stock.length());
            int val = Math.round(Float.parseFloat(valS));
            historyVal.add(""+val);
            history = history.substring(stock.length()+1);
        }

    }

}
