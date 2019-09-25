package com.krom.moneymanager.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.krom.moneymanager.Fragments.ChartFragment;
import com.krom.moneymanager.Fragments.HomeFragment;
import com.krom.moneymanager.R;
import com.krom.moneymanager.Fragments.SettingsFragment;
import com.krom.moneymanager.app.Transaction;
import com.krom.moneymanager.app.UserBalance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextMessage;

    private ImageButton addButton;
    private UserBalance userBalance;
    private Activity activity;
    private Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    findViewById(R.id.addButton).setVisibility(View.VISIBLE);
                    loadUser();
                    break;
                case R.id.navigation_chart:
                    selectedFragment = new ChartFragment();
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new SettingsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        initializeButton();
        context = this;

        userBalance = ViewModelProviders.of(this).get(UserBalance.class);
        loadUser();



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private void initializeButton(){
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.addButton:
                Intent intent = new Intent(this, TransactionsActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void loadUser(){
        SharedPreferences loadUser = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor loadEditor = loadUser.edit();

        Gson gson = new Gson();
        String json = loadUser.getString("TransactionList", null);

        //TypeToken<ArrayList<Transaction>> token = new TypeToken<ArrayList<Transaction>>() {};
        Type type = new TypeToken<ArrayList<Transaction>>(){}.getType();
        ArrayList<Transaction> arrayList = gson.fromJson(json, type);

        userBalance.setTransactionsList(arrayList);

        userBalance.setBalance(loadUser.getInt("Balance", -1));
        userBalance.setIncome(loadUser.getInt("Income", -1));
        userBalance.setExpenses(loadUser.getInt("Expenses", -1));

        if (userBalance.getBalance() == -1 && userBalance.getExpenses() == -1 && userBalance.getIncome() == -1 && json == null) {
            userBalance.setBalance(20);
            userBalance.setExpenses(0);
            userBalance.setIncome(0);
            userBalance.setTransactionsList(new ArrayList());
            saveUser();
        }
    }

    private void saveUser(){
        SharedPreferences saveUser = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = saveUser.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userBalance.getTransactionsList());

        saveEditor.putString("TransactionList", json);

        saveEditor.putInt("Balance", userBalance.getBalance());
        saveEditor.putInt("Income", userBalance.getIncome());
        saveEditor.putInt("Expenses", userBalance.getExpenses());
        saveEditor.commit();

    }

    @Override
    public void onResume(){
        super.onResume();
        loadUser();

    }

    @Override
    public void onPause(){
        super.onPause();
        //saveUser();
    }


}
