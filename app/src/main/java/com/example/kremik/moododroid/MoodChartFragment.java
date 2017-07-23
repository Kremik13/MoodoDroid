package com.example.kremik.moododroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoodChartFragment extends Fragment {

    private static final String TAG = "MoodChartFragment";
    private LineChart mLineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_chart, container, false);
        mLineChart = (LineChart) view.findViewById(R.id.line_chart_moods);
        setupLineChart();
        Log.d(TAG, "onCreate: Starting.");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupLineChart();
    }

    private void setupLineChart() {
        if ( MoodLab.getInstance(getActivity()).size() != 0 ) {
            ArrayList<String> xAxes = new ArrayList<>();
            ArrayList<Entry> yAxisMoods = new ArrayList<>();
            List<MoodLog> moodList = MoodLab.getInstance(getActivity()).getMoodLogs();
            double x = 0;
            for (int i = 0; i < MoodLab.getInstance(getActivity()).size(); i++) {
                int mood = 0;

                yAxisMoods.add(new Entry(i, moodList.get(i).getMood()));

                x++;
                xAxes.add(i, String.valueOf(x));
            }
            String[] xaxes = new String[xAxes.size()];
            for (int i = 0; i < xAxes.size(); i++) {
                xaxes[i] = xAxes.get(i).toString();
            }
            LineDataSet lineDataSet = new LineDataSet(yAxisMoods,"mood");
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawValues(false);
            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            mLineChart.setData(new LineData(lineDataSet));
            mLineChart.setTouchEnabled(false);
            mLineChart.getXAxis().setDrawGridLines(false);
            mLineChart.setDescription(null);
            mLineChart.getLegend().setEnabled(false);
            mLineChart.getAxisRight().setDrawLabels(false);
            YAxis leftAxis = mLineChart.getAxisLeft();
        } else {
            mLineChart.setBackgroundColor(Color.RED);
        }
    }

}
