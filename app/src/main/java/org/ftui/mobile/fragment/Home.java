package org.ftui.mobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.*;
import org.ftui.mobile.model.CompleteUser;
import org.ftui.mobile.model.User;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT";
    public static final String COMPLETE_USER_SHARED_PREFERENCES = "COMPLETE_USER_SHARED_PREFERENCES";

    private LinearLayout academicGuideBookBtn, eKomplainBtn, campusMapBtn, academicRegulationBtn;
    private LinearLayout userGreetingWrapper;
    private TextView user_fullname, user_role;

    private boolean userPrefExist;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ApiService service;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        userPrefExist = LoginActivity.userPrefExist(getActivity());
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        academicGuideBookBtn = view.findViewById(R.id.guideBookLinearButton);
        eKomplainBtn = view.findViewById(R.id.eComplaintLinearButton);
        campusMapBtn = view.findViewById(R.id.campusMapLinearButton);
        academicRegulationBtn = view.findViewById(R.id.academicRegulationLinearButton);
        userGreetingWrapper = view.findViewById(R.id.greeting_card_wrapper);

        user_fullname = view.findViewById(R.id.user_name);
        user_role = view.findViewById(R.id.user_role);

        if(userPrefExist){
            SharedPreferences spf = getActivity().getSharedPreferences(LoginActivity.USER_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            Gson jsonUtil  = new Gson();
            Log.d("onCheckuserpref", spf.getString("user", null));
            User user = jsonUtil.fromJson(spf.getString("user", null), User.class);

            service = ApiCall.getClient().create(ApiService.class);

            String accept_header = "application/json";
            String auth_header =  "Bearer " + user.getToken();

            Call<JsonObject> call = service.amialive(accept_header, auth_header);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.errorBody() != null && !response.body().get("message").getAsString().equals("Authenticated")){
                        return;
                    }
                    JsonObject parsedBody = response.body();
                    String jsonData = parsedBody.get("success").toString();
                    Gson jsonUtil = new Gson();
                    CompleteUser completeUser = jsonUtil.fromJson(jsonData, CompleteUser.class);
                    Log.d("On Response :", completeUser.getEmail() + " - " + completeUser.getName());

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(COMPLETE_USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putString("complete_user", jsonData);
                    editor.apply();

                    user_fullname.setText(completeUser.getName());
                    user_role.setText(completeUser.getIdentitas());

                    NavigationView nv = getActivity().findViewById(R.id.nav_view);
                    View navHeader = nv.getHeaderView(0);
                    TextView userName = navHeader.findViewById(R.id.header_user_fullname);
                    TextView userRole = navHeader.findViewById(R.id.header_user_role);
                    userName.setText(completeUser.getName());
                    userRole.setText(completeUser.getIdentitas());
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }

        academicRegulationBtn.setOnClickListener(this);
        eKomplainBtn.setOnClickListener(this);
        campusMapBtn.setOnClickListener(this);
        academicGuideBookBtn.setOnClickListener(this);

        userGreetingWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPrefExist){
                    Intent i = new Intent(getActivity(), UserProfileActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.academicRegulationLinearButton:
                Toasty.info(getActivity().getApplicationContext(), "Incoming Features").show();
                break;
            case R.id.eComplaintLinearButton:
                Fragment fr = new EKeluhan();
                ((MainActivity) getActivity()).replaceFragment(fr, EKeluhan.EKELUHAN_FRAGMENT_TAG);
                break;
            case R.id.campusMapLinearButton:
                Intent i = new Intent(getActivity(), CampusMap.class);
                startActivity(i);
                break;
            case R.id.guideBookLinearButton:
                Toasty.info(getActivity().getApplicationContext(), "Incoming Features").show();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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
