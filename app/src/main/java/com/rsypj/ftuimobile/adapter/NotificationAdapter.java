package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.viewholder.NotificationViewHolder;
import com.rsypj.ftuimobile.model.TimelineModel;

import java.util.ArrayList;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<TimelineModel> data = new ArrayList<>();

    public NotificationAdapter(Context context, ArrayList<TimelineModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification, null);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationViewHolder){
            ((NotificationViewHolder) holder).sendDataToUI(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
