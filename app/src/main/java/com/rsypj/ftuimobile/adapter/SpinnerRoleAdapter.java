package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rsypj.ftuimobile.model.RoleModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 6/07/2018.
 */
public class SpinnerRoleAdapter extends ArrayAdapter<RoleModel> {

    Context context;
    ArrayList<RoleModel> data = new ArrayList<>();

    public SpinnerRoleAdapter(Context context, int textViewResourceId, ArrayList<RoleModel> data){
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
    public RoleModel getItem(int position) {
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
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        label.setTextColor(Color.WHITE);
        label.setTypeface(font);
        label.setText(data.get(position).getRole());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        label.setTextColor(Color.BLACK);
        label.setTypeface(font);
        label.setText(data.get(position).getRole());
        return label;
    }
}
