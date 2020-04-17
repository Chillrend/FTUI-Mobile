package com.rsypj.ftuimobile.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.LauncherModel;
import com.rsypj.ftuimobile.ui.activity.HomeGuideBookActivity;
import com.rsypj.ftuimobile.ui.activity.MapsActivity;
import com.rsypj.ftuimobile.ui.activity.SplashActivity;
import com.squareup.picasso.Picasso;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class LauncherRevisiViewHolder extends RecyclerView.ViewHolder {

    ImageView ivLogo;
    TextView tvJudul;
    LauncherModel data;

    public LauncherRevisiViewHolder(View view){
        super(view);
        ivLogo = view.findViewById(R.id.launcher_revisi_ivImage);
        tvJudul = view.findViewById(R.id.launcher_revisi_tvJudul);
    }

    private void onClicked(int pos){
        if (pos != RecyclerView.NO_POSITION){
            Log.d("positionclicked", pos+"");
            if (data.getJudul().equalsIgnoreCase("Academic Guide Book")){
                Intent in = new Intent(itemView.getContext(), HomeGuideBookActivity.class);
                in.putExtra(Helper.DATA, data.getId()+"");
                itemView.getContext().startActivity(in);
                //Helper.idlauncher = data.getId()+"";
            }else if (data.getJudul().equalsIgnoreCase("mComplaint")){
                itemView.getContext().startActivity(new Intent(itemView.getContext(), SplashActivity.class));
            }else if (data.getJudul().equalsIgnoreCase("Map")){
                itemView.getContext().startActivity(new Intent(itemView.getContext(), MapsActivity.class));
            }else if (data.getJudul().equalsIgnoreCase("Academic Regulation")){
                itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://eng.ui.ac.id/peraturan-akademik")));
            }
        }
    }

    public void setUpToUI(LauncherModel data){
        this.data = data;
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getGambar())
                .into(ivLogo);
        tvJudul.setText(data.getJudul());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                onClicked(pos);
            }
        });
    }
}
