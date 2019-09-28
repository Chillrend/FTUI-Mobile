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
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import org.ftui.mobile.R;
import org.ftui.mobile.adapter.BasicComplaintCardViewAdapter;
import org.ftui.mobile.model.keluhan.*;
import org.ftui.mobile.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComplaintDescription.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComplaintDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintDescription extends Fragment {

    public static String DESCRIBABLE_TAG = "DESCRIBABLE_TAG";
    public static String BASE_IMG_URL_TAG = "baseImgUrl";


    public static String COMPLAINT_DESCRIPTION_FRAGMENT_TAG = "COMPLAINT_DESCRIPTION_FRAGMENT";

    private String baseimgUrl;

    private Ticket keluhan_data;
    private List<Gambar> gambarList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param describeable Keluhan Detail Object.
     * @param baseImgUrl base URL for retrieving images.
     * @return A new instance of fragment ComplaintDescription.
     */

    public static ComplaintDescription newInstance(Ticket describeable, String baseImgUrl){
       ComplaintDescription cd = new ComplaintDescription();
       Bundle bundle = new Bundle();
       bundle.putSerializable(DESCRIBABLE_TAG, describeable);
       bundle.putString("baseImgUrl", baseImgUrl);
       cd.setArguments(bundle);
       return cd;
    }

    private CarouselView objectImagesSlider;
    private TextView complaintStatus, complaintTitle, complaintDate, complaintLocation,
    complaintUserSubmitted, complaintDescription, complaintType, complaintDaySinceSubmmit;
    private TextView surveyorResponses;
    private ImageView surveyorSubmitedPhotos;

    private OnFragmentInteractionListener mListener;

    public ComplaintDescription() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keluhan_data = (Ticket) getArguments().getSerializable(DESCRIBABLE_TAG);
            baseimgUrl = getArguments().getString("baseImgUrl");

            gambarList = keluhan_data.getGambar();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complaint_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        objectImagesSlider = view.findViewById(R.id.carouselView);

        if(gambarList.size() > 1){
            objectImagesSlider.setPageCount(gambarList.size());
        }else{
            objectImagesSlider.setPageCount(1);
        }

        objectImagesSlider.setImageListener(this::setImageForPosition);

        complaintTitle = view.findViewById(R.id.keluhan_detail_title);
        complaintDate = view.findViewById(R.id.complaint_submitted_date);
        complaintLocation = view.findViewById(R.id.complaint_object_location);
        complaintUserSubmitted = view.findViewById(R.id.complaint_user_fullname);
        complaintType = view.findViewById(R.id.complaint_type);
        complaintDescription = view.findViewById(R.id.complaint_description);
        complaintDaySinceSubmmit = view.findViewById(R.id.complaint_daycount_since_submitted);
        complaintStatus = view.findViewById(R.id.complaint_status);

        complaintTitle.setText(keluhan_data.getSubject());

        complaintDate.setText(TimeUtils.convertEpochToLocalizedString(keluhan_data.getCreatedunix()));
        if(keluhan_data.getLocation() != null){
            complaintLocation.setText(keluhan_data.getLocation());
        }else{
            complaintStatus.setText(getContext().getString(R.string.no_location));
        }

        User user = keluhan_data.getUser();
        complaintUserSubmitted.setText(user.getName());

        Category cat = keluhan_data.getCategory();
        complaintType.setText(BasicComplaintCardViewAdapter.convertComplaintTypeToStringResId(cat.getName()));
        Drawable typeDrawable = getActivity().getResources().getDrawable(BasicComplaintCardViewAdapter.evalComplaintTypeReturnDrawable(cat.getName()));
        complaintType.setCompoundDrawablesWithIntrinsicBounds(typeDrawable, null, null, null);

        complaintDescription.setText(keluhan_data.getContent());

        Map<Integer, Long> dateDiffs = TimeUtils.getDateDiffFromNow(new Date(keluhan_data.getCreatedunix()*1000));
        String daySinceSubmit = "";
        for(Map.Entry<Integer,Long> entry : dateDiffs.entrySet()){
            String unit = getContext().getString(entry.getKey());
            daySinceSubmit = getString(R.string.complaint_daycount_since_submitted, entry.getValue(), unit);
        }

        Status status = keluhan_data.getStatus();
        if(status.getName().equals("FINISHED")){
            complaintDaySinceSubmmit.setVisibility(View.GONE);
        }else{
            complaintDaySinceSubmmit.setText(daySinceSubmit);
        }
        complaintStatus.setText(BasicComplaintCardViewAdapter.convertComplaintStatusToStringResId(status.getName()));
        Drawable background = complaintStatus.getBackground();

        if (background instanceof ShapeDrawable) {
            // cast to 'ShapeDrawable'
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(ContextCompat.getColor(getActivity(), BasicComplaintCardViewAdapter.evalComplaintStatusReturnColor(status.getName())));
        } else if (background instanceof GradientDrawable) {
            // cast to 'GradientDrawable'
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(ContextCompat.getColor(getActivity(), BasicComplaintCardViewAdapter.evalComplaintStatusReturnColor(status.getName())));
        } else if (background instanceof ColorDrawable) {
            // alpha value may need to be set again after this call
            ColorDrawable colorDrawable = (ColorDrawable) background;
            colorDrawable.setColor(ContextCompat.getColor(getActivity(), BasicComplaintCardViewAdapter.evalComplaintStatusReturnColor(status.getName())));
        }
    }

    public void setImageForPosition(int position, ImageView imageView) {
        if(gambarList.size() > 0){
            Picasso.get().load(baseimgUrl + gambarList.get(position).getImage()).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.placeholder_no_image_wide);
        }
    }

    public void onFragmentResume(){
        objectImagesSlider.setImageListener(this::setImageForPosition);
    }

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
        void onFragmentInteraction(Uri uri);
    }
}
