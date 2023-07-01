package models.chat;

import java.util.Date;

import models.User;

public class Message {
    public final int id;
    private String text;
    public final User sender;
    public final Chat chat;
    public final Date sentAt = new Date();

    public Message(int id, String text, User sender, Chat chat) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
