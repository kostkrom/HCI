package com.krom.moneymanager.app;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class UserBalance extends ViewModel {

    private int balance;
    private int expenses;
    private int income;
    private ArrayList<Transaction> transactionsList = new ArrayList<>();

    public UserBalance(){}

    public UserBalance(int balance, int expenses, int income){
        this.balance = balance;
        this.expenses = expenses;
        this.income = income;
    }

    public ArrayList<Transaction> getTransactionsList(){
        return transactionsList;
    }

    public void addTransaction(Transaction transaction){
        transactionsList.add(transaction);
    }

    public void setTransactionsList(ArrayList arrayList){
        this.transactionsList = arrayList;
    }

    public void addIncome(int addMoney){
        balance += addMoney;
    }

    public void addExpence(int removeMoney){
        expenses += removeMoney;
        balance -= removeMoney;
    }

    public int getBalance() {
        return balance;
    }

    public int getExpenses(){
        return expenses;
    }

    public int getIncome(){
        return income;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String toString(int value){
        return String.valueOf(value);
    }
}
