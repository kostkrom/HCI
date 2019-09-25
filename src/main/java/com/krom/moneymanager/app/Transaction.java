package com.krom.moneymanager.app;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class Transaction {

    private String transactionName;
    private int expense;
    private int income;
    private int transactionCode;
    private int categoryNumber;
    private long transactionDate;
    private int totalTransaction;
    private String Day;
    private String Month;

    public Transaction(int transactionCode, String transactionName, int expense, int categoryNumber){
        this.transactionCode = transactionCode;
        this.transactionName = transactionName;
        totalTransaction = 0;
        this.expense = expense;
        this.categoryNumber = categoryNumber;


        Calendar calendar = Calendar.getInstance();
        Day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        Month = String.valueOf(calendar.get(Calendar.MONTH));
    }

    public int getCategoryNumber(){
        return categoryNumber;
    }

    public int getTotalTransaction(){
        return totalTransaction;
    }

    public void setTransactionDate(int transactionDate){
        this.transactionDate = (long) transactionDate;
    }

    public String getTransactionDate() {
        return Day + "/" + Month;
    }

    public String getDay(){
        return Day;
    }

    public String getMonth(){
        return Month;
    }

    public int getTransactionCode(){
        return transactionCode;
    }

    public String getTransactionName(){
        return transactionName;
    }

    public void setTransactionCode(int transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public void setExpense(int expense){
        this.expense = expense;
    }

    public int getExpense() {
        return expense;
    }
}
