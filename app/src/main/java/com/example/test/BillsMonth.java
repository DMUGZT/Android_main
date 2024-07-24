package com.example.test;

public class BillsMonth {
    private int id;
    private double income;
    private String date;
    private double balance;
    private double expense;
    private String cashType;
    public BillsMonth(int id, double income, String date, double balance, double expense, String cashType) {
        this.id = id;
        this.income = income;
        this.date = date;
        this.expense = expense;
        this.balance = balance;
        this.cashType = cashType;
    }
    // Getters and Setters

    public String getMonth() {
        return date;
    }

    public double getMonthIncome() {
        return income;
    }

    public double getMonthExpense() {
        return expense;
    }

    public double getMonthBalance() {return balance;}
//    public double getAmount() {return amount;}
    // ...
}
