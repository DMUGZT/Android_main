package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LineChart lineChart = view.findViewById(R.id.lineChart);

        // 创建一个数据集
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1f, 2f));
        entries.add(new Entry(2f, 3f));
        entries.add(new Entry(3f, 5f));
        entries.add(new Entry(4f, 7f));

        LineDataSet dataSet = new LineDataSet(entries, "Sample Data");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        // 创建 LineData 对象
        LineData lineData = new LineData(dataSet);

        // 设置数据到图表中
        lineChart.setData(lineData);
        lineChart.invalidate(); // 刷新图表
    }
}
