package org.ftui.mobile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.adapter.PhotosAdapter;
import org.ftui.mobile.model.Photos;
import org.ftui.mobile.utils.ItemDecorator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddComplaintCamera extends AppCompatActivity {

    public static int PERMISSION_REQUEST_CAMERA = 99;
    CameraView camera;
//    ImageView takenPicturePreview1, takenPicturePreview2, takenPicturePreview3, takenPicturePreview4;
//    ImageButton deleteImageBtn1, deleteImageBtn2, deleteImageBtn3, deleteImageBtn4;
//    RelativeLayout previewContainer1, previewContainer2, previewContainer3, previewContainer4;
    ImageButton takePictureButton;
    RecyclerView rv;
    private static List<Photos> photosArray;
    private PhotosAdapter photosAdapter;
    private Button nextButton;

    public static void setPhotosArray(List<Photos> newPhotosArray){
        photosArray = newPhotosArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint_camera);

        camera = findViewById(R.id.camera_view);
        takePictureButton = findViewById(R.id.take_picture);

        photosArray = new ArrayList<>();

        photosAdapter = new PhotosAdapter(photosArray, this);
        rv = findViewById(R.id.captured_photos_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rv.addItemDecoration(new ItemDecorator(this));
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(photosAdapter);

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddComplaintCamera.this, SubmitComplaintForm.class);
                startActivity(i);
            }
        });


//        takenPicturePreview1 = findViewById(R.id.taken_picture_1);
//        takenPicturePreview2 = findViewById(R.id.taken_picture_2);
//        takenPicturePreview3 = findViewById(R.id.taken_picture_3);
//        takenPicturePreview4 = findViewById(R.id.taken_picture_4);
//
//        deleteImageBtn1 = findViewById(R.id.taken_picture_del_button_1);
//        deleteImageBtn2 = findViewById(R.id.taken_picture_del_button_2);
//        deleteImageBtn3 = findViewById(R.id.taken_picture_del_button_3);
//        deleteImageBtn4 = findViewById(R.id.taken_picture_del_button_4);
//
//        previewContainer1 = findViewById(R.id.picture_container_1);
//        previewContainer2 = findViewById(R.id.picture_container_2);
//        previewContainer3 = findViewById(R.id.picture_container_3);
//        previewContainer4 = findViewById(R.id.picture_container_4);
        if(!cameraPermissionGranted()){
            checkAndAskForCameraPermission();
        }else{
            //find any view before calling this method
            doOnCreateAfterYouGotCameraPermission();
        }

    }

    public void doOnCreateAfterYouGotCameraPermission(){
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture();
            }
        });

        camera.setLifecycleOwner(this);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                switch (photosArray.size()){
                    case 4:
                        Toasty.info(getApplicationContext(), getApplicationContext().getString(R.string.maximum_picture_reached_notification)).show();
                        break;
                    default:
                        result.toBitmap(200, 400, new BitmapCallback() {
                            @Override
                            public void onBitmapReady(@Nullable Bitmap bitmap) {
                                photosArray.add(new Photos(bitmap, new Date().getTime()));
                                photosAdapter.notifyItemInserted(photosArray.size() - 1);
                            }
                        });
                }
            }
        });
    }

    public boolean checkAndAskForCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                AlertDialog.Builder permissionBuilder = new AlertDialog.Builder(this);
                permissionBuilder.setCancelable(true);
                permissionBuilder.setTitle(R.string.camera_permission_title);
                permissionBuilder.setMessage(R.string.camera_permission_description);
                permissionBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AddComplaintCamera.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    }
                });
                AlertDialog permissionDialog = permissionBuilder.create();
                permissionDialog.show();
            } else {
                ActivityCompat.requestPermissions(AddComplaintCamera.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // for each permission check if the user granted/denied them
            // you may want to group the rationale in a single dialog
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(AddComplaintCamera.this, permission );
                    if (! showRationale) {
                        Toasty.error(AddComplaintCamera.this, "Can't access camera").show();
                        finish();
                    }else{
                        finish();
                    }
                }else if(grantResults[i]== PackageManager.PERMISSION_GRANTED){
                    doOnCreateAfterYouGotCameraPermission();
                }
            }
        }
    }

    public boolean cameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}
