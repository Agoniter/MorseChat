package com.ragingdevs.morsechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfylling on 27.11.2016.
 */

public class ContactAdapter extends ArrayAdapter<ChatUser> {

    ChatUser contact;

    public ContactAdapter(Context context, ArrayList<ChatUser> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        contact = getItem(position);
        RecyclerView.ViewHolder holder = null;


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_contact, parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.username);
        //CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        username.setText(contact.getUsername());

//        if (contact != null) {
//            checkBox.setChecked(false);
//        } else {
//            checkBox.setChecked(true);
//        }

        return convertView;
    }
}

