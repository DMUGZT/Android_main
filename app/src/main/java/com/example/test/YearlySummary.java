    // YearlySummary.java
    package com.example.test;

    public class YearlySummary {
        private String year;
        private double income;
        private double expense;
        private double endBalance;

        public YearlySummary(String year, double income, double expense, double endBalance) {
            this.year = year;
            this.income = income;
            this.expense = expense;
            this.endBalance = endBalance;
        }

        public String getYear() {
            return year;
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
