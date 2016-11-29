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

public class ContactAdapter extends ArrayAdapter<ChatUser> {

    public ContactAdapter(Context context, ArrayList<ChatUser> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatUser contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact, parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.username);
        username.setText(contact.getUsername());

        return convertView;
    }
}
