package com.rsypj.ftuimobile.ui.activity.controller;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rsypj.ftuimobile.connection.KeluhanDetailRequest;
import com.rsypj.ftuimobile.connection.listener.ChartCallBack;
import com.rsypj.ftuimobile.model.PieChartModel;
import com.rsypj.ftuimobile.ui.activity.PieChartActivity;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 29/06/2018.
 */
public class PieChartController {

    PieChartActivity activity;
    PieDataSet dataSet;
    PieData pieData;
    ArrayList<PieChartModel> data = new ArrayList<>();

    public PieChartController(PieChartActivity activity) {
        this.activity = activity;

        customActionBar();
        onRequestData();
    }

    private void customActionBar(){
        activity.hideActionBar();
        activity.getTvTitle().setText("Persentasi Posting");
        activity.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onRequestData(){
        new KeluhanDetailRequest(activity).onRequestChartData(new ChartCallBack() {
            @Override
            public void onDataSetResult(ArrayList<PieChartModel> response) {
                data.addAll(response);
                createDataSet();
            }

            @Override
            public void onDataIsEmpty() {
                activity.showToast("Data not found!");
                createDataSet();
            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
            }
        });
    }

    private void createDataSet(){
        activity.getPieChart().setUsePercentValues(true);
        activity.getPieChart().getDescription().setEnabled(false);
        activity.getPieChart().setExtraOffsets(5, 10, 5, 5);
        activity.getPieChart().setDragDecelerationFrictionCoef(0.95f);
        activity.getPieChart().setCenterText(generateCenterSpannableText());
        activity.getPieChart().setDrawHoleEnabled(true);
        activity.getPieChart().setHoleColor(Color.WHITE);
        activity.getPieChart().setTransparentCircleColor(Color.WHITE);
        activity.getPieChart().setTransparentCircleAlpha(110);
        activity.getPieChart().setHoleRadius(58f);
        activity.getPieChart().setTransparentCircleRadius(61f);
        activity.getPieChart().setDrawCenterText(true);
        activity.getPieChart().setRotationAngle(0);
        activity.getPieChart().setRotationEnabled(true);
        activity.getPieChart().setHighlightPerTapEnabled(true);
        activity.getPieChart().setOnChartValueSelectedListener(activity);

        ArrayList<PieEntry> yvalues = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            yvalues.add(new PieEntry(data.get(i).getPersentase(), data.get(i).getName()));
        }

        dataSet = new PieDataSet(yvalues, "Persentasi Keluhan");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.DKGRAY);
        activity.getPieChart().setData(pieData);
        activity.getPieChart().animateXY(1400,1400);
        activity.getPieChart().highlightValues(null);
        activity.getPieChart().invalidate();

        Legend l = activity.getPieChart().getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        activity.getPieChart().setEntryLabelColor(Color.DKGRAY);
        activity.getPieChart().setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Chart Persentasi\nKeluhan Smart SIP");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 16, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 16, s.length() - 10, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 16, s.length() - 10, 0);
        s.setSpan(new RelativeSizeSpan(1f), 16, s.length() - 10, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 9, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1.7f), s.length() - 9, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 9, s.length(), 0);
        return s;
    }

    public void showAlertDialog(int index){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Jumlah Post");
        alertDialog.setMessage("Jumlah Post untuk " + data.get(index).getName() + " adalah " + data.get(index).getJumlah());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
