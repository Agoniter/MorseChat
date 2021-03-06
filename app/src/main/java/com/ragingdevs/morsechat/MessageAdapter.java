package com.ragingdevs.morsechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexfylling on 27.11.2016.
 */

public class MessageAdapter extends ArrayAdapter {


    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message= (Message)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
        }
        ChatUser mTextContext = message.getSender();
        TextView contact = (TextView) convertView.findViewById(R.id.contact);
        contact.setText("Message from: " + mTextContext.getUsername());

        return convertView;
    }

    @Override
    public int getCount(){
        return UserSingleton.getInstance().getMessages().size();
    }
}
