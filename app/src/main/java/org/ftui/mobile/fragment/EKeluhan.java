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
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.AddComplaintCamera;
import org.ftui.mobile.R;
import org.ftui.mobile.adapter.BasicComplaintCardViewAdapter;
import org.ftui.mobile.model.BasicComplaint;

import java.util.List;


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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        List<BasicComplaint> complaintList = BasicComplaint.mockComplainData();

        ShimmerFrameLayout loadingLayout = view.findViewById(R.id.shimmer_container);
        loadingLayout.startShimmer();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Filter");

        addKeluhanBtn = view.findViewById(R.id.add_keluhan_btn);
        filterBtn = view.findViewById(R.id.filter_btn);

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

        RecyclerView rv = view.findViewById(R.id.keluhan_recyclerview);
        BasicComplaintCardViewAdapter adapter = new BasicComplaintCardViewAdapter(complaintList, getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        loadingLayout.stopShimmer();
        loadingLayout.setVisibility(View.GONE);
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
