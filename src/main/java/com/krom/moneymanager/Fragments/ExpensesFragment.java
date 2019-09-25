package com.krom.moneymanager.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krom.moneymanager.Activities.MainActivity;
import com.krom.moneymanager.Adapter.CategoriesAdapter;
import com.krom.moneymanager.R;
import com.krom.moneymanager.Decoration.VerticalSpaceItemDecoration;
import com.krom.moneymanager.app.Transaction;
import com.krom.moneymanager.app.UserBalance;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment implements CategoriesAdapter.ItemClickListener{

    private View view;
    private CategoriesAdapter categoriesAdapter;
    private ListView listView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private EditText inputExpenseNumber;
    private UserBalance userBalance;
    private ArrayList<Transaction> transactionsList = new ArrayList<>();
    private String categorySelected;
    private int categoryNumber;
    private int expenseAdded;
    private Resources resources;
    private int[] categoryImages;
    private String[] categoryNames;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expenses, container, false);

        userBalance = ViewModelProviders.of(getActivity()).get(UserBalance.class);

        inputExpenseNumber = view.findViewById(R.id.expenseEditText);
        addListenerOnTextChanged();

        resources = getResources();

        getImagesArray();
        getNamesArray();

        categoriesAdapter = new CategoriesAdapter(getContext(), categoryNames, categoryImages);
        categoriesAdapter.setClickListener(this);

        recyclerView = view.findViewById(R.id.categoriesRecyclerView);

        gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(categoriesAdapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(100));

        recyclerView.isClickable();


        return view;
    }


    @Override
    public void onItemCLick(View view, int position) {
        categorySelected = categoriesAdapter.getItem(position);
        categoryNumber = position;
        Toast.makeText(getContext(), categoriesAdapter.getItem(position), Toast.LENGTH_SHORT).show();

        getView().findViewById(R.id.expenseEditText).setVisibility(View.VISIBLE);
        forceKeyboardPopUp();
    }

    private void forceKeyboardPopUp(){
        inputExpenseNumber.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(inputExpenseNumber, InputMethodManager.SHOW_IMPLICIT);
    }

    private void addListenerOnTextChanged(){
        inputExpenseNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                expenseAdded = Integer.parseInt(inputExpenseNumber.getText().toString());
                userBalance.addExpence(expenseAdded);
                addExpense();
                disableTextView();
                jumpToActivity();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void jumpToActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void disableTextView(){
        getView().findViewById(R.id.expenseEditText).setVisibility(View.INVISIBLE);
    }

    private void updateTextView(){
        TextView balance = getView().findViewById(R.id.balanceTextView);
        balance.setText(userBalance.toString(userBalance.getBalance()));

    }

    private void addExpense(){
        Transaction transaction = new Transaction(1, categorySelected, expenseAdded, categoryNumber);
        userBalance.addTransaction(transaction);
        Log.d("List size Expense", userBalance.toString(userBalance.getTransactionsList().size()));
    }

    private void getImagesArray(){
        TypedArray images = resources.obtainTypedArray(R.array.categoriesImages);
        int count = images.length();
        categoryImages = new int[count];

        for (int i =0; i < count; i++){
            categoryImages[i] = images.getResourceId(i,0);
        }
    }

    private void getNamesArray(){
        categoryNames = resources.getStringArray(R.array.categoriesNames);
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
