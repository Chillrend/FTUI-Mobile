package org.ftui.mobile;

import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import org.ftui.mobile.fragment.ComplaintComments;
import org.ftui.mobile.fragment.ComplaintDescription;

public class KeluhanDetail extends AppCompatActivity implements
        ComplaintDescription.OnFragmentInteractionListener,
        ComplaintComments.OnFragmentInteractionListener {


    private LinearLayout parentSwitcher;
    private LinearLayout complaintDetailSwitcher;
    private LinearLayout commentSwitcher;
    Boolean switcherStateAtComplaintDetail = true;
    TransitionDrawable complaintDetailTransDrawable;
    TransitionDrawable commentTransDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluhan_detail);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.complaint_detail_main_fragment, new ComplaintDescription(), ComplaintDescription.COMPLAINT_DESCRIPTION_FRAGMENT_TAG)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.komplaint);


        complaintDetailSwitcher = findViewById(R.id.complaint_desc_switcher);
        commentSwitcher = findViewById(R.id.comments_switcher);

        complaintDetailTransDrawable = (TransitionDrawable) complaintDetailSwitcher.getBackground();
        commentTransDrawable = (TransitionDrawable) commentSwitcher.getBackground();

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

                            getSupportFragmentManager().popBackStack();
                        }
                        break;
                    case R.id.comments_switcher :
                        if(switcherStateAtComplaintDetail){
                            complaintDetailTransDrawable.startTransition(0);
                            complaintDetailTransDrawable.reverseTransition(300);
                            commentTransDrawable.startTransition(300);
                            switcherStateAtComplaintDetail = false;

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.complaint_detail_main_fragment, new ComplaintComments(), ComplaintComments.COMPLAINT_COMMENTS_FRAGMENT_TAG)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        break;
                }
            }
        };

        complaintDetailSwitcher.setOnClickListener(mHandler);
        commentSwitcher.setOnClickListener(mHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.keluhan_detail_activity_context_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            finish();
        }else if(!switcherStateAtComplaintDetail){
            commentTransDrawable.startTransition(0);
            commentTransDrawable.reverseTransition(300);
            complaintDetailTransDrawable.startTransition(300);
            switcherStateAtComplaintDetail = true;

            getSupportFragmentManager().popBackStack();

        }else if(switcherStateAtComplaintDetail){
            complaintDetailTransDrawable.startTransition(0);
            complaintDetailTransDrawable.reverseTransition(300);
            commentTransDrawable.startTransition(300);
            switcherStateAtComplaintDetail = false;

            getSupportFragmentManager().popBackStack();
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener(){
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener(){
            public void onBackStackChanged(){
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null){
                    ComplaintDescription currFrag = (ComplaintDescription) manager.findFragmentById(R.id.complaint_detail_main_fragment);

                    currFrag.onFragmentResume();
                }
            }
        };

        return result;
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
