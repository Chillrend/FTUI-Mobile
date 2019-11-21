package org.ftui.mobile;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.ftui.mobile.utils.SPService;

public class SubmitComplaintForm extends AppCompatActivity implements View.OnClickListener {

    private Button categoryFacilitiesBtn, categoryBuildingBtn, categoryHumanResBtn, categoryCleaningBtn, categoryIncidentsBtn, categoryOthersBtn;
    private String complaint_type_constraint = "OTHERS";
    public static String CONSTRAINT_FACILITIES_AND_INFRASTRUCTURE = "FACILITIES_AND_INFRASTRUCTURE", CONSTRAINT_BUILDINGS = "BUILDINGS",
            CONSTRAINT_HUMAN_RESOURCE = "HUMAN_RESOURCE", CONSTRAINT_CLEANING = "CLEANING_AND_GARDENING", CONSTRAINT_INCIDENT = "INCIDENT_AND_RULE_VIOLATION",
            CONSTRAINT_OTHERS = "OTHERS";
    private SPService sharedPreferenceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_complaint_form);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.fill_complaint_detail));

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

        categoryOthersBtn.performClick();
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
