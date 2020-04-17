package com.rsypj.ftuimobile.ui.activity.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.connection.RatingRequest;
import com.rsypj.ftuimobile.connection.TimelineRequest;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.AndroidMultiPartEntity;
import com.rsypj.ftuimobile.helper.ConfigImage;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.LocationHelper;
import com.rsypj.ftuimobile.ui.activity.ApprovedActivity;
import com.rsypj.ftuimobile.ui.activity.MenuActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class ApprovedController {

    String filePath = "";
    long totalSize = 0;
    ApprovedActivity activity;

    public ApprovedController(ApprovedActivity activity) {
        this.activity = activity;

        Intent i = activity.getIntent();
        activity.getTvLocation().setText(Helper.location);
        filePath = i.getStringExtra("filePath");
        if (filePath != null){
            previewMedia();
        }else {
            activity.showToast("Maaf, file path tidak ditemukan");
        }
        customActionBar();
        //onSetLocation();
        onButtonSendClicked();
    }

    private void onButtonSendClicked(){
        activity.getIvSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.getEtKet().getText().length() < 4){
                    activity.getEtKet().setError("Harap masukkan keterangan lebih dari 4 karakter");
                }else if (activity.getTvLocation().length() < 4){
                    activity.getEtKet().setError("Harap masukkan lokasi lebih dari 4 karakter");
                }else {
                    new UploadFileToServer().execute();
//                    onUpdateData();
                    onInsertToRating();
                    activity.finish();
                }
            }
        });
    }

    private void onInsertToRating(){
        Helper.idKeluhanDetail = Helper.idKeluhan;
        Helper.rating = 1;
        Log.d("IDKELUHANDETAIL", Helper.idKeluhanDetail+"");

        new RatingRequest(activity).postRating(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void onUpdateData(){
        new TimelineRequest(activity).updatePost(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {
                activity.showToast(response);
            }

            @Override
            public void onError(String message) {
                activity.showToast(message);
            }
        });
    }

    private void customActionBar(){
        activity.getTvActionbar().setText("Approved Post");
        activity.hideActionBar();
        activity.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void onSetLocation(){

        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try{
            List<Address> listAddress = geocoder.getFromLocation(LocationHelper.latitude, LocationHelper.longtitude, 1);
            if(listAddress != null && listAddress.size() > 0){
                String locationStreet = listAddress.get(0).getThoroughfare();
                String locationSubLocality = listAddress.get(0).getSubLocality();
                String locationLocality = listAddress.get(0).getLocality();

                activity.getTvLocation().setText(locationStreet+", " +locationSubLocality + ", " + locationLocality);
                Helper.location = locationStreet+", " +locationSubLocality + ", " + locationLocality;
            }
        }catch (IOException e){

        }
    }

    private void previewMedia(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 0;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        activity.getIvFoto().setImageBitmap(bitmap);
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        int progress = 0;
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;
        int id = 10;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PendingIntent contentIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, MenuActivity.class), 0);
            notificationManager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
            notificationBuilder  = new NotificationCompat.Builder(activity)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setProgress(100,progress,false)
                    .setContentTitle("Uploading Image")
                    .setSmallIcon(R.drawable.logoui)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);

            notificationManager.notify(id,notificationBuilder.build());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            notificationBuilder.setContentText(""+values[0]+"%");
            notificationBuilder.setProgress(100, values[0],false);
            notificationManager.notify(id, notificationBuilder.build());
        }

        @Override
        protected String doInBackground(Void... voids) {
            return uploadFile();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            notificationBuilder.setContentTitle("Upload Image Success");
            notificationBuilder.setContentText("Upload Complete");
            notificationManager.notify(1, notificationBuilder.build());
            Log.e("Error: " , "" + s);
        }

        private String uploadFile(){
            String responseString = "";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ConfigImage.FILE_UPLOAD_DETAIL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File actualImageFile = new File(filePath);
                File sourceFile = new Compressor(activity)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
//                        .setDestinationDirectoryPath(mediaStorageDir.getPath() + File.separator)
                        .compressToFile(actualImageFile);

                entity.addPart("id_keluhan", new StringBody(Helper.idKeluhan+""));
                entity.addPart("NIM", new StringBody(SharePref.getmInstance(activity).getNim()));
                entity.addPart("deskripsi", new StringBody(activity.getEtKet().getText().toString()));
                entity.addPart("foto", new FileBody(sourceFile));
                entity.addPart("lokasi", new StringBody(Helper.location));
                entity.addPart("rating", new StringBody("1"));

                totalSize = entity.getContentLength();
                httpPost.setEntity(entity);

                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity r_Entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200){
                    responseString = EntityUtils.toString(r_Entity);
                }else {
                    responseString = "Error! Http Status Code: " + statusCode;
                }
            }catch (ClientProtocolException e){
                responseString = e.toString();
            }catch (IOException e){
                responseString = e.toString();
            }
            return responseString;
        }
    }
}
