package server.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.User;

public class Message {
    public final int id;
    private String text;
    public final User sender;
    public final Chat chat;
    private final Date sentAt;

    public Message(String text, User sender, Chat chat) {
        System.out.println("start of making message");
        this.id = chat.getNextMessageId();
        this.text = text;
        this.sender = sender;
        this.chat = chat;
        System.out.println("making message " + sender.getUsername() + " " + chat.getMessages());
        sentAt = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(sentAt);
    }
}
