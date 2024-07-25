package com.example.test;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;


import androidx.fragment.app.Fragment;

import com.example.test.database.IncomeDAO;
import com.example.test.utils.UserSessionManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    private enum E_Type{
        Income,//收入
        Expend,//支出
        IncomeAndExpend//收支平衡
    }
    private enum E_Chart{
        Line,//折线图
        Bar,//条状图
        Pie//饼图
    }

    //当前图表类型
    private E_Type currentType = E_Type.Income;
    private E_Chart currentChart = E_Chart.Line;
    //下拉框组件
    private Spinner spinnerYear;
    private Spinner spinnerMonth;
    private Spinner spinnerType;
    private Spinner spinnerChart;

    //图标相关
    private LineChart lineChart;
    private LineData lineData_income;
    private LineData lineData_expend;
    private LineData lineData_incomeAndExpend;

    private BarChart barChart;
    private BarData barData_income;
    private BarData barData_expend;
    private BarData barData_incomeAndExpend;

    private PieChart pieChart;
    private PieData pieData_income;
    private PieData pieData_expend;
    private PieData pieData_incomeAndExpend;

    //数据库
    private IncomeDAO incomeDAO;
    private UserSessionManager sessionManager;
    private String yearStr="2024";
    private String monthStr="7";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        //添加图表组件
        lineChart = view.findViewById(R.id.lineChart);
        barChart = view.findViewById(R.id.barChart);
        pieChart = view.findViewById(R.id.pieChart);

        //数据库
        incomeDAO = new IncomeDAO(getContext());
        sessionManager = new UserSessionManager(getContext());

        //初始化图表数据
        Init();

        //添加监听
        spinnerType = (Spinner) (view.findViewById(R.id.spinnerType));
        spinnerChart = (Spinner) (view.findViewById(R.id.spinnerChart));
        spinnerYear = (Spinner) (view.findViewById(R.id.spinnerYear));
        spinnerMonth = (Spinner) (view.findViewById(R.id.spinnerMonth));

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选择后
                switch ((int)id){
                    case 0:
                        currentType= E_Type.Income;
                        break;
                    case 1:
                        currentType=E_Type.Expend;
                        break;
                    case 2:
                        currentType=E_Type.IncomeAndExpend;
                        break;

                }
                UpdateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerChart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选择后
                switch ((int)id){
                    case 0:
                        currentChart=E_Chart.Line;
                        break;
                    case 1:
                        currentChart=E_Chart.Bar;
                        break;
                    case 2:
                        currentChart=E_Chart.Pie;
                        break;

                }
                UpdateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选择后
                switch ((int)id){
                    case 0:
                        yearStr = "2018";
                        break;
                    case 1:
                        yearStr = "2019";
                        break;
                    case 2:
                        yearStr = "2020";
                        break;
                    case 3:
                        yearStr = "2021";
                        break;
                    case 4:
                        yearStr = "2022";
                        break;
                    case 5:
                        yearStr = "2023";
                        break;
                    case 6:
                        yearStr = "2024";
                        break;
                    case 7:
                        yearStr = "2025";
                        break;
                    case 8:
                        yearStr = "2026";
                        break;
                    case 9:
                        yearStr = "2027";
                        break;
                    case 10:
                        yearStr = "2028";
                        break;
                    case 11:
                        yearStr = "2029";
                        break;
                }
                Init();
                UpdateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选择后
                switch ((int)id){
                    case 0:
                        monthStr = "1";
                        break;
                    case 1:
                        monthStr = "2";
                        break;
                    case 2:
                        monthStr = "3";
                        break;
                    case 3:
                        monthStr = "4";
                        break;
                    case 4:
                        monthStr = "5";
                        break;
                    case 5:
                        monthStr = "6";
                        break;
                    case 6:
                        monthStr = "7";
                        break;
                    case 7:
                        monthStr = "8";
                        break;
                    case 8:
                        monthStr = "9";
                        break;
                    case 9:
                        monthStr = "10";
                        break;
                    case 10:
                        monthStr = "11";
                        break;
                    case 11:
                        monthStr = "12";
                        break;
                }
                Init();
                UpdateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Init();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    //更新图表
    private void UpdateChart(){

        switch(currentChart){
            case Line:
                lineChart.setVisibility(View.VISIBLE);
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                switch (currentType){
                    case Income:
                        lineChart.setData(lineData_income);
                        lineChart.getDescription().setText("收入分析图");
                        break;
                    case Expend:
                        lineChart.setData(lineData_expend);
                        lineChart.getDescription().setText("支出分析图");
                        break;
                    case IncomeAndExpend:
                        lineChart.setData(lineData_incomeAndExpend);
                        lineChart.getDescription().setText("收支分析图");
                        break;
                }
                lineChart.invalidate();
                break;
            case Bar:
                lineChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                switch (currentType){
                    case Income:
                        barChart.setData(barData_income);
                        barChart.getDescription().setText("收入分析图");
                        break;
                    case Expend:
                        barChart.setData(barData_expend);
                        barChart.getDescription().setText("支出分析图");
                        break;
                    case IncomeAndExpend:
                        barChart.setData(barData_incomeAndExpend);
                        barChart.getDescription().setText("收支分析图");
                        break;
                }
                barChart.invalidate();
                break;
            case Pie:
                lineChart.setVisibility(View.GONE);
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                switch (currentType){
                    case Income:
//                        pieChart.setData(pieData_income);
//                        pieChart.getDescription().setText("收入分析图");
                        //break;
                    case Expend:
//                        pieChart.setData(pieData_expend);
//                        pieChart.getDescription().setText("支出分析图");
                        //break;
                    case IncomeAndExpend:
                        pieChart.setData(pieData_incomeAndExpend);
                        pieChart.getDescription().setText("收支分析图");
                        break;
                }
                pieChart.invalidate();
                break;
        }
    }
    //初始化图表
    private void Init(){

        int dayNum =31;

        //折线图数据
        List<Entry> lineEntriesIncome = new ArrayList<>();
        for(int i=0;i<dayNum;i++)lineEntriesIncome.add(new Entry(i+1,0f));
        List<Entry> lineEntriesExpend = new ArrayList<>();
        for(int i=0;i<dayNum;i++)lineEntriesExpend.add(new Entry(i+1,0f));
        List<Entry> lineEntriesIncomeAndExpend = new ArrayList<>();
        for(int i=0;i<dayNum;i++)lineEntriesIncomeAndExpend.add(new Entry(i+1,0f));
        //条形图数据
        List<BarEntry> barEntriesIncome = new ArrayList<>();
        for(int i=0;i<dayNum;i++)barEntriesIncome.add(new BarEntry(i+1,0f));
        List<BarEntry> barEntriesExpend = new ArrayList<>();
        for(int i=0;i<dayNum;i++)barEntriesExpend.add(new BarEntry(i+1,0f));
        List<BarEntry> barEntriesIncomeAndExpend = new ArrayList<>();
        for(int i=0;i<dayNum;i++)barEntriesIncomeAndExpend.add(new BarEntry(i+1,0f));
        //饼图数据
//        List<PieEntry> pieEntriesIncome = new ArrayList<>();
//        for(int i=0;i<dayNum;i++)pieEntriesIncome.add(new PieEntry(0f));
//        List<PieEntry> pieEntriesExpend = new ArrayList<>();
//        for(int i=0;i<dayNum;i++)pieEntriesExpend.add(new PieEntry(0f));
        List<PieEntry> pieEntriesIncomeAndExpend = new ArrayList<>();
        for(int i=0;i<dayNum;i++)pieEntriesIncomeAndExpend.add(new PieEntry(0f));

        //读取数据库
        Cursor cursor = incomeDAO.getIncomeByYearAndMonth(sessionManager.getUserId(),yearStr,monthStr);

        String[] strs;
        int day;
        Entry entry;
        BarEntry barEntry;
        PieEntry pieEntry;

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

                strs = date.split("-");
                if(strs.length>0)
                    day = Integer.parseInt(strs[strs.length-1]);
                else
                    day = 0;

                if(day !=0){
                    if(description.equals("入账"))
                    {
                        //折线图
                        entry = lineEntriesIncome.get(day-1);
                        entry.setY(entry.getY()+(float) amount);
                        entry = lineEntriesIncomeAndExpend.get(day-1);
                        entry.setY(entry.getY()+(float) amount);
                        //条形图
                        barEntry = barEntriesIncome.get(day-1);
                        barEntry.setY(barEntry.getY()+(float) amount);
                        barEntry = barEntriesIncomeAndExpend.get(day-1);
                        barEntry.setY(barEntry.getY()+(float) amount);
                        //饼图
//                        pieEntry = pieEntriesIncome.get(day-1);
//                        pieEntry.setY(pieEntry.getY()+(float) amount);
                        pieEntry = pieEntriesIncomeAndExpend.get(day-1);
                        pieEntry.setY(pieEntry.getY()+(float) amount);
                    }
                    else if(description.equals("支出"))
                    {
                        //折线图
                        entry = lineEntriesExpend.get(day-1);
                        entry.setY(entry.getY()+(float) amount);
                        entry = lineEntriesIncomeAndExpend.get(day-1);
                        entry.setY(entry.getY()-(float) amount);
                        //条形图
                        barEntry = barEntriesExpend.get(day-1);
                        barEntry.setY(barEntry.getY()+(float) amount);
                        barEntry = barEntriesIncomeAndExpend.get(day-1);
                        barEntry.setY(barEntry.getY()-(float) amount);
                        //饼图
//                        pieEntry = pieEntriesExpend.get(day-1);
//                        pieEntry.setY(pieEntry.getY()+(float) amount);
                        pieEntry = pieEntriesIncomeAndExpend.get(day-1);
                        pieEntry.setY(pieEntry.getY()-(float) amount);
                    }
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        //更新饼图
        float y;
        int i=0;
        int[] colors=new int[dayNum];
        for (PieEntry pie_entry : pieEntriesIncomeAndExpend){
            y = pie_entry.getY();
            if(y >= 0f){
                colors[i++]=Color.BLUE;
            }else{
                pie_entry.setY(-y);
                colors[i++]=Color.RED;
            }
        }

        //设置图表数据
        {
            //折线图
            //收入
            LineDataSet lineDataSet_incmoe = new LineDataSet(lineEntriesIncome, "收入数据");
            lineDataSet_incmoe.setColor(Color.GREEN);
            lineDataSet_incmoe.setValueTextColor(Color.BLACK);

            //支出
            LineDataSet lineDataSet_expend = new LineDataSet(lineEntriesExpend, "支出数据");
            lineDataSet_expend.setColor(Color.RED);
            lineDataSet_expend.setValueTextColor(Color.BLACK);

            //收支平衡
            LineDataSet lineDataSet_incomeAndExpend = new LineDataSet(lineEntriesIncomeAndExpend, "收支数据");
            lineDataSet_incomeAndExpend.setColor(Color.YELLOW);
            lineDataSet_incomeAndExpend.setValueTextColor(Color.BLACK);


            //条形图
            //收入
            BarDataSet barDataSet_income = new BarDataSet(barEntriesIncome, "收入数据");
            barDataSet_income.setColor(Color.GREEN);
            barDataSet_income.setValueTextColor(Color.BLACK);

            //支出
            BarDataSet barDataSet_expend = new BarDataSet(barEntriesExpend, "支出数据");
            barDataSet_expend.setColor(Color.RED);
            barDataSet_expend.setValueTextColor(Color.BLACK);

            //收支
            BarDataSet barDataSet_incomeAndExpend = new BarDataSet(barEntriesIncomeAndExpend, "收支数据");
            barDataSet_incomeAndExpend.setColor(Color.YELLOW);
            barDataSet_incomeAndExpend.setValueTextColor(Color.BLACK);


            //饼图
//            //收入
//            PieDataSet pieDataSet_income = new PieDataSet(pieEntriesIncome, "收入数据");
//            pieDataSet_income.setColor(Color.GREEN);
//            pieDataSet_income.setValueTextColor(Color.BLACK);
//
//            //支出
//            PieDataSet pieDataSet_expend = new PieDataSet(pieEntriesExpend, "支出数据");
//            pieDataSet_expend.setColor(Color.RED);
//            pieDataSet_expend.setValueTextColor(Color.BLACK);

            //收支
            PieDataSet pieDataSet_incomeAndExpend = new PieDataSet(pieEntriesIncomeAndExpend, "收支数据");
            pieDataSet_incomeAndExpend.setColors(colors);

            //折线图
            lineData_income = new LineData(lineDataSet_incmoe);
            lineData_expend = new LineData(lineDataSet_expend);
            lineData_incomeAndExpend = new LineData(lineDataSet_incomeAndExpend);
            //条形图
            barData_income = new BarData(barDataSet_income);
            barData_expend = new BarData(barDataSet_expend);
            barData_incomeAndExpend = new BarData(barDataSet_incomeAndExpend);
            //饼图
//            pieData_income = new PieData(pieDataSet_income);
//            pieData_expend = new PieData(pieDataSet_expend);
            pieData_incomeAndExpend = new PieData(pieDataSet_incomeAndExpend);
        }

    }
}
