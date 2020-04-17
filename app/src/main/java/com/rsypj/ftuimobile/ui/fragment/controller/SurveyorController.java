package com.rsypj.ftuimobile.ui.fragment.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.ListPendingPostAdapter;
import com.rsypj.ftuimobile.adapter.listener.ButtonApproved;
import com.rsypj.ftuimobile.connection.TimelineRequest;
import com.rsypj.ftuimobile.connection.listener.ListPendingPostCallBack;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.helper.ConfigImage;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ListPendingPostModel;
import com.rsypj.ftuimobile.ui.activity.ApprovedActivity;
import com.rsypj.ftuimobile.ui.activity.MenuActivity;
import com.rsypj.ftuimobile.ui.fragment.SurveyorFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class SurveyorController {

    ArrayList<ListPendingPostModel> data = new ArrayList<>();
    SurveyorFragment fragment;
    ListPendingPostAdapter adapter;
    AlertDialog alertDialog;
    Uri fileUri;
    final static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    final static int MEDIA_TYPE_IMAGE = 1;

    public SurveyorController(SurveyorFragment fragment) {
        this.fragment = fragment;

        onScroll();
        onLoadmoreClicked();
        setAdapter();
        setRefreshLayout();
        onPendingPostDataRequest();
    }

    private void setRefreshLayout(){
        fragment.getSwipeRefreshLayout().setOnRefreshListener(fragment);
        fragment.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        fragment.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                onPendingPostDataRequest();
            }
        });
    }

    private void onLoadmoreClicked(){
        fragment.getTvLoadMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPendingRequestMoreData();
            }
        });
    }

    private void onScroll(){
        fragment.getListPost().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fragment.getLlLoadMore().setVisibility(View.GONE);
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (URL.nextPagePendingPost.equals(null)){
                        fragment.getLlLoadMore().setVisibility(View.GONE);
                    }else {
                        fragment.getLlLoadMore().setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fragment.getLlLoadMore().isShown()){
                    fragment.getLlLoadMore().setVisibility(View.GONE);
                }
            }
        });
    }

    public void onPendingPostDataRequest(){
        new TimelineRequest(fragment.getContext()).requestPendingForSurveyor(new ListPendingPostCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<ListPendingPostModel> response) {
                data.clear();
                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onDataIsEmpty() {
                fragment.showToast("Data Not Found!");
                fragment.getTvTidakAdaData().setVisibility(View.VISIBLE);
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onRequestError(String errorMessage) {
                fragment.showToast(errorMessage);
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }

    private void onPendingRequestMoreData(){
        new TimelineRequest(fragment.getContext()).requestPendingPostSurveyorLoadMore(new ListPendingPostCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<ListPendingPostModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getLlLoadMore().setVisibility(View.GONE);
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

    private void setAdapter(){
        adapter = new ListPendingPostAdapter(fragment.getContext(), data);

        adapter.buttonApproveClicked(new ButtonApproved() {
            @Override
            public void onButtonApprovedClicked(int status, String lokasi) {
                dialogBuilder(status,lokasi);
            }
        });
        fragment.getListPost().setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        fragment.getListPost().setItemAnimator(new DefaultItemAnimator());
        fragment.getListPost().setAdapter(adapter);
    }

    private void dialogBuilder(int status, final String lokasi){
        LayoutInflater layoutInflater = LayoutInflater.from(fragment.getContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_update_status, null);
        alertDialog = new AlertDialog.Builder(fragment.getContext()).create();
        Button btnPostpone = promptView.findViewById(R.id.buttonPostpone);
        Button btnComplete = promptView.findViewById(R.id.buttonComplete);

        if (status == 1){
//            btnPosting.setVisibility(View.GONE);
            btnPostpone.setVisibility(View.GONE);
        }

        btnPostpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.is_approved = 1;
                updateStatus();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Helper.is_approved = 2;
                Helper.location = lokasi;
                Log.d("Lokasi Surveyor", lokasi);
                captureImage();
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(promptView);
        alertDialog.show();
    }

    private void updateStatus(){
        new TimelineRequest(fragment.getContext()).updatePost(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {
                if (response.equals("Success")){
                    Toast.makeText(fragment.getContext(), "Post berhasil diupdate", Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentManager manager = ((MenuActivity)fragment.getContext()).getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.activity_frame, new SurveyorFragment()).commit();
                    ((MenuActivity)fragment.getContext()).getIvNotification().setVisibility(View.GONE);
                    ((MenuActivity)fragment.getContext()).getMenuName().setText("Surveyor");
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(fragment.getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void launchUploadActivity(){
        Intent in = new Intent(fragment.getContext(), ApprovedActivity.class);
        in.putExtra("filePath", fileUri.getPath());
        fragment.startActivity(in);
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
}
