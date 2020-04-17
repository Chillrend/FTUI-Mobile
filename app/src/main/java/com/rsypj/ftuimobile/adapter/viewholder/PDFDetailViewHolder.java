package com.rsypj.ftuimobile.adapter.viewholder;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.service.DownloadService;
import com.rsypj.ftuimobile.ui.activity.ReadEbookActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class PDFDetailViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    ImageView ivImage;
    String fileName;
    String judul;

    ProgressDialog dialog;

    public PDFDetailViewHolder(View view){
        super(view);

        dialog = new ProgressDialog(view.getContext());

        tvTitle = view.findViewById(R.id.custom_home_guide_book_tvJudul);
        ivImage = view.findViewById(R.id.custom_home_guide_book_image);
    }

    public void setUpToUI(final DetailPDFModel data){
        tvTitle.setText(data.getNama());
        Picasso.with(itemView.getContext())
                .load(URL.image + data.getGambar())
                .fit()
                .centerInside()
                .into(ivImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = data.getPdf();
                judul = data.getNama();
                onClickedFile(data.getPdf());
            }
        });
    }

    private void onClickedFile(String name){
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Smart SIP/" + name);
        if (pdfFile.exists()){
            //open
            openFile(judul, name);
        }else {
            //download
            //new DownloadFile().execute(URL.file + name, name);
            executedDownload(URL.file, name);
        }
    }

    private void executedDownload(String url, String name) {
        DownloadManager.Request downloadManager = new DownloadManager.Request(Uri.parse(url + name));
        downloadManager.allowScanningByMediaScanner();
        downloadManager.setTitle("Download progress");
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.setVisibleInDownloadsUi(true);
        downloadManager.setDestinationInExternalPublicDir("/Smart SIP/", name);

        DownloadManager downloadManager1 = (DownloadManager) itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager1.enqueue(downloadManager);

        Intent intent = new Intent(itemView.getContext(), DownloadService.class);
        itemView.getContext().startService(intent);

        IntentFilter intentFilter = new IntentFilter(Helper.DOWNLOAD_KEY);
        itemView.getContext().registerReceiver(broadcastReceiver, intentFilter);

        dialog.setMessage("Please wait while loading the data ..");
        dialog.setCancelable(false);
        dialog.show();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("SuccessCode", 0) == 200) {
                // rubah button kalo di boardicle
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                openFile(judul, fileName);
                Toast.makeText(itemView.getContext(), "Download Success", Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
            }
        }
    };

    private void openFile(String judul, String name) {
        Intent in = new Intent(itemView.getContext(), ReadEbookActivity.class);
        in.putExtra(Helper.DATA, name);
        in.putExtra(Helper.DATANAME, judul);
        itemView.getContext().startActivity(in);
    }
}
