package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.listener.ButtonApproved;
import com.rsypj.ftuimobile.adapter.viewholder.ListPendingPostViewHolder;
import com.rsypj.ftuimobile.model.ListPendingPostModel;

import java.util.ArrayList;

public class ListPendingPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<ListPendingPostModel> data;
    ButtonApproved listener;

    public ListPendingPostAdapter(Context context, ArrayList<ListPendingPostModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_manager, null);
        return new ListPendingPostViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListPendingPostViewHolder){
            ((ListPendingPostViewHolder) holder).setUpToUI(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void buttonApproveClicked(ButtonApproved listener){
        this.listener = listener;
    }
}
