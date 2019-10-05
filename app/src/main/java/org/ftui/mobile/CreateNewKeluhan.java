package org.ftui.mobile;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.adapter.PhotosAdapter;
import org.ftui.mobile.utils.ItemDecorator;
import org.ftui.mobile.utils.PicassoImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CreateNewKeluhan extends AppCompatActivity implements View.OnClickListener{

    private Button categoryFacilitiesBtn, categoryBuildingBtn, categoryHumanResBtn, categoryCleaningBtn, categoryIncidentsBtn, categoryOthersBtn;
    private String complaint_type_constraint = "OTHERS";
    public static String CONSTRAINT_FACILITIES_AND_INFRASTRUCTURE = "FACILITIES_AND_INFRASTRUCTURE", CONSTRAINT_BUILDINGS = "BUILDINGS",
            CONSTRAINT_HUMAN_RESOURCE = "HUMAN_RESOURCE", CONSTRAINT_CLEANING = "CLEANING_AND_GARDENING", CONSTRAINT_INCIDENT = "INCIDENT_AND_RULE_VIOLATION",
            CONSTRAINT_OTHERS = "OTHERS";
    private static LinearLayout messageWrapper, rvWrapper;
    private static List<Image> imageList = new ArrayList<>();
    private PhotosAdapter adapter;

    private RecyclerView imageRv;
    private Button addImageButton, clearImageButton;


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

        }
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
}
