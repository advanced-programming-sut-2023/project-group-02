package client.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.User;
import server.chat.Chat;
import server.chat.ChatType;
import server.chat.Message;
import utils.Graphics;

import java.util.Objects;

public class ChatMenu {
    private final Chat chat;
    private User currentUser = Main.getPlayerConnection().getLoggedInUser();
    private Pane rootPane;
    private VBox mainVBox = new VBox();
    private VBox chatMessages = new VBox();

    public ChatMenu(Chat chat) {
        this.chat = chat;
    }

    public Pane getPane() {
        rootPane = new Pane();
        initPane();
        return rootPane;
    }

    private void initPane() {
        rootPane.getChildren().clear();
        mainVBox.getChildren().clear();
        chatMessages.getChildren().clear();
        rootPane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/messenger_menu.png"))));
        rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Messenger.css")).toExternalForm());
        rootPane.setPrefSize(960, 540);
        mainVBox.setSpacing(10);
        mainVBox.setTranslateX(300);
        mainVBox.setTranslateY(10);
        mainVBox.setAlignment(Pos.CENTER);
        addChatTitle();
        addBackButton();
        makeChatMessagesVBox();
        makeScrollPane();
        addBottomBar();
        rootPane.getChildren().add(mainVBox);
    }

    private void addChatTitle() {
        Label chatName = new Label(chat.getName() + " " + chat.id);
        if (chat.type.equals(ChatType.ROOM)) chatName.setText(chatName.getText() + "\n members: " + chat.getUsers().size());
        chatName.setTextFill(Color.WHITE);
        chatName.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
        mainVBox.getChildren().add(chatName);
    }

    private void makeScrollPane() {
        ScrollPane scrollPane = new ScrollPane(chatMessages);
        scrollPane.setPrefWidth(500);
        scrollPane.setPrefHeight(400);
        scrollPane.setVvalue(1.0);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainVBox.getChildren().add(scrollPane);
    }

    private void makeChatMessagesVBox() {
        System.out.println("we have messages right? " + chat.getMessages());
        chatMessages.setAlignment(Pos.BOTTOM_LEFT);
        chatMessages.setPadding(new Insets(10));
        for (Message message : chat.getMessages()) {
            chatMessages.getChildren().add(makeOneMessageVBox(message));
        }

    }

    private VBox makeOneMessageVBox(Message message) {
        Text messageText = new Text(message.getText());

        ImageView editButton = new ImageView(new Image(getClass().getResource("/images/Messenger/edit.png").toExternalForm()));
        editButton.setFitWidth(10);
        editButton.setFitHeight(10);
        editButton.setOnMouseClicked(event -> {
            //TODO : Edit
        });

        ImageView deleteButton = new ImageView(new Image(getClass().getResource("/images/Messenger/delete.png").toExternalForm()));
        deleteButton.setFitHeight(10);
        deleteButton.setFitWidth(10);
        deleteButton.setOnMouseClicked(event -> {
            //TODO : Delete
        });

        Text date = new Text(message.sentAt + "");

        HBox deleteAndEdit = new HBox(10,editButton,deleteButton,date);
        VBox wholeMessage = new VBox(10,messageText,deleteAndEdit);

        if (!message.sender.equals(currentUser)) {
            wholeMessage.setBackground(new Background(new BackgroundFill(Color.CYAN,null,null)));
            messageText.setFill(Color.WHITE);
        }

        return wholeMessage;
    }

    private void addBottomBar() {
        TextField textField = new TextField();
        textField.setPromptText("Message:");
        textField.setPrefWidth(300);

        ImageView sendButton = new ImageView(new Image(getClass().getResource("/images/Messenger/send.png").toExternalForm()));
        sendButton.setFitWidth(20);
        sendButton.setFitHeight(20);
        sendButton.setOnMouseClicked(event -> {
            if (textField.getText() != null && !textField.getText().equals("")) {
                chat.sendMessage(currentUser,textField.getText());
            }
            textField.setText("");
            initPane();
        });

        HBox bottomBar = new HBox(10,textField,sendButton);
        mainVBox.getChildren().add(bottomBar);
    }

    private void addBackButton() {
        Button backButton = new Button("Back");
        backButton.setTranslateX(10);
        backButton.setTranslateY(10);
        backButton.getStyleClass().add("button1");
        backButton.setOnAction(event -> Main.getStage().setScene(new Scene(new MessengerMenu().getPane())));
        rootPane.getChildren().add(backButton);
    }
}
