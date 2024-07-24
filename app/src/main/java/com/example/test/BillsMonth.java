package com.example.test;

public class BillsMonth {
    private double income;
    private String date;
    private double balance;
    private double expense;

    public BillsMonth( double income,double expense,String date, double balance) {
        this.income = income;
        this.date = date;
        this.expense = expense;
        this.balance = balance;
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

    public double getMonthBalance() {
        return balance;
    }
}
