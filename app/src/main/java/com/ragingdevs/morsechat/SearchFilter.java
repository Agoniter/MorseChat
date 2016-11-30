package com.ragingdevs.morsechat;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfylling on 30.11.2016.
 */

public class SearchFilter extends Filter {

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        List<ChatUser> list = new ArrayList<ChatUser>();
        FilterResults result = new FilterResults();

        String substr = charSequence.toString();
        if (substr == null || substr.length() == 0) {
            result.values = list;
            result.count = list.size();
        } else {
            ArrayList<ChatUser> retList = new ArrayList<>();
            for (ChatUser username : list) {
                try {
                    if (username.getUsername().contains(charSequence)) {
                        retList.add(username);
                    }
                } catch (Exception e) {
                    Log.i("Filter", e.getMessage());
                }
            }
            result.values = retList;
            result.count = retList.size();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        ContactAdapter search = null;
        search.clear();

        if (filterResults.count > 0) {
            for (ChatUser object : (ArrayList<ChatUser>) filterResults.values) {
                search.add(object);
            }
        }
    }
}
