package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        //添加图表组件
        lineChart = view.findViewById(R.id.lineChart);
        barChart = view.findViewById(R.id.barChart);
        pieChart = view.findViewById(R.id.pieChart);

        //初始化图表数据
        Init();

        //添加监听
        spinnerType = (Spinner) (view.findViewById(R.id.spinnerType));
        spinnerChart = (Spinner) (view.findViewById(R.id.spinnerChart));

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

        return view;
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
                        pieChart.setData(pieData_income);
                        pieChart.getDescription().setText("收入分析图");
                        break;
                    case Expend:
                        pieChart.setData(pieData_expend);
                        pieChart.getDescription().setText("支出分析图");
                        break;
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
        //折线图
        {
            //收入
            List<Entry> lineEntriesIncome = new ArrayList<>();
            //todo 添加数据
            lineEntriesIncome.add(new Entry(1f, 2f));

            LineDataSet lineDataSet_incmoe = new LineDataSet(lineEntriesIncome, "收入数据");
            lineDataSet_incmoe.setColor(Color.BLUE);
            lineDataSet_incmoe.setValueTextColor(Color.BLACK);

            //支出
            List<Entry> lineEntriesExpend = new ArrayList<>();
            //todo 添加数据
            lineEntriesExpend.add(new Entry(1f, 2f));

            LineDataSet lineDataSet_expend = new LineDataSet(lineEntriesExpend, "支出数据");
            lineDataSet_expend.setColor(Color.BLUE);
            lineDataSet_expend.setValueTextColor(Color.BLACK);

            //收支平衡
            List<Entry> lineEntriesIncomeAndExpend = new ArrayList<>();
            //todo 添加数据
            lineEntriesIncomeAndExpend.add(new Entry(1f, 2f));

            LineDataSet lineDataSet_incomeAndExpend = new LineDataSet(lineEntriesIncomeAndExpend, "收支数据");
            lineDataSet_incomeAndExpend.setColor(Color.BLUE);
            lineDataSet_incomeAndExpend.setValueTextColor(Color.BLACK);

            lineData_income = new LineData(lineDataSet_incmoe);
            lineData_expend = new LineData(lineDataSet_expend);
            lineData_incomeAndExpend = new LineData(lineDataSet_incomeAndExpend);

        }
        //条形图
        {
            //收入
            List<BarEntry> barEntriesIncome = new ArrayList<>();
            //todo 添加数据


            BarDataSet barDataSet_income = new BarDataSet(barEntriesIncome, "收入数据");
            barDataSet_income.setColor(Color.BLUE);
            barDataSet_income.setValueTextColor(Color.BLACK);

            //支出
            List<BarEntry> barEntriesExpend = new ArrayList<>();
            //todo 添加数据


            BarDataSet barDataSet_expend = new BarDataSet(barEntriesExpend, "支出数据");
            barDataSet_expend.setColor(Color.BLUE);
            barDataSet_expend.setValueTextColor(Color.BLACK);

            //收支
            List<BarEntry> barEntriesIncomeAndExpend = new ArrayList<>();
            //todo 添加数据


            BarDataSet barDataSet_incomeAndExpend = new BarDataSet(barEntriesIncomeAndExpend, "收支平衡");
            barDataSet_incomeAndExpend.setColor(Color.BLUE);
            barDataSet_incomeAndExpend.setValueTextColor(Color.BLACK);

            barData_income = new BarData(barDataSet_income);
            barData_expend = new BarData(barDataSet_expend);
            barData_incomeAndExpend = new BarData(barDataSet_incomeAndExpend);
        }
        //饼图
        {
            //收入
            List<PieEntry> pieEntriesIncome = new ArrayList<>();
            //todo 添加数据

            PieDataSet pieDataSet_income = new PieDataSet(pieEntriesIncome, "收入数据");
            pieDataSet_income.setColor(Color.BLUE);
            pieDataSet_income.setValueTextColor(Color.BLACK);

            //支出
            List<PieEntry> pieEntriesExpend = new ArrayList<>();
            //todo 添加数据

            PieDataSet pieDataSet_expend = new PieDataSet(pieEntriesExpend, "支出数据");
            pieDataSet_expend.setColor(Color.BLUE);
            pieDataSet_expend.setValueTextColor(Color.BLACK);

            //收支
            List<PieEntry> pieEntriesIncomeAndExpend = new ArrayList<>();
            //todo 添加数据

            PieDataSet pieDataSet_incomeAndExpend = new PieDataSet(pieEntriesIncomeAndExpend, "收支数据");
            pieDataSet_incomeAndExpend.setColor(Color.BLUE);
            pieDataSet_incomeAndExpend.setValueTextColor(Color.BLACK);

            pieData_income = new PieData(pieDataSet_income);
            pieData_expend = new PieData(pieDataSet_expend);
            pieData_incomeAndExpend = new PieData(pieDataSet_incomeAndExpend);
        }
    }
}
