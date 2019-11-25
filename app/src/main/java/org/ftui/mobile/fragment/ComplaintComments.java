package org.ftui.mobile.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.R;
import org.ftui.mobile.adapter.CommentsAdapter;
import org.ftui.mobile.model.User;
import org.ftui.mobile.model.keluhan.Comment;
import org.ftui.mobile.model.keluhan.Ticket;
import org.ftui.mobile.utils.ApiCall;
import org.ftui.mobile.utils.ApiService;
import org.ftui.mobile.utils.PicassoImageLoader;
import org.ftui.mobile.utils.SPService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;


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
    private EditText comment_form;
    private Image capturedImage;
    private Drawable imageBtnBg;

    private ListView commentListView;
    private ArrayList<Comment> commentArrayList;
    private Ticket keluhan_data;
    private CommentsAdapter commentsAdapter;
    private User tokenUser;
    private SPService sharedPreferenceService;

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
    public static ComplaintComments newInstance(ArrayList<Comment> comments, Ticket ticket) {
        ComplaintComments fragment = new ComplaintComments();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, comments);
        args.putSerializable(ARG_PARAM2, ticket);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commentArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);
            keluhan_data = (Ticket) getArguments().getSerializable(ARG_PARAM2);
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

        sharedPreferenceService = new SPService(getActivity());
        tokenUser = sharedPreferenceService.getUserFromSp();
        addImageBtn = view.findViewById(R.id.add_image_comment_button);
        comment_form = view.findViewById(R.id.comment_form);
        sendCommentBtn = view.findViewById(R.id.send_button);
        commentListView = view.findViewById(R.id.comment_list_view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        imageBtnBg = addImageBtn.getBackground();

        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_form.getText().toString().trim().equals("") && comment_form.getText().toString().trim().length() < 7){
                    Toasty.error(getContext(), R.string.all_field_is_required).show();
                    return;
                }
                HashMap<String,String> headerMap = new HashMap<>();
                headerMap.put("accept", "application/json");
                headerMap.put("Authorization", "Bearer " + tokenUser.getToken());

                ApiService service = ApiCall.getClient().create(ApiService.class);
                Call<JsonObject> call = service.submitComment(headerMap, comment_form.getText().toString().trim(), keluhan_data.getId().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.errorBody() != null){
                            Toasty.error(getContext(), "Can't submit comment (err: errorBody not empty)", Toasty.LENGTH_LONG).show();
                            Log.e("OnResponse", "error Body not null -> " + response.errorBody().toString());
                            return;
                        }
                        Toasty.success(getContext(), R.string.comment_successfully_added, Toasty.LENGTH_SHORT).show();
                        JsonObject json = response.body();
                        Gson gson = new Gson();
                        Comment comment = gson.fromJson(json.get("comment").toString(), Comment.class);
                        commentArrayList.add(comment);
                        commentsAdapter.notifyDataSetChanged();
                        comment_form.getText().clear();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toasty.error(getContext(), "Please check your internet connection", Toasty.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        commentsAdapter = new CommentsAdapter(commentArrayList, getActivity());
        commentListView.setAdapter(commentsAdapter);
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
