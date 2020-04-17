package com.rsypj.ftuimobile.adapter.viewholder;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.FileDownloader;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ListBookModel;
import com.rsypj.ftuimobile.service.DownloadService;

import java.io.File;
import java.io.IOException;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class PDFViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    String fileName;

    public PDFViewHolder(View view){
        super(view);

        tvTitle = view.findViewById(R.id.launcher_pdf_tvNama);
    }

    public void setUpToUI(final ListBookModel data){
        tvTitle.setText(Html.fromHtml("<font color=black>" + "\u2022 " + "</font>" + data.getNama()));

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = data.getGambar();
                onClickedFile(data.getGambar());
            }
        });
    }



    private void onClickedFile(String name){
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Smart SIP/" + name);
        if (pdfFile.exists()){
            //open
            openFile(name);
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
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("SuccessCode", 0) == 200) {
                // rubah button kalo di boardicle
                openFile(fileName);
                Toast.makeText(itemView.getContext(), "Download Success", Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
            }
        }
    };

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        int id = 10;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Smart SIP");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

    private void openFile(String name) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Smart SIP/" + name);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            itemView.getContext().startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(itemView.getContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
