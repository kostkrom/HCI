package com.krom.moneymanager.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.krom.moneymanager.Activities.MainActivity;
import com.krom.moneymanager.Adapter.TransactionAdapter;
import com.krom.moneymanager.R;
import com.krom.moneymanager.app.Transaction;
import com.krom.moneymanager.app.UserBalance;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    private TransactionAdapter transactionAdapter;
    private ListView listView;
    private View view;
    private TextView expenseText;
    private TextView incomeText;
    private TextView balanceText;
    private int balance;

    //private ArrayList<Transaction> transactionsList = new ArrayList<>();

    private UserBalance userBalance;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.transactionListView);
        expenseText = view.findViewById(R.id.expencesTextView);
        incomeText = view.findViewById(R.id.incomeTextView);
        balanceText = view.findViewById(R.id.balanceTextView);

        userBalance = ViewModelProviders.of(getActivity()).get(UserBalance.class);

        updateTextView();
        addCustomTransactionView();

        return view;
    }

    private void addCustomTransactionView(){
        transactionAdapter = new TransactionAdapter(getContext(), userBalance.getTransactionsList());
        listView.setAdapter(transactionAdapter);
    }

    public void updateTextView(){
        //expenseText.setText();
        balance = userBalance.getIncome() - userBalance.getExpenses();
        balanceText.setText(userBalance.toString(balance));
        expenseText.setText(userBalance.toString(userBalance.getExpenses()));
        incomeText.setText(userBalance.toString(userBalance.getIncome()));
    }

    public void updateListView(){
        transactionAdapter.clear();
        transactionAdapter.addAll(userBalance.getTransactionsList());
        transactionAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume(){
        super.onResume();
        updateTextView();
        //updateListView();
    }


}
