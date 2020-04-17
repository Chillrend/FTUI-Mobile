package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.viewholder.CategoryViewHolder;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.CategoryModel;
import com.rsypj.ftuimobile.ui.activity.UploadActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<CategoryModel> data = new ArrayList<>();
    int row_index = -1;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> data) {
        this.context = context;
        this.data = data;
    }

    public void updateList(ArrayList<CategoryModel> list){
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_category, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CategoryViewHolder){
            ((CategoryViewHolder) holder).setUPToUI(data.get(position));

            ((CategoryViewHolder) holder).rlKotak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index = position;
                    notifyDataSetChanged();
                }
            });

            if (row_index == position){
                ((CategoryViewHolder) holder).tvCategoryName.setTextColor(Color.BLUE);
                Helper.idKategori = data.get(position).getIdCategory();
                ((UploadActivity)context).getIvSend().setVisibility(View.VISIBLE);
            }else {
                ((CategoryViewHolder) holder).tvCategoryName.setTextColor(Color.parseColor("#616161"));
                ((CategoryViewHolder) holder).ivGambar.setColorFilter(Color.parseColor("#616161"), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
