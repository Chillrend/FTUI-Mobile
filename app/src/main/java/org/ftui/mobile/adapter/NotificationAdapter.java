package org.ftui.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.ftui.mobile.R;
import org.ftui.mobile.model.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {

    private ArrayList<Notification> data;
    private Notification model;
    private Context context;
    private OnNotificationClickListener onNotificationClickListener;

    public NotificationAdapter(ArrayList<Notification> data, Context context, OnNotificationClickListener onNotificationClickListener) {
        this.data = data;
        this.context = context;
        this.onNotificationClickListener = onNotificationClickListener;
    }

    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 1. Declare your Views here

        public ImageView notification_type_icon;
        public TextView notification_type_text;
        public TextView notification_description;
        public OnNotificationClickListener onNotificationClickListener;


        public Viewholder(View itemView, OnNotificationClickListener onNotificationClickListener) {
            super(itemView);

            // 2. Define your Views here

            notification_type_icon = (ImageView)itemView.findViewById(R.id.notification_type_icon);
            notification_type_text = (TextView)itemView.findViewById(R.id.notification_type_text);
            notification_description = (TextView)itemView.findViewById(R.id.notification_description);
            this.onNotificationClickListener = onNotificationClickListener;

            itemView.setOnClickListener(this);

        }

        public void onClick(View v) {
            onNotificationClickListener.onNotificationClick(getAdapterPosition());
        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item_lists, parent, false);

        return new Viewholder(itemView, onNotificationClickListener);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        model = data.get(position);


        holder.notification_type_text.setText(model.getTitle());
        holder.notification_description.setText(model.getContent());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnNotificationClickListener{
        void onNotificationClick(int pos);
    }

}
