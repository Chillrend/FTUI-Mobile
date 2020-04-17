package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rsypj.ftuimobile.model.NoTelphoneModel;

import java.util.ArrayList;

/**
 * Created Date: 21/05/2018
 * Author: dhadotid
 */
public class CallSpinnerAdapter extends ArrayAdapter<NoTelphoneModel> {

    Context context;
    ArrayList<NoTelphoneModel> data = new ArrayList<>();

    public CallSpinnerAdapter(Context context, int textViewResourceId, ArrayList<NoTelphoneModel> data){
        super(context, textViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public NoTelphoneModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextSize(20);
        label.setText(data.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextSize(20);
        label.setText(data.get(position).getName());

        return label;
    }
}
