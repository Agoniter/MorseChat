package com.ragingdevs.morsechat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexfylling on 27.11.2016.
 */

public class ContactList {
    ArrayList<ChatUser> contacts;

    public ContactList() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(long id, String username) {
        contacts.add(new ChatUser(id, username));
    }

    public ArrayList<ChatUser> getContacts() {
        return contacts;
    }
}
