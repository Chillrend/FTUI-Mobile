package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.listener.NewChatKotakClick;
import com.rsypj.ftuimobile.adapter.viewholder.NewChatViewHolder;
import com.rsypj.ftuimobile.model.UserModel;

import java.util.ArrayList;

public class NewChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<UserModel> data = new ArrayList<>();
    NewChatKotakClick listener;

    public NewChatAdapter(Context context, ArrayList<UserModel> data) {
        this.context = context;
        this.data = data;
    }

    public void updateList(ArrayList<UserModel> list){
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_new_chat, null);
        return new NewChatViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewChatViewHolder){
            ((NewChatViewHolder) holder).sendDataToUI(data.get(position));
        }
    }

    public void kotakClicked(NewChatKotakClick listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
