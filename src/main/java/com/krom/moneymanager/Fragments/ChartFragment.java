package com.krom.moneymanager.Fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.krom.moneymanager.Activities.MainActivity;
import com.krom.moneymanager.R;
import com.krom.moneymanager.app.Transaction;
import com.krom.moneymanager.app.UserBalance;



import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.krom.moneymanager.app.barChart;
import com.krom.moneymanager.app.pieChart;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private View view;
    private UserBalance userBalance;
    //List<DataEntry> data;
    private ArrayList<Transaction> transactionsList;
    private Resources resources;
    private String[] categoryNames;
    private int[] categoriesTimesUsed;
    private ConstraintLayout constraintLayout;
    private PieChart pieChart;
    private HorizontalBarChart horizontalChart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private int totalCategoriesUsed;

    private com.krom.moneymanager.app.pieChart pieChartInit;
    private barChart  barChartInit;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chart, container, false);
        setHasOptionsMenu(true);

        getActivity().findViewById(R.id.addButton).setVisibility(View.INVISIBLE);

        //getActivity().findViewById(R.id.addButton).setVisibility(View.INVISIBLE);

        userBalance = ViewModelProviders.of(getActivity()).get(UserBalance.class);
        resources = getResources();
        //data = new ArrayList<>();

        getNamesArray();
        categoriesTimesUsed = new int[categoryNames.length];

        pieChart = view.findViewById(R.id.chart1);
        pieChart.setOnChartValueSelectedListener(this);

        //horizontalChart = view.findViewById(R.id.horizontalChart);
       // horizontalChart.setOnChartValueSelectedListener(this);

        //barChartInit = new barChart(view, getContext(), horizontalChart);

        pieChartInit = new pieChart(view, getContext(), pieChart);

        setDataToCharts();
        pieChartInit.initializePieChart();
        //barChartInit.initializeBarChart();

        tvX = view.findViewById(R.id.tvXMax);
        tvY = view.findViewById(R.id.tvYMax);

        seekBarX = view.findViewById(R.id.seekBar1);
        seekBarY = view.findViewById(R.id.seekBar2);

        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);

        //addHorizontalChart();
        //addValuesToChart(6,11);
        //pieChartInit.setData(5, 5);

        return view;
    }

    private void setDataToCharts(){
        transactionsList = userBalance.getTransactionsList();

        categoriesCount();

        pieChartInit.setData(categoryNames.length, categoriesTimesUsed, categoryNames);
        //barChartInit.setData(categoryNames.length, categoriesTimesUsed, categoryNames);



    }

    private void calculatePercetange(){

    }

    private void categoriesCount(){
        totalCategoriesUsed = 0;
        categoriesTimesUsed = new int[categoryNames.length];
        transactionsList = userBalance.getTransactionsList();
        for (int i =0; i < categoryNames.length; i++){
            for ( int j = 0; j < userBalance.getTransactionsList().size(); j++){
                Transaction transaction = transactionsList.get(j);
                if (categoryNames[i].equals(transaction.getTransactionName())){
                    categoriesTimesUsed[i] += 1;
                    totalCategoriesUsed++;
                }
           }
        }
    }

    private void getNamesArray(){
        categoryNames = resources.getStringArray(R.array.categoriesNames);
    }

    @Override
    public void onResume(){

        super.onResume();
        setDataToCharts();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        //pieChartInit.setData(5, 5);
        //barChartInit.setData(5,5);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());

    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment selectedFragment = null;

        switch (item.getItemId()){
            case R.id.barChartMenu:
                selectedFragment = new BarChartFragment();
                break;
            case R.id.pieChartMenu:
                selectedFragment = new ChartFragment();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chart_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
