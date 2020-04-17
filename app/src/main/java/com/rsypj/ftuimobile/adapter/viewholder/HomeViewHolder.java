package com.rsypj.ftuimobile.adapter.viewholder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hsalf.smilerating.SmileRating;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.connection.KeluhanDetailRequest;
import com.rsypj.ftuimobile.connection.RatingRequest;
import com.rsypj.ftuimobile.connection.listener.KeluhanDetailCallBack;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.TimelineModel;
import com.squareup.picasso.Picasso;

import static com.rsypj.ftuimobile.helper.CalenderHelper.convertLongToTime;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {

    ProgressBar pBarStatus;
    TextView tvName;
    TextView tvTime;
    TextView tvStatus;
    TextView tvLoc;
    ImageView ivStatus;
    TextView tvCategory;
    ImageView ivCategory;
    Button btnIsStatusPost;
    TimelineModel data;

    public HomeViewHolder(View view){
        super(view);

        tvName = view.findViewById(R.id.custom_home_name);
        tvTime = view.findViewById(R.id.custom_home_time);
        tvStatus = view.findViewById(R.id.custom_home_status);
        tvCategory = view.findViewById(R.id.home_category_tv);
        ivCategory = view.findViewById(R.id.home_category_image);
        tvLoc = view.findViewById(R.id.custom_home_location);
        ivStatus = view.findViewById(R.id.custom_home_ivGambar);
        btnIsStatusPost = view.findViewById(R.id.custom_home_btnStatusPost);
        pBarStatus = view.findViewById(R.id.home_pBar_status);
    }

    public void sendDataToUI(final TimelineModel data){
        this.data = data;
        tvName.setText(data.getName());
        tvTime.setText(convertLongToTime(Long.parseLong(data.getTimePost())));
        tvStatus.setText(data.getDescription());
        tvCategory.setText(data.getCategory()+"");
        tvLoc.setText(data.getLocation());
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getCategoryIcon())
                .into(ivCategory);


        Glide.with(itemView.getContext())
                .load(URL.image + data.getFoto())
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pBarStatus.setVisibility(View.GONE);
                        Log.e("TAG", "handle error case", e);
                        Toast.makeText(itemView.getContext(), "handle error case" + e.getMessage(), Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        Log.e("TAG", "handle error case", e);

                        pBarStatus.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivStatus);
        if (data.getIs_approved() == 1){
            btnIsStatusPost.setText("Postpone ..");
//            btnIsStatusPost.setBackgroundColor(Color.parseColor("#FF4081"));
            btnIsStatusPost.getBackground().setColorFilter(Color.parseColor("#FF4081"), PorterDuff.Mode.MULTIPLY);
        }else if(data.getIs_approved() == 2){
            btnIsStatusPost.setText("Complete");

            btnIsStatusPost.getBackground().setColorFilter(Color.parseColor("#00BCD4"), PorterDuff.Mode.MULTIPLY);
        }
        btnIsStatusPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getIs_approved() == 2){
                    requestData(data.getIdKeluhan());
                }
            }
        });

        ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage(data.getFoto());
            }
        });

    }

    private void requestData(int idKeluhan){
        Helper.idKeluhan = idKeluhan;
        Log.d("ID Keluhan: ", idKeluhan+"");

        new KeluhanDetailRequest(itemView.getContext()).requestDetailKeluhan(new KeluhanDetailCallBack() {
            @Override
            public void onDataSetResult(int idKeluhanDetail, String name, String foto, String lokasi, String deskripsi, String rating) {
                dialogBuilder(idKeluhanDetail, name, foto, lokasi, deskripsi, rating);
            }

            @Override
            public void onDataIsEmpty() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void dialogImage(final String imagenya){
        LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
        View view = layoutInflater.inflate(R.layout.dialog_imageview, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView iv = view.findViewById(R.id.dialog_image);
        final ProgressBar pbarIv = view.findViewById(R.id.dialog_pBar);

        Glide.with(itemView.getContext())
                .load(URL.image + imagenya)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pbarIv.setVisibility(View.GONE);
                        Log.e("TAG", "handle error case", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        Log.e("TAG", "handle error case", e);
                        pbarIv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iv);

        alertDialog.setView(view);
        alertDialog.show();
    }
    private void dialogBuilder(final int idKeluhanDetail, String name, String foto, String lokasi, String deskripsi, String rating){
        LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_keluhan_detail, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
        TextView tvName = promptView.findViewById(R.id.custom_home_name);
        TextView tvTime = promptView.findViewById(R.id.custom_home_time);
        TextView tvDescription = promptView.findViewById(R.id.custom_home_status);
        TextView tvLoc = promptView.findViewById(R.id.custom_home_location);
        ImageView ivDesc = promptView.findViewById(R.id.custom_home_ivGambar);
        SmileRating smileRating = promptView.findViewById(R.id.ratingView);

        tvName.setText(name);
//        tvTime.setText();
        tvDescription.setText(deskripsi);
        tvLoc.setText(lokasi);
        Picasso.with(itemView.getContext())
                .load(URL.image + foto)
                .fit()
                .centerCrop()
                .into(ivDesc);
        Double doub = new Double(rating);
        smileRating.setSelectedSmile(doub.intValue());

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Log.d("ASD", level+"");

                Helper.idKeluhanDetail = idKeluhanDetail;
                Helper.rating = level;
                new RatingRequest(itemView.getContext()).postRating(new LoginCallBack() {
                    @Override
                    public void onRequestSuccess(String response) {
                        Log.d("Submit Rating", "Success");
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(itemView.getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        alertDialog.setView(promptView);
        alertDialog.show();
    }

}
