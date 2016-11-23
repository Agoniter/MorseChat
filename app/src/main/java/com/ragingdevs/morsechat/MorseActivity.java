package com.ragingdevs.morsechat;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MorseActivity extends AppCompatActivity {

    int mDot = 200;
    int mDash = 500;
    int short_gap = 200;
    int medium_gap = 500;
    int long_gap = 1000;

    long lastDown;
    long lastDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button mMorseButton = (Button) findViewById(R.id.morse_button);
        mMorseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    mVibrator.vibrate(mDot);
                }


                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lastDuration = System.currentTimeMillis() - lastDown;

                    if (lastDuration < 201) {
                        mVibrator.vibrate(mDot);
                    }

                    if (lastDuration > 200 && lastDuration < 501) {
                        mVibrator.vibrate(mDash);
                    }

                    String tmpLastDown = String.valueOf(lastDown);
                    //Toast.makeText(getApplicationContext(), tmpLastDown, Toast.LENGTH_LONG).show();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    lastDown = System.currentTimeMillis();



                    String tmpLastDuration = String.valueOf(lastDuration);
                    Toast.makeText(getApplicationContext(), tmpLastDuration, Toast.LENGTH_SHORT).show();

                }


                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
