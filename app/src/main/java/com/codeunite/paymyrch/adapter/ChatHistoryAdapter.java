package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.helper.ChateMsg;
import java.util.ArrayList;
import java.util.List;

public class ChatHistoryAdapter extends BaseAdapter {
    ArrayList<ChateMsg> chatmsg;
    Context context;
    String isAdmin;
    String message;
    StatusModel model;

    private static class ViewHolder {
        public LinearLayout content;
        public LinearLayout contentWithBG;
        public TextView txtMessage;

        private ViewHolder() {
        }
    }

    public ChatHistoryAdapter(Context context, ArrayList<ChateMsg> statusModels) {
        this.context = context;
        this.chatmsg = statusModels;
    }

    public int getCount() {
        return this.chatmsg.size();
    }

    public Object getItem(int position) {
        return this.chatmsg.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ChateMsg chatMessage = (ChateMsg) getItem(position);
        LayoutInflater vi = (LayoutInflater) this.context.getSystemService("layout_inflater");
        if (convertView == null) {
            convertView = vi.inflate(R.layout.list_item_chat_message, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setAlignment(holder, chatMessage.isAdmin);
        holder.txtMessage.setText(chatMessage.msg);
        return convertView;
    }

    public void add(ChateMsg message) {
        this.chatmsg.add(message);
    }

    public void add(List<ChateMsg> messages) {
        this.chatmsg.addAll(messages);
    }

    private void setAlignment(ViewHolder holder, String isMe) {
        LayoutParams layoutParams;
        RelativeLayout.LayoutParams lp;
        if (isMe.equalsIgnoreCase("false")) {
            holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);
            layoutParams = (LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = 5;
            holder.contentWithBG.setLayoutParams(layoutParams);
            lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(9, 0);
            lp.addRule(11);
            holder.content.setLayoutParams(lp);
            layoutParams = (LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = 5;
            holder.txtMessage.setLayoutParams(layoutParams);
        } else if (isMe.equalsIgnoreCase("true")) {
            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);
            layoutParams = (LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = 3;
            holder.contentWithBG.setLayoutParams(layoutParams);
            lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(11, 0);
            lp.addRule(9);
            holder.content.setLayoutParams(lp);
            layoutParams = (LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = 3;
            holder.txtMessage.setLayoutParams(layoutParams);
        }
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        return holder;
    }
}
