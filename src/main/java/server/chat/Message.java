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

    public Message(int id, String text, User sender, Chat chat, Date sentAt) {
        System.out.println("start of making message");
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.chat = chat;
        System.out.println("making message " + sender.getUsername() + " " + chat.getMessages());
        this.sentAt = sentAt;
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
