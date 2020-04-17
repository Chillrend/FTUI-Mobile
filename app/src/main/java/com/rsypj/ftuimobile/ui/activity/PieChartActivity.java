package com.rsypj.ftuimobile.ui.activity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.PieChartController;

/**
 * Author By dhadotid
 * Date 29/06/2018.
 */
public class PieChartActivity extends SmartSipBaseActivity implements OnChartValueSelectedListener {

    ImageView ivBack;
    TextView tvTitle;
    PieChart pieChart;
    PieChartController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_chart;
    }

    @Override
    public void initItem() {
        ivBack = findViewById(R.id.activity_chart_ivBack);
        tvTitle = findViewById(R.id.activity_chart_tvNama);
        pieChart = findViewById(R.id.chart_pie);
        pieChart.setUsePercentValues(true);
        controller = new PieChartController(this);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        float fx = h.getX();
        int i = (int)Math.round(fx);
        Log.i("VALINT", i+"");
        controller.showAlertDialog(i);
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {

    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public PieChart getPieChart() {
        return pieChart;
    }
}
