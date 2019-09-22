package org.ftui.mobile.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.R;
import org.ftui.mobile.adapter.CommentsAdapter;
import org.ftui.mobile.model.Comments;
import org.ftui.mobile.model.keluhan.Comment;
import org.ftui.mobile.utils.PicassoImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComplaintComments.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComplaintComments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintComments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static String COMPLAINT_COMMENTS_FRAGMENT_TAG = "COMPLAINT_COMMENTS";

    private OnFragmentInteractionListener mListener;
    private ImageButton sendCommentBtn, addImageBtn;
    private Image capturedImage;
    private Drawable imageBtnBg;

    private ListView commentListView;
    private ArrayList<Comment> commentArrayList;

    public ComplaintComments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param comments List of keluhan comments.
     * @return A new instance of fragment ComplaintComments.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintComments newInstance(ArrayList<Comment> comments) {
        ComplaintComments fragment = new ComplaintComments();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, comments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commentArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complaint_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        addImageBtn = view.findViewById(R.id.add_image_comment_button);
        commentListView = view.findViewById(R.id.comment_list_view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        imageBtnBg = addImageBtn.getBackground();

        List<Comments> mockApiCommentList = Comments.mockCommentsData();

        CommentsAdapter adapter = new CommentsAdapter(commentArrayList, getActivity());
        commentListView.setAdapter(adapter);
        ImagePicker imagePicker = ImagePicker
                .create(this)
                .enableLog(true)
                .single()
                .returnMode(ReturnMode.ALL)
                .imageLoader(new PicassoImageLoader());

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(capturedImage == null){
                    imagePicker.start();
                }else{
                    builder.setTitle(R.string.dialog_title_image_chosen);
                    ImageView imageInsideDialog = new ImageView(getContext());

                    imageInsideDialog.setImageBitmap(BitmapFactory.decodeFile(capturedImage.getPath()));

                    builder.setView(imageInsideDialog);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton(R.string.dialog_negative_button_remove_image, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            capturedImage = null;
                            GradientDrawable dw = (GradientDrawable) imageBtnBg;
                            dw.setColor(ContextCompat.getColor(getContext(), R.color.white));
                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton(R.string.dialog_neutral_button_change_image, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imagePicker.start();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data){
        Log.d("AWE", "onActivityResult: igetin");
        if(ImagePicker.shouldHandle(requestCode, resultCode, data)){
            capturedImage = ImagePicker.getFirstImageOrNull(data);
            GradientDrawable dw = (GradientDrawable) imageBtnBg;
            dw.setColor(ContextCompat.getColor(getContext(), R.color.drawer_blue));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addImageBtn.setTooltipText(getString(R.string.image_has_been_added_click_again_to_preview));
            } else {
                Toasty.info(getContext(), R.string.image_has_been_added_click_again_to_preview).show();

                TooltipCompat.setTooltipText(addImageBtn, getString(R.string.image_has_been_added_click_again_to_preview));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
