package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.viewholder.LauncherRevisiViewHolder;
import com.rsypj.ftuimobile.model.LauncherModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class LauncherRevisiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<LauncherModel> data = new ArrayList<>();

    public LauncherRevisiAdapter(Context context, ArrayList<LauncherModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_launcher_revisi, null);
        return new LauncherRevisiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LauncherRevisiViewHolder){
            ((LauncherRevisiViewHolder) holder).setUpToUI(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
