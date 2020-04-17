package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.viewholder.LauncherBodyViewHolder;
import com.rsypj.ftuimobile.adapter.viewholder.LauncherHeaderViewHolder;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.LauncherModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<LauncherModel> data = new ArrayList<>();

    public LauncherAdapter(Context context, ArrayList<LauncherModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == Helper.HEADER){
            return Helper.HEADER;
        }else {
            return Helper.BODY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == Helper.HEADER){
            view = LayoutInflater.from(context).inflate(R.layout.custom_launcher_1, null);
            return new LauncherHeaderViewHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.custom_launcher_2, null);
            return new LauncherBodyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LauncherHeaderViewHolder){
            ((LauncherHeaderViewHolder) holder).setUpToUI(data.get(position));
        }else if (holder instanceof LauncherBodyViewHolder){
            ((LauncherBodyViewHolder) holder).setUpToUI(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
