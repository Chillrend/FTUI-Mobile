package com.rsypj.ftuimobile.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.CategoryModel;
import com.squareup.picasso.Picasso;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivGambar;
    public TextView tvCategoryName;
    public LinearLayout rlKotak;
    View view;

    public CategoryViewHolder(View view){
        super(view);
        this.view = view;
        ivGambar = view.findViewById(R.id.custom_category_image);
        tvCategoryName = view.findViewById(R.id.custom_category_tvTextview);
        rlKotak = view.findViewById(R.id.custom_category_Linear);

//        onTextTouch();
    }

    public void setUPToUI(CategoryModel data){
        tvCategoryName.setText(data.getKategori());
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getIcon())
                .into(ivGambar);
    }
}
