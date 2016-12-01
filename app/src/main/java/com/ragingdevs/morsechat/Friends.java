package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Friends extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Friends");

        final ListView friendList = (ListView) findViewById(R.id.friendlist);
        contactAdapter = new ContactAdapter(this, UserSingleton.getInstance().getContacts());
        friendList.setAdapter(contactAdapter);

        friendList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        friendList.setItemsCanFocus(false);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                contactAdapter.getItem(i).toggleSelect();

            }
        });

        FloatingActionButton fabSendTo = (FloatingActionButton) findViewById(R.id.send_to_fab);
        fabSendTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Long> selected = new ArrayList<>();
                for(ChatUser c: UserSingleton.getInstance().getContacts()){
                    if (c.isSelected()){
                        selected.add(c.getId());
                        Log.d("slct", c.getUsername());
                    }
                }
                Intent intent = new Intent(Friends.this,MorseActivity.class);
                intent.putExtra("selected", selected);
                startActivity(intent);
            }
        });

        final EditText searchFriend = (EditText) findViewById(R.id.search_friend);
        Button addFriendButton = (Button) findViewById(R.id.add_friend_button);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 29.11.2016 search friend -> add to list


            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // TODO: 29.11.2016 add friend to contact list
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
