package com.rsypj.ftuimobile.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.listener.NewChatKotakClick;
import com.rsypj.ftuimobile.model.UserModel;

public class NewChatViewHolder extends RecyclerView.ViewHolder {

    TextView tvName;
    RelativeLayout rlKotak;
    UserModel data;
    NewChatKotakClick listener;
    public NewChatViewHolder(View view, NewChatKotakClick listener){
        super(view);
        this.listener = listener;
        tvName = view.findViewById(R.id.custom_newchat_tvName);
        rlKotak = view.findViewById(R.id.custom_newchat_relativeLayout);
    }

    public void sendDataToUI(final UserModel data){
        this.data = data;
        tvName.setText(data.getName());

        rlKotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToUI(data.getNim(), data.getName());
                Log.d("Nim in view holder", data.getNim());
            }
        });
    }

    private void setToUI(String nim, String nama){
        listener.onKotakClicked(nim, nama);
    }
}
