package com.example.test;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.IncomeDAO;

public class Test_IncomeDAO extends AppCompatActivity  {

//    private IncomeDAO incomeDAO;
//    private long testIncomeId = -1;
//    private TextView tvResults;
//
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isDeleted = this.deleteDatabase("app.db");
        };
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testIncomeId != -1) {
//                    int rowsUpdated = incomeDAO.updateIncome(testIncomeId, "Salary", "2024-07-20", 5500.00, "Updated salary", "Cash");
//                    tvResults.setText("Rows Updated: " + rowsUpdated);
//                } else {
//                    tvResults.setText("Please add an income first.");
//                }
//            }
//        });
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testIncomeId != -1) {
//                    int rowsDeleted = incomeDAO.deleteIncome(testIncomeId);
//                    tvResults.setText("Rows Deleted: " + rowsDeleted);
//                    testIncomeId = -1;  // Reset the test ID after deletion
//                } else {
//                    tvResults.setText("Please add an income first.");
//                }
//            }
//        });
//
//        btnGetAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor cursor = incomeDAO.getAllIncomes();
//                StringBuilder results = new StringBuilder();
//                if (cursor.moveToFirst()) {
//                    do {
//                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INCOME_ID));
//                        String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INCOME_CATEGORY));
//                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INCOME_DATE));
//                        double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INCOME_AMOUNT));
//                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INCOME_DESCRIPTION));
//                        String cashType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INCOME_CASH_TYPE));
//
//                        results.append("ID: ").append(id)
//                                .append(", Category: ").append(category)
//                                .append(", Date: ").append(date)
//                                .append(", Amount: ").append(amount)
//                                .append(", Description: ").append(description)
//                                .append(", Cash Type: ").append(cashType)
//                                .append("\n");
//                    } while (cursor.moveToNext());
//                } else {
//                    results.append("No incomes found.");
//                }
//                cursor.close();
//                tvResults.setText(results.toString());
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        incomeDAO.close();
//    }
}
