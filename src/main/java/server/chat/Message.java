package server.chat;

import java.util.Date;

import models.User;

public class Message {
    public final int id;
    private String text;
    public final User sender;
    public final Chat chat;
    public final Date sentAt;

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
}
