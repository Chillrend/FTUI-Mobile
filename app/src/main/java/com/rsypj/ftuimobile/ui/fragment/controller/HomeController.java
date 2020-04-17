package com.rsypj.ftuimobile.ui.fragment.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.HomeAdapter;
import com.rsypj.ftuimobile.connection.TimelineRequest;
import com.rsypj.ftuimobile.connection.listener.TimelineCallBack;
import com.rsypj.ftuimobile.helper.ConfigImage;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.TimelineModel;
import com.rsypj.ftuimobile.ui.activity.UploadActivity;
import com.rsypj.ftuimobile.ui.fragment.HomeFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class HomeController {

    Uri fileUri;
    HomeAdapter adapter;
    HomeFragment fragment;
    ArrayList<TimelineModel> data = new ArrayList<>();
    final static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    final static int MEDIA_TYPE_IMAGE = 1;

    public HomeController(HomeFragment fragment) {
        this.fragment = fragment;

        setAdapter();
        setRefreshLayout();
        onScroll();
        onCameraClicked();
        onDataRequest();
        onCLickLoadmore();
    }

    private void setRefreshLayout(){
        fragment.getSwipeRefreshLayout().setOnRefreshListener(fragment);
        fragment.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        fragment.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                onDataRequest();
            }
        });
    }

    private void onCLickLoadmore(){
        fragment.getLlLoadmore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDataRequestLoadmore();
            }
        });
    }

    private void onDataRequestLoadmore(){
        new TimelineRequest(fragment.getContext()).requestTimelineDataLoadMore(new TimelineCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<TimelineModel> response) {
//                data = response;

                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getLlLoadmore().setVisibility(View.GONE);
            }

            @Override
            public void onDataIsEmpty() {
                fragment.showToast("Data Not Found!");
            }

            @Override
            public void onRequestError(String errorMessage) {
//                fragment.showToast(errorMessage);
            }
        });
    }

    public void onDataRequest(){
        new TimelineRequest(fragment.getContext()).requestTimelineData(new TimelineCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<TimelineModel> response) {
//                data = response;
                data.clear();
                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onDataIsEmpty() {
                fragment.showToast("Data Not Found!");
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onRequestError(String errorMessage) {
                fragment.showToast(errorMessage);
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }

    private void setAdapter(){
        adapter = new HomeAdapter(fragment.getContext(), data);

        fragment.getHomeList().setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        fragment.getHomeList().setItemAnimator(new DefaultItemAnimator());
        fragment.getHomeList().setAdapter(adapter);
    }

    private void onScroll(){
        fragment.getHomeList().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fragment.getFtCamera().show();
                    fragment.getLlLoadmore().setVisibility(View.GONE);
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (URL.nextPage.equals(null)){
                        fragment.getLlLoadmore().setVisibility(View.GONE);
                    }else {
                        fragment.getLlLoadmore().setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fragment.getFtCamera().isShown()){
                    fragment.getFtCamera().hide();
                    fragment.getLlLoadmore().setVisibility(View.GONE);
                }
                if (URL.nextPage.equals(null)){
                    fragment.getLlLoadmore().setVisibility(View.GONE);
                }
            }
        });
    }

    private void onCameraClicked(){
        fragment.getFtCamera().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void captureImage(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        in.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        //fragment..startActivityForResult(in, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        fragment.startActivityForResult(in, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void launchUploadActivity(){
        Intent in = new Intent(fragment.getContext(), UploadActivity.class);

        compressImage(fileUri.getPath(), fragment.getActivity());

        Log.e("HAHA", compressImage(fileUri.getPath(), fragment.getActivity()));
        in.putExtra("filePath", fileUri.getPath());

        fragment.startActivity(in);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ConfigImage.IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdir()){
                Log.d(TAG, "Oops! Failed create " + ConfigImage.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        //image
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }else {
            return null;
        }
        return mediaFile;
    }

    public static String compressImage(String imageUri, Activity activity) {
        String filename = "";
        try {
            String filePath = getRealPathFromURI(imageUri, activity);
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas;
            if (scaledBitmap != null) {
                canvas = new Canvas(scaledBitmap);
                canvas.setMatrix(scaleMatrix);
                canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
            }


            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);

                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);

                } else if (orientation == 3) {
                    matrix.postRotate(180);

                } else if (orientation == 8) {
                    matrix.postRotate(270);

                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out;
            filename = getFileName(Uri.parse(imageUri), activity);
            try {
                out = new FileOutputStream(filename);
                if (scaledBitmap != null) {
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filename;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private static String getRealPathFromURI(String contentURI, Activity activity) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private static String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
