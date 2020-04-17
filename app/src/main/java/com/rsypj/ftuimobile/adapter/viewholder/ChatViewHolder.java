package com.rsypj.ftuimobile.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.model.ChatModel;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout chatLeftWrapper;
    private LinearLayout chatRightWrapper;
    private TextView chatLeft;
    private TextView chatRight;
    private TextView usernameLeft;
    private TextView usernameRight;

    public ChatViewHolder(View view){
        super(view);

        chatLeft = view.findViewById(R.id.chatBubbleLeft);
        chatRight = view.findViewById(R.id.chatBubbleRight);
        chatLeftWrapper = view.findViewById(R.id.leftWrapper);
        chatRightWrapper = view.findViewById(R.id.rightWrapper);
        usernameLeft = view.findViewById(R.id.leftUsernameText);
        usernameRight = view.findViewById(R.id.rightUsernameText);
    }

    public void sendDataToUI(ChatModel data){
        if(data.getOwner().equals(SharePref.getmInstance(itemView.getContext()).getNim())) {
            chatRightWrapper.setVisibility(View.VISIBLE);
            chatRight.setText(data.getContent());
            usernameRight.setText(data.getName());
        }
        else {
            chatLeftWrapper.setVisibility(View.VISIBLE);
            chatLeft.setText(data.getContent());
            usernameLeft.setText(data.getName());
        }
    }
}
