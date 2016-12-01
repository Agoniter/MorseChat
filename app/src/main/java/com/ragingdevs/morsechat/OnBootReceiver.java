package com.ragingdevs.morsechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vegard on 01/12/2016.
 */

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "onBoot received!", Toast.LENGTH_LONG);
        toast.show();
        context.startService(new Intent(context,MessageService.class));
    }
}

