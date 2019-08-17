package org.ftui.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import org.ftui.mobile.R;
import org.ftui.mobile.model.Comments;
import org.ftui.mobile.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentsAdapter extends ArrayAdapter<Comments> {
    private List<Comments> items;
    Context ctx;

    private static class ViewHolder {
        TextView userFullname;
        TextView comments;
        TextView dateSubmitted;
    }

    public CommentsAdapter(List<Comments> items, Context ctx){
        super(ctx, R.layout.comments_list_items, items);
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        Comments item = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comments_list_items, parent, false);

            viewHolder.userFullname = convertView.findViewById(R.id.comments_username);
            viewHolder.comments = convertView.findViewById(R.id.comment_box);
            viewHolder.dateSubmitted = convertView.findViewById(R.id.comment_date_submitted);

            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.userFullname.setText(item.getUserFullname());
        viewHolder.comments.setText(item.getComments());
        viewHolder.dateSubmitted.setText(TimeUtils.convertEpochToLocalizedString(item.getDateSubmitted()));

        return result;
    }
}
