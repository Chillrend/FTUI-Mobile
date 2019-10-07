package org.ftui.mobile.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import es.dmoral.toasty.Toasty;
import okhttp3.HttpUrl;
import org.ftui.mobile.*;
import org.ftui.mobile.adapter.BasicComplaintCardViewAdapter;
import org.ftui.mobile.model.BasicComplaint;
import org.ftui.mobile.model.CompleteUser;
import org.ftui.mobile.model.User;
import org.ftui.mobile.model.keluhan.Keluhan;
import org.ftui.mobile.model.keluhan.Metum;
import org.ftui.mobile.model.keluhan.Results;
import org.ftui.mobile.model.keluhan.Ticket;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.ftui.mobile.utils.EndlessRecyclerViewScrollListener;
import org.ftui.mobile.utils.GetKeluhanIntoRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EKeluhan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EKeluhan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EKeluhan extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String EKELUHAN_FRAGMENT_TAG = "EKELUHAN_FRAGMENT";
    private ImageButton addKeluhanBtn, filterBtn;

    private CompleteUser user;
    private User userTokenMdl;

    private RadioGroup keluhanTypeFilterFirstGroup, statusFilterFirstGroup;

    private String typeFilter, statusFilter;
    private String baseImgurl;
    private RecyclerView rv;
    private ShimmerFrameLayout loadingLayout;

    private EndlessRecyclerViewScrollListener rvScrollListener;

    private ApiService service;
    private Metum responseMeta;
    private List<Ticket> keluhan_data = new ArrayList<>();

    private Integer typeId = 1;
    private Integer statusId = 1;

    private String userToken;

    private int typeCheckedId = R.id.facilities_and_infrastructure_filter_radio_button, statusCheckedId = R.id.awaiting_follow_up_filter_radio_button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GetKeluhanIntoRecyclerView gk;
    private List<String> includeParam = new ArrayList<>();
    HashMap<String, String> otherParam = new HashMap<String, String>();


    private OnFragmentInteractionListener mListener;

    public EKeluhan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EKeluhan.
     */
    // TODO: Rename and change types and number of parameters
    public static EKeluhan newInstance(String param1, String param2) {
        EKeluhan fragment = new EKeluhan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!LoginActivity.completeUserPrefExist(getActivity()) || !LoginActivity.userPrefExist(getActivity())){
            getActivity().getSupportFragmentManager().popBackStack();
            Toasty.error(getActivity(), R.string.no_privilege_to_access_complaint, Toasty.LENGTH_LONG).show();
            return;
        }

        String userTokenJsonData = getActivity().getSharedPreferences(LoginActivity.USER_SHARED_PREFERENCE, Context.MODE_PRIVATE).getString("user", null);
        String jsonData = getActivity().getSharedPreferences(Home.COMPLETE_USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString("complete_user", null);

        Gson jsonUtil = new Gson();

        user = jsonUtil.fromJson(jsonData, CompleteUser.class);
        userTokenMdl = jsonUtil.fromJson(userTokenJsonData, User.class);


        userToken = userTokenMdl.getToken();

        includeParam.add("status");
        includeParam.add("category");
        includeParam.add("user");
        includeParam.add("comments");
        includeParam.add("gambar");

        otherParam.put("limit", "5");


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static String buildGetKeluhanUrl(HashMap<String, Integer>filterParam, List<String> includeParam, HashMap<String, String> parameter){
        StringBuilder urlBuilder = new StringBuilder("http://pengaduan.ccit-solution.com/api/keluhan?");
        if(filterParam != null){
            Iterator filterIterator = filterParam.entrySet().iterator();
            int i = 0;
            while (filterIterator.hasNext()){
                Map.Entry mapElement = (Map.Entry)filterIterator.next();
                urlBuilder.append("filter_groups[0][filters][").append(i).append("][key]=").append(mapElement.getKey()).append("&")
                        .append("filter_groups[0][filters][").append(i).append("][value]=").append(mapElement.getValue()).append("&")
                        .append("filter_groups[0][filters][").append(i).append("][operator]=eq").append("&");
                i++;
            }
        }

        for (String s : includeParam) {
            urlBuilder.append("includes[]=").append(s).append("&");
        }

        Iterator iterator = parameter.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry mapElement = (Map.Entry)iterator.next();
            urlBuilder.append(mapElement.getKey()).append("=").append(mapElement.getValue()).append("&");
        }
        return urlBuilder.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ekeluhan, container, false);
    }

    public void logMe(int checkedId){
        Log.d("onLogMe", getResources().getResourceEntryName(checkedId));

    }

    private void setMultipleParam(Map<String,Object> returnedData){
        responseMeta = (Metum) returnedData.get("meta");
        keluhan_data = (List<Ticket>) returnedData.get("data");
        baseImgurl = (String) returnedData.get("baseImgUrl");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        loadingLayout = view.findViewById(R.id.shimmer_container);
        loadingLayout.startShimmer();

        if(user == null){
            getActivity().getSupportFragmentManager().popBackStack();
            Toasty.error(getActivity(), R.string.no_privilege_to_access_complaint, Toasty.LENGTH_LONG).show();
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Filter");

        addKeluhanBtn = view.findViewById(R.id.add_keluhan_btn);
        filterBtn = view.findViewById(R.id.filter_btn);
        rv = view.findViewById(R.id.keluhan_recyclerview);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_dialog, (ViewGroup) getView(), false);

                keluhanTypeFilterFirstGroup = dialogView.findViewById(R.id.keluhan_type_radio_button_group_1);

                statusFilterFirstGroup = dialogView.findViewById(R.id.keluhan_status_radio_button_group_1);

                RadioButton typeChecked = dialogView.findViewById(typeCheckedId);
                typeChecked.setChecked(true);

                RadioButton statusChecked = dialogView.findViewById(statusCheckedId);
                statusChecked.setChecked(true);

                keluhanTypeFilterFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        typeId = 1;
                        switch (checkedId){
                            case R.id.facilities_and_infrastructure_filter_radio_button:
                                typeId = 1;
                                break;
                            case R.id.buildings_filter_radio_button:
                                typeId = 2;
                                break;
                            case R.id.human_resource_filter_radio_button:
                                typeId = 3;
                                break;
                            case R.id.cleaning_and_gardening_filter_radio_button:
                                typeId = 4;
                                break;
                            case R.id.incidents_filter_radio_button:
                                typeId = 5;
                                break;
                            case R.id.other_filter_radio_button:
                                typeId = 6;
                                break;
                        }
                        typeCheckedId = checkedId;
                        logMe(typeCheckedId);
                    }
                });


                statusFilterFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        statusId = 1;
                        switch (checkedId){
                            case R.id.awaiting_follow_up_filter_radio_button:
                                statusId=1;
                                break;
                            case R.id.is_being_followed_up_filter_radio_button:
                                statusId=2;
                                break;
                            case R.id.done_filter_radio_button:
                                statusId=3;
                                break;
                            case R.id.reopened_filter_radio_button:
                                statusId=4;
                                break;
                        }
                        statusCheckedId = checkedId;
                        logMe(checkedId);
                    }
                });


                builder.setView(dialogView);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(statusId != null && typeId != null){
                            HashMap<String, Integer> filterParam = new HashMap<>();
                            filterParam.put("status_id", statusId);
                            filterParam.put("category_id", typeId);

                            Map<String,Object> returnedData = gk.getKeluhanToRv(buildGetKeluhanUrl(filterParam, includeParam, otherParam), false, true);
                            setMultipleParam(returnedData);
                        }else if(statusId != null){
                            HashMap<String, Integer> filterParam = new HashMap<>();
                            filterParam.put("status_id", statusId);
                            Map<String,Object> returnedData = gk.getKeluhanToRv(buildGetKeluhanUrl(filterParam, includeParam, otherParam), false, true);
                            setMultipleParam(returnedData);
                        }else if(typeId != null){
                            HashMap<String, Integer> filterParam = new HashMap<>();
                            filterParam.put("category_id", typeId);
                            Map<String,Object> returnedData = gk.getKeluhanToRv(buildGetKeluhanUrl(filterParam, includeParam, otherParam), false, true);
                            setMultipleParam(returnedData);
                        }
                    }
                });
                builder.setNeutralButton(R.string.clear, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        typeId = null;
                        statusId = null;

                        String url = buildGetKeluhanUrl(null, includeParam, otherParam);
                        Map<String, Object> returnedData = gk.getKeluhanToRv(url,false, true);
                        setMultipleParam(returnedData);
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);

                builder.show();
            }
        });

        View.OnClickListener addKeluhanListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateNewKeluhan   .class);
                startActivity(i);
            }
        };

        if(user.getTicketit_agent() == 1 || user.getTicketit_admin() == 1){
            addKeluhanBtn.setVisibility(View.GONE);
        }else{
            addKeluhanBtn.setOnClickListener(addKeluhanListener);
        }

        String url = buildGetKeluhanUrl(null, includeParam, otherParam);

        gk = new GetKeluhanIntoRecyclerView(buildGetKeluhanUrl(null, includeParam,otherParam), userToken, rv, loadingLayout, getActivity());

        Map<String, Object> returnedData = gk.getKeluhanToRv(url,true, null);
        setMultipleParam(returnedData);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
