package com.example.test;

public class MonthlySummary {
    private String month;
    private double income;
    private double expense;
    private double endBalance;

    public MonthlySummary(String month, double income, double expense, double endBalance) {
        this.month = month;
        this.income = income;
        this.expense = expense;
        this.endBalance = endBalance;
    }

    public String getMonth() {
        return month;
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public double getEndBalance() {
        return endBalance;
    }
}
