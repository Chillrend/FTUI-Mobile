package org.ftui.mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.JsonObject;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.fragment.ComplaintComments;
import org.ftui.mobile.fragment.ComplaintDescription;
import org.ftui.mobile.fragment.EKeluhan;
import org.ftui.mobile.model.CompleteUser;
import org.ftui.mobile.model.User;
import org.ftui.mobile.model.keluhan.Comment;
import org.ftui.mobile.model.keluhan.Ticket;
import org.ftui.mobile.model.singlekeluhan.SingleKeluhan;
import org.ftui.mobile.model.surveyor.Details;
import org.ftui.mobile.model.surveyor.Surveyor;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.ftui.mobile.utils.SPService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;

public class KeluhanDetail extends AppCompatActivity implements
        ComplaintDescription.OnFragmentInteractionListener,
        ComplaintComments.OnFragmentInteractionListener {


    private LinearLayout parentSwitcher;
    private LinearLayout complaintDetailSwitcher;
    private LinearLayout commentSwitcher;
    private Ticket keluhan_data;
    private ArrayList<Comment> keluhan_comment;
    private SPService sharedPreferenceService;
    Boolean switcherStateAtComplaintDetail = true;
    TransitionDrawable complaintDetailTransDrawable;
    TransitionDrawable commentTransDrawable;
    private CompleteUser user;
    private User tokenUser;
    private Menu optMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluhan_detail);

        sharedPreferenceService = new SPService(this);
        tokenUser = sharedPreferenceService.getUserFromSp();
        user = sharedPreferenceService.getCompleteUserFromSp();

        Intent i = getIntent();

        if(i.getSerializableExtra("keluhan_data") != null){
            keluhan_data = (Ticket) i.getSerializableExtra("keluhan_data");
            List<Comment> comment = keluhan_data.getComments();
            keluhan_comment = new ArrayList<>(comment.size());
            keluhan_comment.addAll(comment);
            String baseImgUrl = i.getStringExtra("baseImgUrl");

            Fragment fr = ComplaintDescription.newInstance(keluhan_data, baseImgUrl);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.complaint_detail_main_fragment, fr, ComplaintDescription.COMPLAINT_DESCRIPTION_FRAGMENT_TAG)
                    .commit();
        }else if(i.getStringExtra("id") != null){
            String k_id = i.getStringExtra("id");
            getKeluhanDataFromId(k_id);
        }

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
                        if(switcherStateAtComplaintDetail && keluhan_comment != null){
                            complaintDetailTransDrawable.startTransition(0);
                            complaintDetailTransDrawable.reverseTransition(300);
                            commentTransDrawable.startTransition(300);
                            switcherStateAtComplaintDetail = false;

                            Fragment fr = ComplaintComments.newInstance(keluhan_comment, keluhan_data);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.complaint_detail_main_fragment, fr, ComplaintComments.COMPLAINT_COMMENTS_FRAGMENT_TAG)
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

    public void getKeluhanDataFromId(String id){
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("accept", "application/json");
        headerMap.put("Authorization", "Bearer " + tokenUser.getToken());

        List<String> includeParams = new ArrayList<>();
        includeParams.add("user");
        includeParams.add("gambar");
        includeParams.add("status");
        includeParams.add("category");
        includeParams.add("comments");

        String url = EKeluhan.buildGetKeluhanUrl("http://pengaduan.ccit-solution.com/api/keluhan/" + id + "?", null, includeParams, null);

        ApiService service = ApiCall.getClient().create(ApiService.class);
        Call<SingleKeluhan> call = service.getKeluhanById(url, headerMap);
        call.enqueue(new Callback<SingleKeluhan>() {
            @Override
            public void onResponse(Call<SingleKeluhan> call, Response<SingleKeluhan> response) {
                if(!response.isSuccessful()){
                    Toasty.error(KeluhanDetail.this, R.string.general_cant_get_complaint_error_msg).show();
                    Log.d("Unsuccesful Resp", response.errorBody().toString());
                }

                keluhan_data = response.body().getResults().getTicket();
                keluhan_comment = new ArrayList<>(keluhan_data.getComments());

                Fragment fr = ComplaintDescription.newInstance(keluhan_data, response.body().getUrlimg());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.complaint_detail_main_fragment, fr, ComplaintDescription.COMPLAINT_DESCRIPTION_FRAGMENT_TAG)
                        .commit();

                createOptMenu(KeluhanDetail.this.optMenu);
            }

            @Override
            public void onFailure(Call<SingleKeluhan> call, Throwable t) {
                Toasty.error(KeluhanDetail.this, R.string.general_cant_get_complaint_error_msg).show();
                t.printStackTrace();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void createOptMenu(Menu menu){

        if(keluhan_data == null || keluhan_comment == null){
            Log.d("From createOptMenu", "keluhan_data or keluhan_comment is null");
            return;
        }

        if(sharedPreferenceService.isCompleteSpExist()){
            CompleteUser user = sharedPreferenceService.getCompleteUserFromSp();
            List<Surveyor> surveyors = sharedPreferenceService.getSurveyorListFromSp();
            if(surveyors != null && surveyors.size() > 0){
                for(Surveyor surveyor : surveyors){
                    Details det = surveyor.getDetails();
                    if(det.getName().equals(keluhan_data.getCategory().getName())){
                        getMenuInflater().inflate(R.menu.keluhan_detail_activity_context_menu, menu);
                        MenuItem item = menu.getItem(0);
                        item.setTitle(evalStatusToOptionMenuString(keluhan_data.getStatus().getName()));
                        break;
                    }
                }
            }else if(keluhan_data.getStatus().getName().equals("FINISHED") && user.getId() == keluhan_data.getUser().getId()){
                getMenuInflater().inflate(R.menu.keluhan_detail_activity_context_menu, menu);
                MenuItem item = menu.getItem(0);
                item.setTitle(R.string.FINISHED_OM);
            }else if(user.getId() == keluhan_data.getUser().getId()){
                getMenuInflater().inflate(R.menu.keluhan_detail_activity_context_menu_user, menu);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.optMenu = menu;
        createOptMenu(menu);
        return true;
    }

    public static int evalStatusToOptionMenuString(String status){
        int humanReadableStringResId;
        switch (status){
            case "AWAITING_FOLLOWUP" :
                humanReadableStringResId = R.string.AWAITING_FOLLOWUP_OM;
                break;
            case "IS_BEING_FOLLOWED_UP" :
                humanReadableStringResId = R.string.IS_BEING_FOLLOWED_UP_OM;
                break;
            case "FINISHED":
                humanReadableStringResId = R.string.FINISHED_OM;
                break;
            case "REOPENED":
                humanReadableStringResId = R.string.IS_BEING_FOLLOWED_UP_OM;
                break;
            default:
                humanReadableStringResId = R.string.AWAITING_FOLLOWUP_OM;
        }

        return humanReadableStringResId;
    }

    public static String evalStatusToAPIMethod(String status){
        String method;
        switch (status){
            case "IS_BEING_FOLLOWED_UP" :
            case "REOPENED":
                method = "complete";
                break;
            case "FINISHED":
                method = "reopen";
                break;
            default:
                method = "process";
        }

        return method;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.elevate_complaint_status:
                HashMap<String,String> headerMap = new HashMap<>();
                headerMap.put("accept", "application/json");
                headerMap.put("Authorization", "Bearer " + tokenUser.getToken());

                String url = buildProcessUrl(evalStatusToAPIMethod(keluhan_data.getStatus().getName()), keluhan_data.getId());
                ApiService service = ApiCall.getClient().create(ApiService.class);
                Call<JsonObject> call = service.procKeluhan(headerMap, url);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.errorBody() != null){
                            Toasty.error(KeluhanDetail.this, "Can't elevate Complaint (err: errorBody not empty)", Toasty.LENGTH_LONG).show();
                            Log.e("OnResponse", "error Body not null -> " + response.errorBody().toString());
                            return;
                        }
                        JsonObject jsonResponse = response.body();
                        String success_message = jsonResponse.get("message").toString();
                        Toasty.success(KeluhanDetail.this, success_message, Toasty.LENGTH_LONG).show();
                        finish();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        Toasty.error(KeluhanDetail.this, "Please check your internet connection", Toasty.LENGTH_LONG).show();
                        Log.e("OnFailure", t.getMessage());

                    }
                });

                break;
            case R.id.delete_complaint:
            case R.id.delete_complaint_user:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.delete_complaint)
                        .setMessage(R.string.delete_complaint_confirmation)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                delete_complaint(keluhan_data.getId(), tokenUser.getToken());
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }
        return true;
    }

    public void delete_complaint(int id, String token){
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("accept", "application/json");
        headerMap.put("Authorization", "Bearer " + token);

        ApiService service = ApiCall.getClient().create(ApiService.class);
        Call<JsonObject> call = service.deleteKeluhan(id, headerMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.errorBody() != null){
                    Toasty.error(KeluhanDetail.this, "Can't delete Complaint (err: errorBody not empty)", Toasty.LENGTH_LONG).show();
                    Log.e("Delete : OnResponse", "error Body not null -> " + response.errorBody().toString());
                    return;
                }

                Toasty.success(KeluhanDetail.this, R.string.complaint_deleted_successfully, Toasty.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toasty.error(KeluhanDetail.this, "Please check your internet connection", Toasty.LENGTH_LONG).show();
                Log.e("Delete : OnFailure", t.getMessage());
            }
        });
    }

    public static String buildProcessUrl(String method, Integer id){

        return "http://pengaduan.ccit-solution.com/api/keluhan/" + method + "/" + id;
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

        return new FragmentManager.OnBackStackChangedListener(){
            public void onBackStackChanged(){
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null){
                    ComplaintDescription currFrag = (ComplaintDescription) manager.findFragmentById(R.id.complaint_detail_main_fragment);

                    currFrag.onFragmentResume();
                }
            }
        };
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
