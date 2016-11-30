package com.ragingdevs.morsechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class Friends extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner dropDownSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //dropDownSearchList = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);



        ListView friendList = (ListView) findViewById(R.id.friendlist);
        ContactAdapter contactAdapter = new ContactAdapter(this, UserSingleton.getInstance().getContacts());
        friendList.setAdapter(contactAdapter);

        final EditText searchFriend = (EditText) findViewById(R.id.search_friend);
        Button addFriendButton = (Button) findViewById(R.id.add_friend_button);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 29.11.2016 search friend -> add to list

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropDownSearchList.setAdapter(adapter);
                dropDownSearchList.setOnItemSelectedListener(Friends.this);
            }
        });

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
