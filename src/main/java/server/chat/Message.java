package server.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.User;

public class Message {
    public final int id;
    private String text;
    public final User sender;
    private final Date sentAt;

    public Message(int id, String text, User sender, Date sentAt) {
        System.out.println("start of making message");
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.sentAt = sentAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return sentAt;
    }

    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(sentAt);
    }
}
