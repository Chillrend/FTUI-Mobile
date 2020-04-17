package com.rsypj.ftuimobile.adapter.viewholder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.listener.ButtonApproved;
import com.rsypj.ftuimobile.helper.CalenderHelper;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.TimeCalculate;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ListPendingPostModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListPendingPostViewHolder extends RecyclerView.ViewHolder{

    ImageView ivStatus;
    TextView tvStatus;
    TextView tvTime;
    Button btnApprove;
    ProgressBar pBarStatus;
    int idKeluhan;
    ButtonApproved listener;
    ListPendingPostModel data;

    public ListPendingPostViewHolder(View view, ButtonApproved listener){
        super(view);

        ivStatus = view.findViewById(R.id.postImage);
        btnApprove = view.findViewById(R.id.postApprover);
        tvStatus = view.findViewById(R.id.postDescription);
        pBarStatus = view.findViewById(R.id.postManagerpBar);
        tvTime = view.findViewById(R.id.postTime);

        this.listener = listener;
    }

    public void setUpToUI(final ListPendingPostModel data){
        this.data = data;
        idKeluhan = data.getIdKeluhan();
        tvStatus.setText(data.getDescription());

        Glide.with(itemView.getContext())
                .load(URL.image + data.getFoto())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pBarStatus.setVisibility(View.GONE);
                        Log.e("TAG", "handle error case", e);
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
        if (data.getIs_approved() == 0){
            btnApprove.setText("Publish");
            btnApprove.setBackgroundColor(Color.GRAY);
        }else if (data.getIs_approved() == 1){
            btnApprove.setText("Postpone");
            btnApprove.setBackgroundColor(Color.parseColor("#FF3D00"));
        }else if (data.getIs_approved() == 2){
            btnApprove.setText("Complete");
        }

//        String timeText = "";
//        if (data.getIs_approved() == 0){
//            timeText = "Sudah " + TimeDiffLib.getTimeComparator(data.getCreated_at(), data.getUpdated_at()) + " tidak terpublish";
//        } else if (data.getIs_approved() == 1){
//            timeText = "Postpone dalam " + TimeDiffLib.getTimeComparator(data.getCreated_at(), data.getUpdated_at());
//        } else if (data.getIs_approved() == 2){
//            timeText = "Complete dalam "+ TimeDiffLib.getTimeComparator(data.getCreated_at(), data.getUpdated_at());
//        }
//        tvTime.setText(timeText);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateCreated = sdf.parse(data.getCreated_at());
            Date dateUpdated = sdf.parse(data.getUpdated_at());
            if (data.getIs_approved() == 0){
                tvTime.setText("Sudah " + TimeCalculate.DateDifference(dateCreated.getTime()) + " tidak terpublish");
            }
            else if (data.getIs_approved() == 1){
                tvTime.setText("Postpone dalam " + TimeCalculate.DateDifference(dateCreated.getTime(), dateUpdated.getTime()));
            }else if (data.getIs_approved() == 2){
                tvTime.setText("Complete dalam "+TimeCalculate.DateDifference(dateCreated.getTime(), dateUpdated.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        if (!data.getCreated_at().equals(data.getUpdated_at())){
//
//        }

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.idKeluhan = idKeluhan;
                buttonClicked(data.getIs_approved(), data.getLocation());
//                updateStatus();
            }
        });

        ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder(data.getName(), CalenderHelper.convertLongToTime(Long.parseLong(data.getTimePost())), data.getDescription(), data.getCategory(), data.getCategoryIcon(), data.getLocation(), data.getFoto(), data.getIs_approved()+"");
            }
        });
    }

    private void dialogBuilder(String name, String time, String desc, String cat, String ivcat, String loc, String ivdesc, String isPOst){
        LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
        View promptView = layoutInflater.inflate(R.layout.custom_home, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
        TextView tvName = promptView.findViewById(R.id.custom_home_name);
        TextView tvTime = promptView.findViewById(R.id.custom_home_time);
        TextView tvDescription = promptView.findViewById(R.id.custom_home_status);
        TextView tvCategory = promptView.findViewById(R.id.home_category_tv);
        ImageView ivCategory = promptView.findViewById(R.id.home_category_image);
        TextView tvLoc = promptView.findViewById(R.id.custom_home_location);
        ImageView ivDesc = promptView.findViewById(R.id.custom_home_ivGambar);
        Button btnisPost = promptView.findViewById(R.id.custom_home_btnStatusPost);
        ProgressBar progressBar = promptView.findViewById(R.id.home_pBar_status);

        tvName.setText(name);
        tvTime.setText(time);
        tvDescription.setText(desc);
        tvCategory.setText(cat);
        Picasso.with(itemView.getContext())
                .load(URL.image + ivcat)
                .fit()
                .centerCrop()
                .into(ivCategory);
        tvLoc.setText(loc);
//        Picasso.with(itemView.getContext())
//                .load(URL.image + ivdesc)
//                .fit()
//                .centerCrop()
//                .into(ivDesc);
        Glide.with(itemView.getContext())
                .load(URL.image + ivdesc)
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pBarStatus.setVisibility(View.GONE);
                        Log.e("TAG", "handle error case", e);
                        Toast.makeText(itemView.getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        Log.e("TAG", "handle error case", e);

                        pBarStatus.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivDesc);
        if (isPOst.equals("0")){
            btnisPost.setText("Publish");
            btnisPost.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }else if (isPOst.equals("1")){
            btnisPost.setText("Postpone");
            btnisPost.getBackground().setColorFilter(Color.parseColor("#FF3D00"), PorterDuff.Mode.MULTIPLY);
        }else if (isPOst.equals("2")){
            btnisPost.setText("Complete");
            btnisPost.getBackground().setColorFilter(Color.parseColor("#448AFF"), PorterDuff.Mode.MULTIPLY);
        }


        alertDialog.setView(promptView);
        alertDialog.show();
    }

    private void buttonClicked(int status, String lokasi){
        listener.onButtonApprovedClicked(status, lokasi);
    }
}
