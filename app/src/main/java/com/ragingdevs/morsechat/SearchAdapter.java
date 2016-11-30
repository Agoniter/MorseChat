package com.ragingdevs.morsechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alexfylling on 29.11.2016.
 */
//USELESS!!!
public class SearchAdapter extends ArrayAdapter {

    private Filter filter;

    public SearchAdapter(Context context, ArrayList<JSONObject> usernames) {
        super(context, 0, usernames);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_item, parent, false);
        }

        TextView searchUsername = (TextView) convertView.findViewById(R.id.search_item_name);

        JSONObject username = (JSONObject) this.getItem(position);
        convertView.setTag(username);
        try {
            CharSequence user = username.getString("name");

            searchUsername.setText(user);

        } catch (JSONException e) {
            Log.i("SearchAdapter", e.getMessage());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SearchFilter();
        }
        return filter;
    }
}
