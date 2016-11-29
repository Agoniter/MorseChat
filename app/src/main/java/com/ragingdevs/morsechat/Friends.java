package com.ragingdevs.morsechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Friends extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView friendList = (ListView) findViewById(R.id.friendlist);
        ContactAdapter contactAdapter = new ContactAdapter(this,UserSingleton.getInstance().getContacts());
        friendList.setAdapter(contactAdapter);
        setSupportActionBar(toolbar);

        final EditText addFriend = (EditText) findViewById(R.id.add_friend);
        Button addFriendButton = (Button) findViewById(R.id.add_friend_button);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 29.11.2016 search friend -> send friend request
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
