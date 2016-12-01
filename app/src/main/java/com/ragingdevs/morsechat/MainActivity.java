package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ragingdevs.utils.FileHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ServerCom serverCom;
    private MessageAdapter msgAdpt;
    ListView messageLV;
    private SwipeRefreshLayout messageRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        messageRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveMessages();
            }
        });

        serverCom = new ServerCom();
        tokenLoginCheck();
        if(UserSingleton.getInstance().isLoggedIn(MainActivity.this)){
            getUsers();
            retrieveMessages();
        }

        messageLV = (ListView) findViewById(R.id.msglv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        messageLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message mesg = (Message) msgAdpt.getItem(position);
                mesg.play(MainActivity.this);
                removeMessage(mesg.getId());
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent friendsListIntent = new Intent(MainActivity.this, Friends.class);
                getUsers();
                startActivity(friendsListIntent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(msgAdpt != null){
            retrieveMessages();
            // getUsers();
            msgAdpt.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            UserSingleton.getInstance().clearMe(MainActivity.this);
            initiateLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void retrieveMessages(){
        RequestParams params = new RequestParams();
        params.put("recipientid", UserSingleton.getInstance().getUser().getId());
        Log.d("thisuserid", "" + UserSingleton.getInstance().getUser().getId() );
        serverCom.get("message/messages",params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<Message> msgs = new ArrayList<Message>();
                        Gson gson = new Gson();
                        for(int i=0; i< response.length(); i++){
                            try {;
                                JSONObject obj = response.getJSONObject(i);
                                Long senderid = obj.getLong("sender");
                                Long messageid = obj.getLong("id");
                                ChatUser sender = null;
                                for(ChatUser c : UserSingleton.getInstance().getContacts()){
                                    if(c.getId() == senderid) {
                                        sender = c;
                                    }
                                }

                                //Log.d("Sender", sender.getUsername());
                                Long[] message = gson.fromJson(obj.getString("message"),Long[].class);
                                List<Long>  msg = Arrays.asList(message);
                                ArrayList<Long> mMessage = new ArrayList<>();
                                mMessage.addAll(msg);
                                Log.d("msg: ", msg.toString());
                                if(sender != null) {
                                    msgs.add(new Message(mMessage, UserSingleton.getInstance().getUser(), sender, messageid));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        UserSingleton.getInstance().setMessages(msgs);
                        msgAdpt = new MessageAdapter(MainActivity.this, UserSingleton.getInstance().getMessages());
                        messageLV.setAdapter(msgAdpt);
                        msgAdpt.notifyDataSetChanged();
                        messageRefresh.setRefreshing(false);
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
                UserTrans[] result = gson.fromJson(response.toString(), UserTrans[].class);
                ArrayList<ChatUser> usrs = new ArrayList<>();
                for(UserTrans c: result){
                    usrs.add(new ChatUser(c.id,c.username));
                }
                UserSingleton.getInstance().setChatUsers(usrs);
            }
        });
    }


    public void removeMessage(Long id){
        RequestParams params = new RequestParams();
        params.put("messageid", id);
        Log.d("msgDel", "Try: " + id);
        serverCom.delete("message/delete", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                retrieveMessages();
                String resp = "";
                try {
                    resp += new String(responseBody,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("msgDel", "Success: " + resp);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Log.d("msgDel", "Fail");
            }
        });
    }

    public void tokenLoginCheck(){
        final String token = UserSingleton.getInstance().doTokenCheck(MainActivity.this);
        if(!token.equals("")) {
            serverCom.setAuthHead(token);
            RequestParams params = new RequestParams();
            params.put("token", token);
            serverCom.post("user/tokenlogin", params, new JsonHttpResponseHandler(
            ){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String username = response.getString("username");
                        String token = response.getString("token");
                        Long id = response.getLong("id");
                        ChatUser owner = new ChatUser(id, username);
                        owner.setToken(token);

                        FileHandler fh = new FileHandler();
                        try {
                            fh.writeToFile(token, "token.txt", MainActivity.this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        UserSingleton.getInstance().setUser(owner);
                        serverCom.setAuthHead(token);
                        getUsers();
                        retrieveMessages();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast toast = Toast.makeText(LoginActivity.this, "Password or username is wrong, please try again", Toast.LENGTH_LONG);
                        //toast.show();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String benis, Throwable error) {
                    Log.d("tknLogin", "token login failed: " + token);
                    error.printStackTrace();
                    initiateLogin();
                }

            });
        }
        else{
            initiateLogin();
        }
    }

    public void initiateLogin(){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}