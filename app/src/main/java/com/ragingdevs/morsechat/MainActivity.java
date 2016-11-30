package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ServerCom serverCom;
    private MessageAdapter msgAdpt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if a user is logged in
        if (!UserSingleton.getInstance().isLoggedIn()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
        else
        {
            msgAdpt = new MessageAdapter(this, UserSingleton.getInstance().getMessages());
            getUsers();
            retrieveMessages();
        }
        serverCom = new ServerCom();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView messageLV = (ListView) findViewById(R.id.msglv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent friendsListIntent = new Intent(MainActivity.this, Friends.class);
                startActivity(friendsListIntent);

                // for testing purpose
                //Intent morseIntent = new Intent(MainActivity.this, MorseActivity.class);
                //startActivity(morseIntent);

                // TODO: Go to contact list
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void retrieveMessages(){
        RequestParams params = new RequestParams();
        params.put("recipientid", UserSingleton.getInstance().getUser().getId());
        serverCom.get("message/messages",params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
               /* ArrayList<JSONObject> tmp = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    try {
                        tmp.add((JSONObject)response.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("message","" + tmp.get(0));

                for(JSONObject j : tmp){
                    messages.add(gson.fromJson(j.toString(), Message.class));
                }*/
                Log.d("rsp", response.toString());
                Message[] messages = gson.fromJson(response.toString(), Message[].class);
                ArrayList<Message> msgs = new ArrayList<>();
                for(Message m : messages){
                    msgs.add(m);
                }
                Log.d("msgs", msgs.toString());
                Log.d("msgRetr", "Messages retreived successfully");
                UserSingleton.getInstance().setMessages(msgs);
                msgAdpt.notifyDataSetChanged();
            }
        }
        );
    }
    private void getUsers(){
        RequestParams params = new RequestParams();
        params.put("userid", UserSingleton.getInstance().getUser().getId());
        serverCom.get("user/all", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                Gson gson = new Gson();
                Log.d("kos og klemz", response.toString());
                UserTrans[] result = gson.fromJson(response.toString(), UserTrans[].class);
                ArrayList<ChatUser> usrs = new ArrayList<>();
                for(UserTrans c: result){
                    usrs.add(new ChatUser(c.id,c.username));
                }
                UserSingleton.getInstance().setChatUsers(usrs);
            }
        });
    }
}
