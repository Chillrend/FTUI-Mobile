package com.rsypj.ftuimobile.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.listener.ButtonVerifUser;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.UnverifiedUserModel;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class VerifUserViewHolder extends RecyclerView.ViewHolder{

    TextView tvNIM;
    TextView tvNama;
    Button btnSubmit;
    ButtonVerifUser listener;

    public VerifUserViewHolder(View view, ButtonVerifUser listener){
        super(view);
        tvNIM = view.findViewById(R.id.verif_user_nim);
        tvNama = view.findViewById(R.id.verif_user_nama);
        btnSubmit = view.findViewById(R.id.verif_user_btnVerif);

        this.listener = listener;
    }

    public void setUpToUI(final UnverifiedUserModel data){
        tvNIM.setText(data.getNim()+"");
        tvNama.setText(data.getNama());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createAndShowAlertDialog(data.getNim()+"", data.getNama());
                Helper.NIM = data.getNim()+"";
                Helper.email = data.getEmail();
                buttonClicked(data.getNim()+"", data.getNama(), data.getEmail());
            }
        });
    }

    private void buttonClicked(String nim, String name, String email){
        listener.onButtonVerifClicked(nim, name, email);
    }
}
