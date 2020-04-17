package com.rsypj.ftuimobile.adapter.viewholder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.PDFAdapter;
import com.rsypj.ftuimobile.connection.LauncherRequest;
import com.rsypj.ftuimobile.connection.listener.PDFCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.LauncherModel;
import com.rsypj.ftuimobile.model.ListBookModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherBodyViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    ImageView ivDetail;
    View line;
    LinearLayout wrapperDeskripsi;
    TextView tvDeskripsi;
    RecyclerView listPdf;
    ImageView ivIcon;
    PDFAdapter adapter;
    ArrayList<ListBookModel> dataPDF = new ArrayList<>();
    boolean expand = false;

    public LauncherBodyViewHolder(View view){
        super(view);

        tvTitle = view.findViewById(R.id.launcher_title2);
        ivDetail = view.findViewById(R.id.launcher_ivDetail2);
        line = view.findViewById(R.id.launcher_line2);
        wrapperDeskripsi = view.findViewById(R.id.launcher_wrapper_deskripsi2);
        tvDeskripsi = view.findViewById(R.id.launcher_tvDeskripsi2);
        listPdf = view.findViewById(R.id.list_pdf1);
        ivIcon = view.findViewById(R.id.launcher_image2);
        setAdapter();
    }

    public void setUpToUI(LauncherModel data){
        tvTitle.setText(data.getJudul());
        tvDeskripsi.setText(data.getDeskripsi());
        onRequestPDF(data.getId()+"");
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getGambar())
                .into(ivIcon);

        ivDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expand){
                    ivDetail.animate().rotation(180).start();
                    wrapperDeskripsi.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    wrapperDeskripsi.setAlpha(0.0f);
                    line.setAlpha(0.0f);

                    wrapperDeskripsi.animate()
                            .translationY(10)
                            .alpha(1.0f)
                            .setListener(null);
                    line.animate()
                            .translationY(10)
                            .alpha(1.0f)
                            .setListener(null);
                    expand = true;
                }else {
                    ivDetail.animate().rotation(0).start();
                    line.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    line.setVisibility(View.GONE);
                                }
                            });
                    wrapperDeskripsi.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    wrapperDeskripsi.setVisibility(View.GONE);
                                }
                            });
                    expand = false;
                }
            }
        });
    }

    private void setAdapter(){
        adapter = new PDFAdapter(itemView.getContext(), dataPDF);
        listPdf.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        listPdf.setItemAnimator(new DefaultItemAnimator());
        listPdf.setAdapter(adapter);
    }

    private void onRequestPDF(String id){
        Helper.idlauncher = id;
        new LauncherRequest(itemView.getContext()).onRequestDataPDF(new PDFCallBack() {
            @Override
            public void onDataSetResult(ArrayList<ListBookModel> response) {
                dataPDF.clear();
                dataPDF.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDataIsEmpty() {

            }

            @Override
            public void onRequestError(String errorMessage) {
                Toast.makeText(itemView.getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
