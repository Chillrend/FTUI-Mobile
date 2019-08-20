package org.ftui.mobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.CampusMap;
import org.ftui.mobile.MainActivity;
import org.ftui.mobile.R;


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

    LinearLayout academicGuideBookBtn, eKomplainBtn, campusMapBtn, academicRegulationBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        academicGuideBookBtn = view.findViewById(R.id.guideBookLinearButton);
        eKomplainBtn = view.findViewById(R.id.eComplaintLinearButton);
        campusMapBtn = view.findViewById(R.id.campusMapLinearButton);
        academicRegulationBtn = view.findViewById(R.id.academicRegulationLinearButton);

        academicRegulationBtn.setOnClickListener(this);
        eKomplainBtn.setOnClickListener(this);
        campusMapBtn.setOnClickListener(this);
        academicGuideBookBtn.setOnClickListener(this);
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
