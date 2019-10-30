package org.ftui.mobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import org.ftui.mobile.R;

public class PDialog {
    private AlertDialog alertDialog;
    private Context ctx;
    private View dialogView;

    public PDialog(Context ctx){
        this.ctx = ctx;
    }

    public void buildDialog(){
        LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
        dialogView = inflater.inflate(R.layout.loading_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(false);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showDialog(){
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
