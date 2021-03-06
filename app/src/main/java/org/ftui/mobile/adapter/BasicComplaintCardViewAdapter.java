package org.ftui.mobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import org.ftui.mobile.R;
import org.ftui.mobile.model.keluhan.*;
import org.ftui.mobile.utils.TimeUtils;

import java.util.List;
import java.util.Locale;

public class BasicComplaintCardViewAdapter extends RecyclerView.Adapter<BasicComplaintCardViewAdapter.CardViewViewHolder>{

    public static final Locale INDONESIAN_LOCALE = new Locale("id");

    public static class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        ImageView userProfilePicture;
        TextView userFullName;
        TextView dateSubmitted;
        TextView objectLocation;
        TextView complaintTitle;
        TextView complaintDescription;
        ImageView coverImage;
        ImageView complaintTypeIcon;
        TextView combinedComplaintTypeandStatus;
        TextView commentCount;
        RelativeLayout combinedComplaintTypeandStatusParentView;
        ImageButton contextMenuBtn;
        OnKeluhanClicklistener onKeluhanClicklistener;

        public CardViewViewHolder(View itemView, OnKeluhanClicklistener onKeluhanClicklistener){
            super(itemView);

            cv = itemView.findViewById(R.id.keluhan_card_view);
            userProfilePicture = itemView.findViewById(R.id.user_submitted_photos);
            userFullName = itemView.findViewById(R.id.submitted_username);
            dateSubmitted = itemView.findViewById(R.id.date_submitted);
            objectLocation = itemView.findViewById(R.id.object_location);
            complaintDescription = itemView.findViewById(R.id.keluhan_title);
            coverImage = itemView.findViewById(R.id.object_photos);
            complaintTypeIcon = itemView.findViewById(R.id.keluhan_type_icon);
            combinedComplaintTypeandStatusParentView = itemView.findViewById(R.id.keluhan_type_and_status_parent_view);
            combinedComplaintTypeandStatus = itemView.findViewById(R.id.keluhan_type_and_status);
            commentCount = itemView.findViewById(R.id.comment_count);
            contextMenuBtn = itemView.findViewById(R.id.context_menu_button);
            this.onKeluhanClicklistener = onKeluhanClicklistener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onKeluhanClicklistener.onKeluhanClick(getAdapterPosition());
        }
    }

    private List<Ticket> itemList;
    private Context appContext;
    private String imageBaseUrl;
    private OnKeluhanClicklistener onKeluhanClicklistener;

    public BasicComplaintCardViewAdapter(List<Ticket> itemList, Context appContext, String imageBaseUrl, OnKeluhanClicklistener onKeluhanClicklistener){
        this.itemList = itemList;
        this.appContext = appContext;
        this.imageBaseUrl = imageBaseUrl;
        this.onKeluhanClicklistener = onKeluhanClicklistener;
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ekeluhan_cardview, viewGroup, false);

        return new CardViewViewHolder(v, onKeluhanClicklistener);
    }
    @Override
    public void onBindViewHolder(final CardViewViewHolder cardViewViewHolder, int i){
        Transformation profilePicTransformation = new RoundedTransformationBuilder().oval(true).build();

        //TODO: Replace with Image URL after Profile Picture Uploading Implemented
        //using placeholder image until profpic uploading implemented
        cardViewViewHolder.userProfilePicture.setImageResource(R.drawable.ic_user);

        User user = itemList.get(i).getUser();
        cardViewViewHolder.userFullName.setText(user.getName());

        String date = TimeUtils.convertEpochToLocalizedString(itemList.get(i).getCreatedunix());
        cardViewViewHolder.dateSubmitted.setText(date);

        if(itemList.get(i).getLocation() != null){
            cardViewViewHolder.objectLocation.setText(itemList.get(i).getLocation());
        }else{
            cardViewViewHolder.objectLocation.setText(appContext.getString(R.string.no_location));
        }
        cardViewViewHolder.complaintDescription.setText(itemList.get(i).getContent());

        Transformation coverImageTransformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(1)
                .cornerRadius(10)
                .oval(false)
                .build();
        List<Gambar> gambar = itemList.get(i).getGambar();
        if(gambar.size() > 0){
            Picasso.get().load(imageBaseUrl + gambar.get(0).getImage()).transform(coverImageTransformation).fit().into(cardViewViewHolder.coverImage);
        }else{
            Picasso.get().load(R.drawable.placeholder_no_image_wide).transform(coverImageTransformation).fit().into(cardViewViewHolder.coverImage);

        }

        Status status = itemList.get(i).getStatus();
        cardViewViewHolder.combinedComplaintTypeandStatusParentView.setBackgroundColor(ContextCompat.getColor(appContext, evalComplaintStatusReturnColor(status.getName())));

        Category category = itemList.get(i).getCategory();
        cardViewViewHolder.complaintTypeIcon.setImageResource(evalComplaintTypeReturnDrawable(category.getName()));

        String complaintType = appContext.getString(convertComplaintTypeToStringResId(category.getName()));
        String complaintStatus = appContext.getString(convertComplaintStatusToStringResId(status.getName()));
        String combinedText = appContext.getString(R.string.COMBINED_STATUS_AND_TYPE, complaintType, complaintStatus);
        cardViewViewHolder.combinedComplaintTypeandStatus.setText(combinedText);

        List<Comment> comment = itemList.get(i).getComments();
        cardViewViewHolder.commentCount.setText(String.format(Locale.getDefault(), "%d", comment.size()));

        cardViewViewHolder.contextMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(appContext, cardViewViewHolder.contextMenuBtn);
                popupMenu.inflate(R.menu.recycler_view_context_menu_privileged);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.see_detailed_complaint:
                                onKeluhanClicklistener.onKeluhanClick(i);
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){

    }

    public static int convertComplaintStatusToStringResId(String status){
        int humanReadableStringResId;
        switch (status){
            case "AWAITING_FOLLOWUP" :
                humanReadableStringResId = R.string.AWAITING_FOLLOWUP;
                break;
            case "IS_BEING_FOLLOWED_UP" :
                humanReadableStringResId = R.string.IS_BEING_FOLLOWED_UP;
                break;
            case "FINISHED":
                humanReadableStringResId = R.string.FINISHED;
                break;
            case "REOPENED":
                humanReadableStringResId = R.string.REOPENED;
                break;
            default:
                humanReadableStringResId = R.string.AWAITING_FOLLOWUP;
        }

        return humanReadableStringResId;
    }

    public static int evalComplaintStatusReturnColor(String status){
        int color = 0;
        switch (status){
            case "AWAITING_FOLLOWUP" :
                color = R.color.AWAITING_FOLLOWUP;
                break;
            case "IS_BEING_FOLLOWED_UP" :
                color = R.color.IS_BEING_FOLLOWED_UP;
                break;
            case "FINISHED":
                color = R.color.FINISHED;
                break;
            default:
                color = R.color.AWAITING_FOLLOWUP;
        }

        return color;
    }

    public static int evalComplaintTypeReturnDrawable(String type){
        int drawable;

        switch (type){
            case "FACILITIES_AND_INFRASTRUCTURE" :
                drawable = R.drawable.ic_event_seat_white_24dp;
                break;
            case "BUILDINGS" :
                drawable = R.drawable.ic_location_city_black_24dp;
                break;
            case "HUMAN_RESOURCE" :
                drawable = R.drawable.ic_school_white_24dp;
                break;
            case "CLEANING_AND_GARDENING" :
                drawable = R.drawable.ic_sharp_local_florist_24px;
                break;
            case "INCIDENT_AND_RULE_VIOLATION" :
                drawable = R.drawable.ic_sharp_gavel_24px;
                break;
            case "OTHERS" :
                drawable = R.drawable.ic_sharp_category_24px;
                break;
            default:
                drawable = R.drawable.ic_sharp_category_24px;
        }

        return drawable;
    }

    public static int convertComplaintTypeToStringResId(String type){
        int humanReadableStringResId;

        switch (type){
            case "FACILITIES_AND_INFRASTRUCTURE" :
                humanReadableStringResId = R.string.FACILITIES_AND_INFRASTRUCTURE;
                break;
            case "BUILDINGS" :
                humanReadableStringResId = R.string.BUILDINGS;
                break;
            case "HUMAN_RESOURCE" :
                humanReadableStringResId = R.string.HUMAN_RESOURCE;
                break;
            case "CLEANING_AND_GARDENING" :
                humanReadableStringResId = R.string.CLEANING_AND_GARDENING;
                break;
            case "INCIDENT_AND_RULE_VIOLATION" :
                humanReadableStringResId = R.string.INCIDENT_AND_RULE_VIOLATION;
                break;
            case "OTHERS" :
                humanReadableStringResId = R.string.OTHERS;
                break;
            default:
                humanReadableStringResId = R.string.OTHERS;
        }

        return humanReadableStringResId;
    }

    public interface OnKeluhanClicklistener{
        void onKeluhanClick(int pos);
    }
}
