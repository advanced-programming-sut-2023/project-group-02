package client.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.User;
import server.chat.Chat;
import utils.Graphics;

import java.util.ArrayList;
import java.util.Objects;

public class MessengerMenu {
    private Pane rootPane;
    private final User currentUser = Main.getPlayerConnection().getLoggedInUser();
    private ArrayList<Chat> chats = Main.getPlayerConnection().getPlayerChats();
    private VBox chatsVBox;

    public Pane getPane() {
        rootPane = new Pane();
        initPane();
        return rootPane;
    }

    private void initPane() {
        rootPane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/messenger_menu.png"))));
        rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Messenger.css")).toExternalForm());
        rootPane.setPrefSize(960, 540);
        addBackButton();
        initChats();
    }

    private void initChats() {
        chatsVBox = new VBox();
        chatsVBox.setSpacing(10);
        chatsVBox.setLayoutX(50);
        chatsVBox.setLayoutY(10);
        chatsVBox.setPrefWidth(860);
        chatsVBox.setPrefHeight(440);
        rootPane.getChildren().add(chatsVBox);
        addChats();
    }

    private void addChats() {

    }

    private VBox createChatPreviewVBox(Chat chat) {
        VBox chatPreviewVBox = new VBox();
        chatPreviewVBox.setSpacing(10);
        chatPreviewVBox.setPrefWidth(200);
        chatPreviewVBox.setPrefHeight(50);
        chatPreviewVBox.getStyleClass().add("chat-preview-vbox");
        chatPreviewVBox.setOnMouseClicked(mouseEvent -> {
            Main.setScene(new ChatMenu(chat).getPane());
        });
        chatPreviewVBox.getChildren().add(createChatPreviewHeader(chat));
        return chatPreviewVBox;
    }

    private Text createChatPreviewHeader(Chat chat) {
        Text title = new Text(chat.getName());
        title.getStyleClass().add("chat-preview-title");
        return title;
    }

    private void addBackButton() {
        ImageView backButton = createBackButton();
        backButton.setOnMouseClicked(mouseEvent -> {
            Main.setScene(new MainMenu().getPane());
        });
        rootPane.getChildren().add(backButton);
    }

    private ImageView createBackButton() {
        ImageView backButton = new ImageView(new Image(getClass().getResource("/images/Messenger/back.png").toExternalForm()));
        backButton.setFitHeight(20);
        backButton.setFitWidth(20);
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        return backButton;
    }
}
