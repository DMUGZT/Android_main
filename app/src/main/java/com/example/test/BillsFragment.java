package com.example.test;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<BillsMonth> BillsMonthList;
    private RecyclerView recyclerView;
    private BillsFragmentAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillsFragment
    newInstance(String param1, String param2) {
        BillsFragment fragment = new BillsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills,container,false);
        TextView Textview1=(TextView) (view.findViewById(R.id.textView1));
        TextView Textview2=(TextView) (view.findViewById(R.id.textView2));
        TextView Textview3=(TextView) (view.findViewById(R.id.textView3));
        TextView Textview4=(TextView) (view.findViewById(R.id.textView4));

        (view.findViewById(R.id.radioButton1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Textview1.setText("月份");
           Textview2.setText("月收入");
           Textview3.setText("月支出");
           Textview4.setText("月结余");
                BillsMonthList = new ArrayList<>();
                adapter = new BillsFragmentAdapter(BillsMonthList);
                recyclerView.setAdapter(adapter);
//                LoadData();
            }
        });
        (view.findViewById(R.id.radioButton2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Textview1.setText("年份");
                Textview2.setText("年收入");
                Textview3.setText("年支出");
                Textview4.setText("年结余");
            }
        });
        return view;
    }
//    private void LoadData(){
//        Cursor cursor = incomeDAO.getIncomeById(Integer.parseInt(userId));
//
//        transactionList.clear();
//        incomeTotal = 0.0;
//        paymentTotal = 0.0;
//
//        if (cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
//                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
//                @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
//                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
//
//                Detail_Transaction transaction = new Detail_Transaction(id, category, date, amount, description, cashType);
//                transactionList.add(transaction);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        for (Detail_Transaction transaction : transactionList) {
//            if ("入账".equals(transaction.getCategory())) {
//                incomeTotal += transaction.getAmount();
//            } else if ("支出".equals(transaction.getCategory())) {
//                paymentTotal += transaction.getAmount();
//            }
//        }
//
//        incomeTextView.setText(String.format("%.2f", incomeTotal));
//        paymentTextView.setText(String.format("%.2f", paymentTotal));
//        adapter.notifyDataSetChanged();
//    }
}