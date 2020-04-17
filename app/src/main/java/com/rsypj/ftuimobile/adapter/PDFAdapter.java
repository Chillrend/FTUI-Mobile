package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.viewholder.PDFViewHolder;
import com.rsypj.ftuimobile.model.ListBookModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class PDFAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<ListBookModel> data = new ArrayList<>();

    public PDFAdapter(Context context, ArrayList<ListBookModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_launcher_pdf, null);
        return new PDFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PDFViewHolder){
            ((PDFViewHolder) holder).setUpToUI(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
