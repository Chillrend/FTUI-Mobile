package org.ftui.mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.ftui.mobile.R;
import org.ftui.mobile.model.BasicComplaint;
import org.ftui.mobile.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BasicComplaintCardViewAdapter extends RecyclerView.Adapter<BasicComplaintCardViewAdapter.CardViewViewHolder>{

    public static final Locale INDONESIAN_LOCALE = new Locale("id");

    public static class CardViewViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView userProfilePicture;
        TextView userFullName;
        TextView dateSubmitted;
        TextView complaintTitle;
        TextView complaintDescription;
        ImageView coverImage;
        ImageView complaintTypeIcon;
        TextView combinedComplaintTypeandStatus;
        TextView commentCount;
        RelativeLayout combinedComplaintTypeandStatusParentView;

        CardViewViewHolder(View itemView){
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.keluhan_card_view);
            userProfilePicture = (ImageView) itemView.findViewById(R.id.user_submitted_photos);
            userFullName = (TextView) itemView.findViewById(R.id.submitted_username);
            dateSubmitted = (TextView) itemView.findViewById(R.id.date_submitted);
            complaintDescription = (TextView) itemView.findViewById(R.id.keluhan_title);
            coverImage = (ImageView) itemView.findViewById(R.id.object_photos);
            complaintTypeIcon = (ImageView) itemView.findViewById(R.id.keluhan_type_icon);
            combinedComplaintTypeandStatusParentView = (RelativeLayout) itemView.findViewById(R.id.keluhan_type_and_status_parent_view);
            combinedComplaintTypeandStatus = (TextView) itemView.findViewById(R.id.keluhan_type_and_status);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);

        }
    }

    private List<BasicComplaint> itemList;

    public BasicComplaintCardViewAdapter(List<BasicComplaint> itemList){
        this.itemList = itemList;
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ekeluhan_cardview, viewGroup, false);

        return new CardViewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder cardViewViewHolder, int i){
        Picasso.get().load(itemList.get(i).getUserProfilePictureUrl()).into(cardViewViewHolder.userProfilePicture);

        cardViewViewHolder.userFullName.setText(itemList.get(i).getUserFullname());

        String date = TimeUtils.convertEpochToLocalizedString(itemList.get(i).getTimestamp(), 3, INDONESIAN_LOCALE);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){

    }
}
