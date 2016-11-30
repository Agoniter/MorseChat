package com.ragingdevs.morsechat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vegard on 30/11/2016.
 */

public class MessageContainer implements Serializable {
    ArrayList<Long> recipients;
    ArrayList<Long> message;
    Long sender;

    public MessageContainer(){
    }

    public ArrayList<Long> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<Long> recipients) {
        this.recipients = recipients;
    }

    public ArrayList<Long> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Long> message) {
        this.message = message;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }
}
