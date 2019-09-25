package com.krom.moneymanager.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.krom.moneymanager.R;
import com.krom.moneymanager.app.Transaction;
import com.krom.moneymanager.app.UserBalance;
import com.krom.moneymanager.app.barChart;

import java.util.ArrayList;

public class BarChartFragment extends Fragment {

    private View view;
    private ArrayList<Transaction> transactionsList;
    private BarChart horizontalChart;
    private barChart barChartInit;
    private UserBalance userBalance;
    private Resources resources;
    private String[] categoryNames;
    private int[] categoriesTimesUsed;
    private int totalCategoriesUsed;
    private int[][] expensesMonthDay;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_barchart, container, false);

        setHasOptionsMenu(true);

        userBalance = ViewModelProviders.of(getActivity()).get(UserBalance.class);
        resources = getResources();

        expensesMonthDay = new int[31][12];

        getNamesArray();
        categoriesTimesUsed = new int[categoryNames.length];

        horizontalChart = view.findViewById(R.id.horizontalChart);
        //horizontalChart.setOnChartValueSelectedListener(this);
        barChartInit = new barChart(view, getContext(), horizontalChart);

        setDataToBarChart();

        barChartInit.initializeBarChart();

        return view;
    }

    private void setDataToBarChart(){
        transactionsList = userBalance.getTransactionsList();

        categoriesCount();
        barChartInit.setData(categoryNames.length,5, categoriesTimesUsed, categoryNames, expensesMonthDay);

    }

    private void categoriesCount(){
        totalCategoriesUsed = 0;

        categoriesTimesUsed = new int[categoryNames.length];
        transactionsList = userBalance.getTransactionsList();
        for (int i =0; i < categoryNames.length; i++){
            for ( int j = 0; j < userBalance.getTransactionsList().size(); j++){
                Transaction transaction = transactionsList.get(j);
                calculateDayExpenses(transactionsList, transaction);

                if (categoryNames[i].equals(transaction.getTransactionName())){
                    categoriesTimesUsed[i] += 1;
                    totalCategoriesUsed++;
                }
            }
        }
    }

    private void calculateDayExpenses(ArrayList transactionsList, Transaction transaction){

        int expenseDay = Integer.valueOf(transaction.getDay());
        int expenseMonth = Integer.valueOf(transaction.getMonth());

        expensesMonthDay[expenseDay][expenseMonth] += transaction.getExpense();

//        for (int i = 0; i < 31; i++){
//            for (int j = 0; j < 12; j++){
//                Log.d("ArrayExpenses", String.valueOf(expensesMonthDay[i][j]));
//            }
//        }

        //Toast.makeText(getContext(), transaction.getDay(), Toast.LENGTH_SHORT).show();


    }

    private void getNamesArray(){
        categoryNames = resources.getStringArray(R.array.categoriesNames);
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
