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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.CategoryAdapter;
import com.rsypj.ftuimobile.connection.CategoryRequest;
import com.rsypj.ftuimobile.connection.listener.CategoryCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.AndroidMultiPartEntity;
import com.rsypj.ftuimobile.helper.CalenderHelper;
import com.rsypj.ftuimobile.helper.ConfigImage;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.LocationHelper;
import com.rsypj.ftuimobile.model.CategoryModel;
import com.rsypj.ftuimobile.ui.activity.MenuActivity;
import com.rsypj.ftuimobile.ui.activity.UploadActivity;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

/**
 * Created by dhadotid on 11/04/2018.
 */

public class UploadController {

    String filePath = "";
    String searchString;
    String catName;
    int idCat;
    int textLength;
    long totalSize = 0;
    UploadActivity activity;
    CategoryAdapter adapter;
    ArrayList<CategoryModel> data = new ArrayList<>();

    public UploadController(UploadActivity activity) {
        this.activity = activity;

        Intent i = activity.getIntent();
        filePath = i.getStringExtra("filePath");
        if (filePath != null){
//            ImageUtils.compressImage(filePath);
            previewMedia();
        }else {
            activity.showToast("Maaf, file path tidak ditemukan");
        }

        onCustomActionBar();
        onSendClicked();
        onSetLocation();
        loadCategory();
        onSearchCategory();
    }

    private void onCustomActionBar(){
        activity.getTvActionBar().setText("Smart SIP");
        activity.hideActionBar();

        activity.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onSendClicked(){
        activity.getIvSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.getEtKet().getText().length() < 4){
                    activity.getEtKet().setError("Harap masukkan keterangan lebih dari 4 karakter");
                }else if (activity.getTvLocation().getText().length() < 2){
                    activity.getTvLocation().setError("Harap masukkan lokasi");
                }else {
                    Helper.location = activity.getTvLocation().getText().toString();
                    new UploadFileToServer().execute();
                    activity.finish();
                }
//                if (Helper.idKategori == 0){
//
//                }else {
//                    activity.showToast("Harap Pilih Kategori");
//                }
            }
        });
    }

    private void filter(String text){
        ArrayList<CategoryModel> temp = new ArrayList();
        for(CategoryModel d: data){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getKategori().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }

    private void onSearchCategory(){
        activity.getEtSearch().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void setAdapter(){
        adapter = new CategoryAdapter(activity, data);

        //LinearLayoutManager horizontalLinearLayout = new LinearLayoutManager()
        activity.getCatList().setItemAnimator(new DefaultItemAnimator());
//        activity.getCatList().setLayoutManager(new LinearLayoGutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        activity.getCatList().setLayoutManager(new GridLayoutManager(activity, 5));
        activity.getCatList().setAdapter(adapter);
    }

    private void loadCategory(){
        new CategoryRequest(activity).requestCategoryData(new CategoryCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<CategoryModel> response) {
                data = response;
                setAdapter();
            }

            @Override
            public void onDataIsEmpty() {
                activity.showToast("Data Not Found!");
            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
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
                //fragment.getTvAccount().setText(locationStreet+", " +locationSubLocality + ", " + locationLocality);
            }
        }catch (IOException e){

        }
    }

    private void previewMedia(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 0;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        activity.getIvImage().setImageBitmap(bitmap);
    }


    private class UploadFileToServer extends AsyncTask<Void, Integer, String>{

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
            HttpPost httpPost = new HttpPost(ConfigImage.FILE_UPLOAD_URL);

            //belum ada shareprefnya
//            httpPost.addHeader("Accept", "application/json");
//            httpPost.addHeader("Authorization" , "Bearer +");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

//                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ConfigImage.IMAGE_DIRECTORY_NAME);
                File actualImageFile = new File(filePath);
//                File sourceFile = new Compressor(activity).compressToFile(actualImageFile);
                File sourceFile = new Compressor(activity)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
//                        .setDestinationDirectoryPath(mediaStorageDir.getPath() + File.separator)
                        .compressToFile(actualImageFile);

                entity.addPart("NIM", new StringBody(SharePref.getmInstance(activity).getNim()));
                entity.addPart("id_kategori", new StringBody(Helper.idKategori+""));
                entity.addPart("deskripsi", new StringBody(activity.getEtKet().getText().toString()));
                entity.addPart("foto", new StringBody(SharePref.getmInstance(activity).getNim()));
                entity.addPart("foto", new FileBody(sourceFile));
                entity.addPart("lokasi", new StringBody(Helper.location));
                entity.addPart("time_post", new StringBody(CalenderHelper.getCalender()+""));
                entity.addPart("is_approved", new StringBody("0"));
                entity.addPart("is_delete", new StringBody("0"));
                entity.addPart("deleted_time", new StringBody("00:00:00"));

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
