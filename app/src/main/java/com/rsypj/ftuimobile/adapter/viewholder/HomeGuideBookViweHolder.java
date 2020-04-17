package com.rsypj.ftuimobile.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ListBookModel;
import com.rsypj.ftuimobile.ui.activity.AcademicGuideBookActivity;
import com.rsypj.ftuimobile.ui.activity.PDFReaderActivity;
import com.rsypj.ftuimobile.ui.fragment.AcademicGuideBookFragment;
import com.squareup.picasso.Picasso;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class HomeGuideBookViweHolder extends RecyclerView.ViewHolder {

    TextView tvJudul;
    ImageView ivImage;
    ListBookModel data;

    public HomeGuideBookViweHolder(View view){
        super(view);
        tvJudul = view.findViewById(R.id.custom_home_guide_book_tvJudul);
        ivImage = view.findViewById(R.id.custom_home_guide_book_image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                onClicked(pos);
            }
        });
    }

    private void onClicked(int pos){
        if (pos != RecyclerView.NO_POSITION){
            //Intent in = new Intent(itemView.getContext(), PDFDetailActivity.class);
            //Intent in = new Intent(itemView.getContext(), PDFReaderActivity.class); // aslinya
            //coba  AcademicGuideBookActivity
            Intent in = new Intent(itemView.getContext(), AcademicGuideBookActivity.class);
            in.putExtra(Helper.DATA, data.getId()+"");
            in.putExtra(Helper.DATANAME, data.getNama());

            Log.d("viewholder.getid", String.valueOf(data.getId()));
            Log.d("viewholder.getname", data.getNama());

            itemView.getContext().startActivity(in);
        }
    }

    public void setUpToUI(ListBookModel data){
        this.data = data;
        tvJudul.setText(data.getNama());
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getGambar())
                .fit()
                .centerInside()
                .into(ivImage);
    }
}
