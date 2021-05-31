package com.appalber.otherchart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private String[] datos = new String[]{"CO2", "Temperatura", "Humedad"};
    private int[] datosRasp = new int[]{305, 21, 200};
    private int[] datosColores = new int[]{Color.CYAN, Color.BLUE, Color.GREEN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barChart = (BarChart) findViewById(R.id.barChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        createCharts();
    }

    private Chart getChart(Chart chart, String description, int textColor, int background, int animateY) {
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart) {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < datos.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = datosColores[i];
            entry.label = datos[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < datosRasp.length; i++) {
            entries.add(new BarEntry(i, datosRasp[i]));
        }
        return entries;
    }

    private ArrayList<PieEntry> getPieEntries() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < datosRasp.length; i++) {
            entries.add(new PieEntry(i, datosRasp[i]));
        }
        return entries;
    }

    private void axisX(XAxis axis) {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(datos));
    }

    private void axisLeft(YAxis axis) {
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis) {
        axis.setEnabled(false);
    }

    private void createCharts(){
        barChart=(BarChart) getChart(barChart,"Medición con sensor", Color.GRAY, Color.GREEN, 3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(false);
        barChart.setData(getBarData());
        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());

        pieChart=(PieChart) getChart(pieChart, "medidas", Color.GRAY, Color.YELLOW, 3000);
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData());
        pieChart.invalidate();
        //pieChart.setDrawHoleEnabled(false);
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(datosColores);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(), ""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return  barData;
    }

    private PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet)getData(new PieDataSet(getPieEntries(), ""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }

}
