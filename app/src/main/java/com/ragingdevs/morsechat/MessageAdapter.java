package com.ragingdevs.morsechat;

import android.content.Context;
import android.support.annotation.NonNull;
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

    Contact mTextContext;

    public MessageAdapter(Context context, ArrayList<Message> messages, Contact textContact) {
        super(context, 0, messages);
        this.mTextContext = textContact;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message= (Message)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
        }

        TextView messageInList = (TextView) convertView.findViewById(R.id.message_in_list);
        TextView contact = (TextView) convertView.findViewById(R.id.contact);
        messageInList.setText(message.getMessage());
        contact.setText(mTextContext.getUsername());

        return convertView;
    }
}
