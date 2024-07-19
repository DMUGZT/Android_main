package com.example.test;

public class Detail_Transaction {
    private String date;
    private String description;
    private String amount;

    public Detail_Transaction(String date, String description, String amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}