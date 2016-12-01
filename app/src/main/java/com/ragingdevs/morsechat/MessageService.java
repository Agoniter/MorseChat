package com.ragingdevs.morsechat;

/**
 * Created by Vegard on 01/12/2016.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageService extends Service {
    Thread worker;
    AtomicBoolean live = new AtomicBoolean(true);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Starting MessageService:");
        synchronized (this) {
            if(worker == null && live.get()) {
                worker = new Thread() {
                    @Override
                    public void run() {
                        while(live.get()) {
                           //Check for new message code goes here


                            try {
                                Thread.sleep(5000); // Sleep 5 sec.
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Time to die...");
                    }
                };
                worker.start();
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("MyService::onDestroy()");
        live.set(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

