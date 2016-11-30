package com.ragingdevs.morsechat;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MorseActivity extends AppCompatActivity {

    long lastDown;
    long lastDownDuration;
    long lastUp;
    long lastUpDuration;
    boolean firstTouch;
    ServerCom serverCom;

    ArrayList<Long> morseMessage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        serverCom = new ServerCom();
        firstTouch = true;
        lastUpDuration = 0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Button mMorseButton = (Button) findViewById(R.id.morse_button);
        mMorseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    if(!firstTouch) {
                        lastUpDuration = System.currentTimeMillis() - lastUp;
                    }
                    lastDown = System.currentTimeMillis();

                    morseMessage.add(lastUpDuration);

                    // For testing
                    //String tmpLastDown = String.valueOf(lastUpDuration);
                    //Toast.makeText(getApplicationContext(), tmpLastDown, Toast.LENGTH_LONG).show();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    lastDownDuration = System.currentTimeMillis() - lastDown;
                    firstTouch = false;
                    lastUp = System.currentTimeMillis();

                    morseMessage.add(lastDownDuration);

                    // For testing
                    //String tmpLastDuration = String.valueOf(lastDownDuration);
                    //Toast.makeText(getApplicationContext(), tmpLastDuration, Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });

        Button mSendButton = (Button) findViewById(R.id.send_message_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morseMessage.remove(0);
                // TODO: Send list
                RequestParams params = new RequestParams();
                serverCom.get("debug/checktoken",params , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody, StandardCharsets.UTF_8);
                        Log.d("usrchk", response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        error.printStackTrace();
                    }
                });
                // For testing purpose
                //Log.d("Morse Message", morseMessage.toString());
                //morseMessage.clear();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
