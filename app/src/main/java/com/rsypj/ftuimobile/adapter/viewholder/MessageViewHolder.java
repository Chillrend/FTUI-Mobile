package com.rsypj.ftuimobile.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.ChatModel;
import com.rsypj.ftuimobile.ui.activity.ChatActivity;

import static com.rsypj.ftuimobile.helper.CalenderHelper.convertLongToTime;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView tvSender;
    TextView tvMessage;
    TextView tvTime;
    RelativeLayout rlMessage;

    ChatModel data;

    public MessageViewHolder(View view){
        super(view);

        tvSender = view.findViewById(R.id.custom_message_tvName);
        tvMessage = view.findViewById(R.id.custom_message_tvMessage);
        tvTime = view.findViewById(R.id.custom_message_tvTime);
        rlMessage = view.findViewById(R.id.custom_message_relativeLayout);

    }

    public void sendDataToUI(final ChatModel data){
        this.data = data;
        tvSender.setText(data.getName());
        tvMessage.setText(data.getContent());
        tvTime.setText(convertLongToTime(data.getTime()));

        rlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListChatClicked(data.getRecipient());
            }
        });
    }

//    private void operation(long time){
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat hourParser = new SimpleDateFormat("HH");
//        SimpleDateFormat minuteParser = new SimpleDateFormat("mm");
//        long jamServer = Integer.parseInt(hourParser.format(time));
//        long minuteServer = Integer.parseInt(minuteParser.format(time));
//        tvTime.setText(jamServer + ":" + minuteServer);
//    }

    private void onListChatClicked(String senders){
        Helper.sender = senders+"";
        Intent in = new Intent(itemView.getContext(), ChatActivity.class);
        in.putExtra("Sender", tvSender.getText().toString());
        itemView.getContext().startActivity(in);
    }
}
