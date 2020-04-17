package com.rsypj.ftuimobile.adapter.viewholder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.TimelineModel;
import com.squareup.picasso.Picasso;

import static com.rsypj.ftuimobile.helper.CalenderHelper.convertLongToTime;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    TextView tvStatus;
    TextView tvTime;
    RelativeLayout rlClicked;
    ImageView ivGambar;

    public NotificationViewHolder(View view){
        super(view);

        tvStatus = view.findViewById(R.id.custom_notification_tvDesc);
        tvTime = view.findViewById(R.id.custom_notification_tvTime);
        ivGambar = view.findViewById(R.id.custom_notification_ivStatus);
        rlClicked = view.findViewById(R.id.rlNotification);

    }

    public void sendDataToUI(final TimelineModel data){
        tvStatus.setText(data.getDescription());
        tvTime.setText(convertLongToTime(Long.parseLong(data.getTimePost())));
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getFoto())
                .fit()
                .centerCrop()
                .into(ivGambar);

        rlClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder(data.getName(), data.getTimePost(), data.getDescription(), data.getCategory(), data.getCategoryIcon(), data.getLocation(), data.getFoto(), data.getIs_approved()+"");
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
        Picasso.with(itemView.getContext())
                .load(URL.image + ivdesc)
                .fit()
                .centerCrop()
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
}
