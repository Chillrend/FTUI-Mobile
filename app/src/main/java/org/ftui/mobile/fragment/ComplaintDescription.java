package org.ftui.mobile.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import org.ftui.mobile.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComplaintDescription.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComplaintDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintDescription extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static String COMPLAINT_DESCRIPTION_FRAGMENT_TAG = "COMPLAINT_DESCRIPTION_FRAGMENT";

    CarouselView objectImagesSlider;
    TextView complaintStatus;
    TextView surveyorResponses;
    ImageView surveyorSubmitedPhotos;

    private OnFragmentInteractionListener mListener;

    public ComplaintDescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintDescription.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintDescription newInstance(String param1, String param2) {
        ComplaintDescription fragment = new ComplaintDescription();
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
        return inflater.inflate(R.layout.fragment_complaint_description, container, false);
    }

    int[] picsumImageRes = {R.drawable.picsum_1, R.drawable.picsum_2, R.drawable.picsum_3, R.drawable.picsum_4};

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        objectImagesSlider = view.findViewById(R.id.carouselView);


        objectImagesSlider.setPageCount(picsumImageRes.length);

        objectImagesSlider.setImageListener(this::setImageForPosition);

        complaintStatus = view.findViewById(R.id.complaint_status);

        Drawable background = complaintStatus.getBackground();

        if (background instanceof ShapeDrawable) {
            // cast to 'ShapeDrawable'
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(ContextCompat.getColor(getActivity(), R.color.AWAITING_FOLLOWUP));
        } else if (background instanceof GradientDrawable) {
            // cast to 'GradientDrawable'
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(ContextCompat.getColor(getActivity(), R.color.AWAITING_FOLLOWUP));
        } else if (background instanceof ColorDrawable) {
            // alpha value may need to be set again after this call
            ColorDrawable colorDrawable = (ColorDrawable) background;
            colorDrawable.setColor(ContextCompat.getColor(getActivity(), R.color.AWAITING_FOLLOWUP));
        }

        surveyorResponses = view.findViewById(R.id.complaint_surveyor_message);
        surveyorSubmitedPhotos = view.findViewById(R.id.complaint_surveyor_submitted_image);

        surveyorResponses.setVisibility(View.GONE);
        surveyorSubmitedPhotos.setVisibility(View.GONE);


    }

    public void setImageForPosition(int position, ImageView imageView) {
        imageView.setImageResource(picsumImageRes[position]);
    }

    public void onFragmentResume(){
        objectImagesSlider.setImageListener(this::setImageForPosition);
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
