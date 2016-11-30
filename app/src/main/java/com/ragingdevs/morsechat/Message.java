package com.ragingdevs.morsechat;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.content.res.TypedArrayUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alexfylling on 18.11.2016.
 */

public class Message implements Serializable {

    ArrayList<Long> message;
    long sender, recipient;

    public Message(ArrayList<Long> message,long recipient, long sender){
            this.message = message;
            this.recipient = recipient;
            this.sender = sender;

    }
    public ArrayList<Long> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Long> message) {
        this.message = message;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public void play(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Long[] tmp = (Long[])message.toArray();
        long[] pattern = new long[message.size()];

        for (int i = 0; i < message.size();i++){
            pattern[i] = tmp[i];
        }
        v.vibrate(pattern, -1);
    }

}
