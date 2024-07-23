package com.example.test;

public class Detail_Transaction {
    private int id;
    private String category;
    private String date;
    private double amount;
    private String description;
    private String cashType;
    public Detail_Transaction(int id, String category, String date, double amount, String description, String cashType) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.cashType = cashType;
    }
    // Getters and Setters

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {return category;}
//    public double getAmount() {return amount;}
    // ...
}