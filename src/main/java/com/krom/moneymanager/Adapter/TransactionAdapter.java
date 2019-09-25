package com.krom.moneymanager.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krom.moneymanager.R;
import com.krom.moneymanager.app.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private List<Transaction> transactionList = new ArrayList<>();
    int[] categoryImages;

    public TransactionAdapter(Context context, ArrayList<Transaction> list){
        super(context, 0, list);

        this.context = context;
        this.transactionList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        getImagesArray();

        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.expenses_layout, parent, false);
        }

        Transaction currentTransaction = transactionList.get(position);

        ImageView transactionImage = listItem.findViewById(R.id.expenseImage);
        transactionImage.setImageResource(categoryImages[currentTransaction.getCategoryNumber()]);

        TextView transactionAmount = listItem.findViewById(R.id.expenseAmmountText);
        transactionAmount.setText(String.valueOf(currentTransaction.getExpense()));

        TextView transactionName = listItem.findViewById(R.id.expenseName);
        transactionName.setText(currentTransaction.getTransactionName());

        TextView transactionDate = listItem.findViewById(R.id.dateText);
        transactionDate.setText(currentTransaction.getTransactionDate());

        TextView transactionTotal = listItem.findViewById(R.id.expensesTotalText);
        transactionTotal.setText(String.valueOf(currentTransaction.getTotalTransaction()));

        return listItem;
    }

    private void getImagesArray(){
        Resources resources = context.getResources();
        TypedArray images = resources.obtainTypedArray(R.array.categoriesImages);
        int count = images.length();
        categoryImages = new int[count];

        for (int i =0; i < count; i++){
            categoryImages[i] = images.getResourceId(i,0);
        }
    }
}
