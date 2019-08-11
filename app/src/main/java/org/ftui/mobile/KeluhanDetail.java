package org.ftui.mobile;

import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class KeluhanDetail extends AppCompatActivity {


    private LinearLayout parentSwitcher;
    private LinearLayout complaintDetailSwitcher;
    private LinearLayout commentSwitcher;
    Boolean switcherStateAtComplaintDetail = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluhan_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.komplaint);

        complaintDetailSwitcher = findViewById(R.id.complaint_desc_switcher);
        commentSwitcher = findViewById(R.id.comments_switcher);

        TransitionDrawable complaintDetailTransDrawable = (TransitionDrawable) complaintDetailSwitcher.getBackground();
        TransitionDrawable commentTransDrawable = (TransitionDrawable) commentSwitcher.getBackground();

        complaintDetailTransDrawable.startTransition(300);

        View.OnClickListener mHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick", "Clicked" + v.getTag());
                switch (v.getId()){
                    case R.id.complaint_desc_switcher :
                        if(!switcherStateAtComplaintDetail){
                            commentTransDrawable.startTransition(0);
                            commentTransDrawable.reverseTransition(300);
                            complaintDetailTransDrawable.startTransition(300);
                            switcherStateAtComplaintDetail = true;
                        }
                        break;
                    case R.id.comments_switcher :
                        if(switcherStateAtComplaintDetail){
                            complaintDetailTransDrawable.startTransition(0);
                            complaintDetailTransDrawable.reverseTransition(300);
                            commentTransDrawable.startTransition(300);
                            switcherStateAtComplaintDetail = false;
                        }
                        break;
                }
            }
        };

        complaintDetailSwitcher.setOnClickListener(mHandler);
        commentSwitcher.setOnClickListener(mHandler);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
