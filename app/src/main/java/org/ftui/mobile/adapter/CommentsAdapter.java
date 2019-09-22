package org.ftui.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;
import org.ftui.mobile.R;
import org.ftui.mobile.model.Comments;
import org.ftui.mobile.model.keluhan.Comment;
import org.ftui.mobile.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentsAdapter extends ArrayAdapter<Comment> {
    private List<Comment> items;
    Context ctx;

    private static class ViewHolder {
        TextView userFullname;
        TextView comments;
        TextView dateSubmitted;
        ImageView complaintImage;
    }

    public CommentsAdapter(List<Comment> items, Context ctx){
        super(ctx, R.layout.comments_list_items, items);
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        Comment item = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comments_list_items, parent, false);

            viewHolder.userFullname = convertView.findViewById(R.id.comments_username);
            viewHolder.comments = convertView.findViewById(R.id.comment_box);
            viewHolder.complaintImage = convertView.findViewById(R.id.comments_image);
            viewHolder.dateSubmitted = convertView.findViewById(R.id.comment_date_submitted);

            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //TODO: Change user ID to User Name
        viewHolder.userFullname.setText(item.getUserId().toString());
        viewHolder.comments.setText(item.getContent());
        //TODO: Uncomment and delete next statement after UNIX time implemented
//        viewHolder.dateSubmitted.setText(TimeUtils.convertEpochToLocalizedString(item.getDateSubmitted()));
        viewHolder.dateSubmitted.setText(item.getCreatedAt());

        //TODO: Uncomment and change IF Statement after comment image uploading implemented
//        if(item.getCommentImgUrl() != null && item.getCommentImgUrl().trim().length() > 0){
//            Picasso.get().load(item.getCommentImgUrl()).into(viewHolder.complaintImage);
//        }else{
//            viewHolder.complaintImage.setVisibility(View.GONE);
//        }
        viewHolder.complaintImage.setVisibility(View.GONE);

        return result;
    }
}
