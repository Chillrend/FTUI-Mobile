package org.ftui.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.ftui.mobile.adapter.PhotosAdapter;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.ftui.mobile.utils.ItemDecorator;
import org.ftui.mobile.utils.PicassoImageLoader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewKeluhan extends AppCompatActivity implements View.OnClickListener, Callback<JsonObject> {

    private Button categoryFacilitiesBtn, categoryBuildingBtn, categoryHumanResBtn, categoryCleaningBtn, categoryIncidentsBtn, categoryOthersBtn;
    private String complaint_type_constraint = "6";
    public static String CONSTRAINT_FACILITIES_AND_INFRASTRUCTURE = "1", CONSTRAINT_BUILDINGS = "2",
            CONSTRAINT_HUMAN_RESOURCE = "3", CONSTRAINT_CLEANING = "4", CONSTRAINT_INCIDENT = "5",
            CONSTRAINT_OTHERS = "6";
    private static LinearLayout messageWrapper, rvWrapper;
    private static List<Image> imageList = new ArrayList<>();
    private PhotosAdapter adapter;

    private RecyclerView imageRv;
    private Button addImageButton, clearImageButton;
    private Button submitKeluhanButton;

    private TextInputEditText subject, content, location;

    public static void setImageList(List<Image> imageList) {
        imageList = imageList;

        if(imageList.size() == 0){
            messageWrapper.setVisibility(View.VISIBLE);
            rvWrapper.setVisibility(View.GONE);
        }else{
            messageWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_keluhan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.fill_complaint_detail));
        rvWrapper = findViewById(R.id.rv_wrapper);
        categoryFacilitiesBtn = findViewById(R.id.button_category_facilities);
        categoryBuildingBtn = findViewById(R.id.button_category_buildings);
        categoryHumanResBtn = findViewById(R.id.button_category_human_resource);
        categoryCleaningBtn = findViewById(R.id.button_category_cleaning);
        categoryIncidentsBtn = findViewById(R.id.button_category_incident);
        categoryOthersBtn = findViewById(R.id.button_category_others);

        subject = findViewById(R.id.complaint_title_form);
        content = findViewById(R.id.complaint_description_form);
        location = findViewById(R.id.object_location_form);

        submitKeluhanButton = findViewById(R.id.submit_keluhan_btn);

        submitKeluhanButton.setOnClickListener(this);

        categoryFacilitiesBtn.setOnClickListener(this);
        categoryBuildingBtn.setOnClickListener(this);
        categoryHumanResBtn.setOnClickListener(this);
        categoryCleaningBtn.setOnClickListener(this);
        categoryIncidentsBtn.setOnClickListener(this);
        categoryOthersBtn.setOnClickListener(this);


        messageWrapper = findViewById(R.id.add_image_message_complaint);

        categoryOthersBtn.performClick();

        rvWrapper.setVisibility(View.GONE);

        imageRv = findViewById(R.id.image_chosen_rv);

        adapter = new PhotosAdapter(imageList, CreateNewKeluhan.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        imageRv.addItemDecoration(new ItemDecorator(this));
        imageRv.setLayoutManager(layoutManager);
        imageRv.setAdapter(adapter);

        addImageButton = findViewById(R.id.add_image_button);
        clearImageButton = findViewById(R.id.clear_image_button);

        ImagePicker imagePicker =  ImagePicker
                .create(this)
                .enableLog(true)
                .limit(4)
                .returnMode(ReturnMode.NONE)
                .imageLoader(new PicassoImageLoader());

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.start();
            }
        });

        clearImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = imageList.size();
                imageList.clear();
                adapter.notifyItemRangeRemoved(0, size);
                addImageButton.setClickable(true);
                rvWrapper.setVisibility(View.GONE);
                messageWrapper.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data){
        Log.d("AWE", "onActivityResult: igetin");
        if(ImagePicker.shouldHandle(requestCode, resultCode, data)){
            List<Image> images = ImagePicker.getImages(data);
            imageList.addAll(images);

            if(imageList.size() > 0){
                rvWrapper.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();

            messageWrapper.setVisibility(View.GONE);
            clearImageButton.setClickable(true);

            if(imageList.size() == 4){
                addImageButton.setClickable(false);
            }else{
                addImageButton.setClickable(true);
            }

            Log.d("TAG", "imageList size : " + imageList.size());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_category_facilities:
                doClickCategoryButton(categoryFacilitiesBtn, CONSTRAINT_FACILITIES_AND_INFRASTRUCTURE);
                break;
            case R.id.button_category_buildings:
                doClickCategoryButton(categoryBuildingBtn, CONSTRAINT_BUILDINGS);
                break;
            case R.id.button_category_human_resource:
                doClickCategoryButton(categoryHumanResBtn, CONSTRAINT_HUMAN_RESOURCE);
                break;
            case R.id.button_category_cleaning:
                doClickCategoryButton(categoryCleaningBtn, CONSTRAINT_CLEANING);
                break;
            case R.id.button_category_incident:
                doClickCategoryButton(categoryIncidentsBtn, CONSTRAINT_INCIDENT);
                break;
            case R.id.button_category_others:
                doClickCategoryButton(categoryOthersBtn, CONSTRAINT_OTHERS);
                break;
            case R.id.submit_keluhan_btn:
                submitKeluhan();
                break;

        }
    }

    private void submitKeluhan(){
        if(!LoginActivity.completeUserPrefExist(this)) return;

        String token = LoginActivity.getUserToken(this);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Authorization", "Bearer " + token);

        if(subject.getText().toString().trim().equals("") || content.getText().toString().trim().equals("") || location.getText().toString().trim().equals("")){
            Toasty.error(this, getString(R.string.all_field_is_required)).show();
            return;
        }

        RequestBody subjectStr = RequestBody.create(MultipartBody.FORM, subject.getText().toString().trim());
        RequestBody contentStr = RequestBody.create(MultipartBody.FORM, content.getText().toString().trim());
        RequestBody locationStr = RequestBody.create(MultipartBody.FORM, location.getText().toString().trim());
        RequestBody categoryStr = RequestBody.create(MultipartBody.FORM, complaint_type_constraint);

        List<File> compressedImage = new ArrayList<>();
        for(Image image : imageList){
            try{
                File original = new File(image.getPath());
                Log.d("onCompressingImage", "Original size -> " + original.length());
                File compressed = new Compressor(this).compressToFile(original);
                Log.d("onCompressingImage", "Compressed size -> " + compressed.length());
                compressedImage.add(compressed);
            }catch (IOException e){
                e.printStackTrace();
                Log.d("onCompressingImage", "IOException -> " + e.getMessage());
            }
        }
        List<MultipartBody.Part> uploadFile = new ArrayList<>();
        for(File file : compressedImage){
            MultipartBody.Part part = prepareFilePart("image[]", file);
            uploadFile.add(part);
        }

        ApiService service = ApiCall.getClient().create(ApiService.class);
        Call<JsonObject> call;
        if(uploadFile.size() < 1){
            call = service.submitKeluhan(headerMap, subjectStr, contentStr, locationStr, categoryStr, null);
        }else{
            call = service.submitKeluhan(headerMap, subjectStr, contentStr, locationStr, categoryStr, uploadFile);
        }

        call.enqueue(this);

    }


    private MultipartBody.Part prepareFilePart(String partName, File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private void doClickCategoryButton(Button btn, String constraint){
        freeButtonState();

        btn.setSelected(true);
        complaint_type_constraint = constraint;
    }

    private void freeButtonState(){
        categoryFacilitiesBtn.setSelected(false);
        categoryBuildingBtn.setSelected(false);
        categoryHumanResBtn.setSelected(false);
        categoryCleaningBtn.setSelected(false);
        categoryIncidentsBtn.setSelected(false);
        categoryOthersBtn.setSelected(false);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        if(response.errorBody() != null || response.body().get("success") == null){
            Toasty.error(this,"Error submiting complaint (Err : errorBody not null)").show();
            Log.e("onResponse", "errorBody not null ->" + response.errorBody());
            return;
        }

        JsonObject parsedRes = response.body();
        JsonObject ticket = parsedRes.getAsJsonObject("ticket");
        String id = ticket.get("id").getAsString();
        Toasty.success(this, "Successfully submitted ticket, ID -> " + id, Toasty.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {

    }
}
