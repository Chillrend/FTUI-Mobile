package org.ftui.mobile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import es.dmoral.toasty.Toasty;

import java.util.List;

public class AddComplaintCamera extends AppCompatActivity {

    public static int PERMISSION_REQUEST_CAMERA = 99;
    CameraView camera;
    ImageView takenPicturePreview1;
    ImageButton takePictureButton;
    private List<Bitmap> photosArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint_camera);

        camera = findViewById(R.id.camera_view);
        takenPicturePreview1 = findViewById(R.id.taken_picture_1);
        takePictureButton = findViewById(R.id.take_picture);

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
                Toasty.info(getApplicationContext(), "A Picture was taken!", Toasty.LENGTH_LONG).show();
                result.toBitmap(200, 400, new BitmapCallback() {
                    @Override
                    public void onBitmapReady(@Nullable Bitmap bitmap) {
                        takenPicturePreview1.setImageBitmap(bitmap);
                        takenPicturePreview1.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                });
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
            // you may want to group the rationale in a single dialog,
            // this is just an example
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(AddComplaintCamera.this, permission );
                    if (! showRationale) {
                        Toasty.error(AddComplaintCamera.this, "Can't access camera");
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
