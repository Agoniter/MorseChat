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
     ChatUser sender, recipient;

    public Message(ArrayList<Long> message,ChatUser recipient, ChatUser sender){
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

    public ChatUser getSender() {
        return sender;
    }

    public void setSender(ChatUser sender) {
        this.sender = sender;
    }

    public ChatUser getRecipient() {
        return recipient;
    }

    public void setRecipient(ChatUser recipient) {
        this.recipient = recipient;
    }

    public void play(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = new long[message.size()];

        for (int i = 0; i < message.size();i++){
            pattern[i] = message.get(i);
        }
        v.vibrate(pattern, -1);
    }

}
