package com.example.test;

public class Account {
    private int id;
    private String type;
    private double balance;


    public Account(int id, String type, double balance) {
        this.id = id;
        this.type = type;
        this.balance = balance;
//        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

//    public String getUserID() {
//        return userID;
//    }
}
