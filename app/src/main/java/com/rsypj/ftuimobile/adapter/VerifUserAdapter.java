package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.listener.ButtonVerifUser;
import com.rsypj.ftuimobile.adapter.viewholder.VerifUserViewHolder;
import com.rsypj.ftuimobile.model.UnverifiedUserModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class VerifUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ButtonVerifUser listener;
    ArrayList<UnverifiedUserModel> data = new ArrayList<>();

    public VerifUserAdapter(Context context, ArrayList<UnverifiedUserModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_verif_user, null);
        return new VerifUserViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VerifUserViewHolder){
            ((VerifUserViewHolder) holder).setUpToUI(data.get(position));
        }
    }

    public UnverifiedUserModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clickedVerifUser(ButtonVerifUser listener){
        this.listener = listener;
    }
}
