package com.krom.moneymanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.krom.moneymanager.Fragments.ExpensesFragment;
import com.krom.moneymanager.Fragments.IncomeFragment;
import com.krom.moneymanager.R;
import com.krom.moneymanager.app.Transaction;
import com.krom.moneymanager.app.UserBalance;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TransactionsActivity extends AppCompatActivity {

    private UserBalance userBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addToolbar();

        setContentView(R.layout.transactions_activity);

        userBalance = ViewModelProviders.of(this).get(UserBalance.class);

        loadUser();

        getSupportFragmentManager().beginTransaction().replace(R.id.trasactions_fragment_container, new ExpensesFragment()).commit();

    }

    private void addToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transactions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.expenses_tab:
                getSupportFragmentManager().beginTransaction().replace(R.id.trasactions_fragment_container, new ExpensesFragment()).commit();
                break;
            case R.id.income_tab:
                getSupportFragmentManager().beginTransaction().replace(R.id.trasactions_fragment_container, new IncomeFragment()).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadUser(){
        SharedPreferences loadUser = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor loadEditor = loadUser.edit();

        Gson gson = new Gson();
        String json = loadUser.getString("TransactionList", null);


        Type type = new TypeToken<ArrayList<Transaction>>(){}.getType();
        ArrayList<Transaction> arrayList = gson.fromJson(json, type);

        userBalance.setTransactionsList(arrayList);

        userBalance.setBalance(loadUser.getInt("Balance", -1));
        userBalance.setIncome(loadUser.getInt("Income", -1));
        userBalance.setExpenses(loadUser.getInt("Expenses", -1));

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
    public void onPause(){
        super.onPause();
        saveUser();
    }
}
