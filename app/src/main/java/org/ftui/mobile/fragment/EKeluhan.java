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
import org.ftui.mobile.AddComplaintCamera;
import org.ftui.mobile.LoginActivity;
import org.ftui.mobile.R;
import org.ftui.mobile.adapter.BasicComplaintCardViewAdapter;
import org.ftui.mobile.model.BasicComplaint;
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
public class EKeluhan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String EKELUHAN_FRAGMENT_TAG = "EKELUHAN_FRAGMENT";
    private ImageButton addKeluhanBtn, filterBtn;

    private RadioGroup keluhanTypeFilterFirstGroup, statusFilterFirstGroup;

    private String typeFilter, statusFilter;
    private String baseImgurl;
    private RecyclerView rv;
    private ShimmerFrameLayout loadingLayout;

    private EndlessRecyclerViewScrollListener rvScrollListener;

    private ApiService service;
    private Metum responseMeta;
    private List<Ticket> keluhan_data = new ArrayList<>();

    private String userToken;

    private int typeCheckedId = R.id.facilities_and_infrastructure_filter_radio_button, statusCheckedId = R.id.awaiting_follow_up_filter_radio_button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        String jsonData = LoginActivity.userPrefExist(getActivity()) ? getActivity().getSharedPreferences(LoginActivity.USER_SHARED_PREFERENCE, Context.MODE_PRIVATE).getString("user", null) : null;

        Gson jsonUtil = new Gson();

        User user = jsonUtil.fromJson(jsonData, User.class);
        userToken = user.getToken();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static String buildGetKeluhanUrl(List<String> includeParam, HashMap<String, String> parameter){
        Iterator iterator = parameter.entrySet().iterator();
        StringBuilder urlBuilder = new StringBuilder("http://pengaduan.ccit-solution.com/api/keluhan/filter?");

        for (int i = 0; i < includeParam.size(); i++) {
            urlBuilder.append("includes[]=").append(includeParam.get(i)).append("&");
        }

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


    private void getKeluhanListFromApi(){
        service = ApiCall.getClient().create(ApiService.class);

        List<String> includeParam = new ArrayList<>();
        includeParam.add("status");
        includeParam.add("category");
        includeParam.add("user");
        includeParam.add("comments");
        includeParam.add("gambar");

        HashMap<String, String> otherParam = new HashMap<String, String>();

        otherParam.put("limit", "5");

        String url = buildGetKeluhanUrl(includeParam, otherParam);

        HttpUrl uri = HttpUrl.parse(url);
        Log.d("TAG", "getKeluhanListFromApi: " + uri);

        Call<Keluhan> call = service.getAllKeluhan(uri.toString(), "application/json", "Bearer " + userToken);

        call.enqueue(new Callback<Keluhan>() {
            @Override
            public void onResponse(Call<Keluhan> call, Response<Keluhan> response) {
                if(response.errorBody() != null){
                    Toasty.error(getContext(), "Tidak dapat mengambil keluhan, silahkan coba lagi").show();
                    Log.d("ERROR", "onResponse: " + response.errorBody().toString());
                    return;
                }
                Keluhan parent_of_parent = response.body();
                responseMeta = parent_of_parent.getMeta().get(0);
                Results what_the_fick_is_even_this_for = parent_of_parent.getResults();
                baseImgurl = parent_of_parent.getUrlimg();
                keluhan_data = what_the_fick_is_even_this_for.getTicket();

                BasicComplaintCardViewAdapter adapter = new BasicComplaintCardViewAdapter(keluhan_data, getActivity(), baseImgurl);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                loadingLayout.stopShimmer();
                loadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Keluhan> call, Throwable t) {
                Toasty.error(getContext(), "Tidak dapat mengambil data keluhan, silahkan coba lagi").show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        loadingLayout = view.findViewById(R.id.shimmer_container);
        loadingLayout.startShimmer();

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
                        typeCheckedId = checkedId;
                        logMe(typeCheckedId);
                    }
                });


                statusFilterFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        statusCheckedId = checkedId;
                        logMe(checkedId);
                    }
                });


                builder.setView(dialogView);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setFilterType(dialogView);
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);

                builder.show();
            }
        });

        addKeluhanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddComplaintCamera.class);
                startActivity(i);
            }
        });

        List<String> includeParam = new ArrayList<>();
        includeParam.add("status");
        includeParam.add("category");
        includeParam.add("user");
        includeParam.add("comments");
        includeParam.add("gambar");

        HashMap<String, String> otherParam = new HashMap<String, String>();

        otherParam.put("limit", "5");

        String url = buildGetKeluhanUrl(includeParam, otherParam);

        GetKeluhanIntoRecyclerView gk = new GetKeluhanIntoRecyclerView(buildGetKeluhanUrl(includeParam,otherParam), userToken, rv, loadingLayout, getActivity());

        Map<String, Object> returnedData = gk.getKeluhanToRv(url,true);
        responseMeta = (Metum) returnedData.get("meta");
        keluhan_data = (List<Ticket>) returnedData.get("data");
        baseImgurl = (String) returnedData.get("baseImgUrl");

//        getKeluhanListFromApi();
    }

    public void setFilterType(View view){
        Toasty.info(getActivity(), "Clicked " + getResources().getResourceEntryName(typeCheckedId) + " Also clicked " + getResources().getResourceEntryName(statusCheckedId), Toasty.LENGTH_LONG).show();
        Log.d("onSetFilterType", "Clicked " + getResources().getResourceEntryName(typeCheckedId) + " Also clicked " + getResources().getResourceEntryName(statusCheckedId));
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
