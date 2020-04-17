package com.rsypj.ftuimobile.adapter.viewholder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.LauncherModel;
import com.rsypj.ftuimobile.ui.activity.SplashActivity;
import com.squareup.picasso.Picasso;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    ImageView ivDown;
    View line1;
    LinearLayout wrapperDeskripsi;
    TextView tvDeskripsi;
    ImageView ivIcon;
    Button btnOpen;
    boolean expand = false;

    public LauncherHeaderViewHolder(View view){
        super(view);

        tvTitle = view.findViewById(R.id.launcher_title1);
        ivDown = view.findViewById(R.id.launcher_ivDetail1);
        line1 = view.findViewById(R.id.launcher_line1);
        wrapperDeskripsi = view.findViewById(R.id.launcher_wrapper_deskripsi1);
        tvDeskripsi = view.findViewById(R.id.launcher_tvDeskripsi1);
        btnOpen = view.findViewById(R.id.launcher_btnOpenAplikasi);
        ivIcon = view.findViewById(R.id.launcher_image1);
        onButtonClicked();
    }

    public void setUpToUI(LauncherModel data){
        tvTitle.setText(data.getJudul());
        tvDeskripsi.setText(data.getDeskripsi());
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getGambar())
                .into(ivIcon);

        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expand){
                    ivDown.animate().rotation(180).start();
                    wrapperDeskripsi.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                    wrapperDeskripsi.setAlpha(0.0f);
                    line1.setAlpha(0.0f);

                    wrapperDeskripsi.animate()
                            .translationY(10)
                            .alpha(1.0f)
                            .setListener(null);
                    line1.animate()
                            .translationY(10)
                            .alpha(1.0f)
                            .setListener(null);
                    expand = true;
                }else {
                    ivDown.animate().rotation(0).start();
                    line1.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    line1.setVisibility(View.GONE);
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

    private void onButtonClicked(){
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(new Intent(itemView.getContext(), SplashActivity.class));
                ((Activity)itemView.getContext()).finish();
            }
        });
    }
}
