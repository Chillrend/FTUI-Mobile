package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.viewholder.ChatViewHolder;
import com.rsypj.ftuimobile.model.ChatModel;

import java.util.ArrayList;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<ChatModel> data = new ArrayList<>();

    public ChatAdapter(Context context, ArrayList<ChatModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_chat, null);

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = data.get(position);
        if (holder instanceof ChatViewHolder){
            ((ChatViewHolder) holder).sendDataToUI(chatModel);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}