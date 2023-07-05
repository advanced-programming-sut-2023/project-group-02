package client.view;

import controllers.UserController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.User;
import server.chat.Chat;
import server.chat.ChatType;
import utils.Graphics;

import java.util.ArrayList;
import java.util.Objects;

public class MessengerMenu {
    private Pane rootPane;
    private final User currentUser = Main.getPlayerConnection().getLoggedInUser();
    private ArrayList<Chat> chats;
    private VBox chatsVBox;
    private VBox makeChatVBox;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    private Timeline timeline;
    private ChatMenu currentChatMenu;
    HBox foundPlayer;
    Text notFoundText = new Text("Player with this username is not found!");

    public Pane getPane() {
        rootPane = new Pane();
        initPane();
        return rootPane;
    }

    private void initPane() {
        currentChatMenu = null;
        Main.getPlayerConnection().getPublicChat();
        chats = Main.getPlayerConnection().getChats();
        rootPane.getChildren().clear();
        rootPane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/messenger_menu.png"))));
        rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Messenger.css")).toExternalForm());
        rootPane.setPrefSize(960, 540);
        addBackButton();
        initChats();
        initAddChat();

        timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            chats = Main.getPlayerConnection().getChats();
            if (currentChatMenu != null) {
                for (Chat chat : chats) {
                    if (chat.id == currentChatMenu.getChat().id) {
                        currentChatMenu.setChat(chat);
                        break;
                    }
                }
            }
            initChats();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void initChats() {
        rootPane.getChildren().remove(chatsVBox);
        chatsVBox = new VBox();
        chatsVBox.setSpacing(10);
        chatsVBox.setLayoutX(50);
        chatsVBox.setLayoutY(10);
        chatsVBox.setPrefWidth(500);
        chatsVBox.setPrefHeight(440);
        for (Chat chat : chats) {
            chatsVBox.getChildren().add(createChatPreviewVBox(chat));
        }
        rootPane.getChildren().add(chatsVBox);
    }

    private void initAddChat() {
        makeChatVBox = new VBox(20);
        makeChatVBox.setPrefWidth(300);
        makeChatVBox.setTranslateY(10);
        makeChatVBox.setTranslateX(700);
        addMakeChatButton();
        rootPane.getChildren().add(makeChatVBox);
    }

    private boolean publicRoomExists(ArrayList<Chat> chats) {
        for (Chat chat : chats) {
            if (chat.type.equals(ChatType.PUBLIC))
                return true;
        }
        return false;
    }

    private void addMakeChatButton() {
        ImageView addChatButton = new ImageView(new Image(getClass().getResource("/images/Messenger/plus.png").toExternalForm()));
        addChatButton.setFitHeight(20);
        addChatButton.setFitWidth(20);
        addChatButton.setOnMouseClicked(event -> makeMakeChatVBox());
        makeChatVBox.getChildren().add(addChatButton);
    }

    private void makeMakeChatVBox() {
        VBox chatDetails = new VBox(10);

        TextField chatName = new TextField();
        chatName.setPromptText("Name");
        chatName.setMaxWidth(150);

        ChoiceBox<ChatType> chatType = new ChoiceBox<>();
        chatType.getItems().addAll(ChatType.PRIVATE, ChatType.ROOM);
        chatType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(ChatType.PRIVATE)) chatName.setDisable(true);
            else chatType.setDisable(false);
        });

        Button makeChat = new Button("Make Chat");
        makeChat.getStyleClass().add("button1");
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button1");
        HBox buttons = new HBox(10, makeChat, cancelButton);

        makeChat.setOnAction(event -> {
            makeChat(chatName, chatType);
            reset();
            initPane();
        });
        cancelButton.setOnAction(event -> reset());

        Text addPlayerText = new Text("Add Users:");
        TextField searchPlayers = new TextField();
        searchPlayers.setMaxWidth(200);
        searchPlayers.setPromptText("Search...");

        chatDetails.getChildren().addAll(chatType, chatName, buttons, addPlayerText, searchPlayers);
        searchPlayers.textProperty().addListener((observable, oldValue, newValue) -> searchPlayers(searchPlayers, notFoundText));

        makeChatVBox.getChildren().add(chatDetails);
    }


    private void makeChat(TextField chatName, ChoiceBox<ChatType> chatType) {
        if (chatType.getValue() == null || (chatType.getValue() != ChatType.PRIVATE && (chatName.getText() == null || chatName.getText().equals(""))))
            Graphics.showMessagePopup("Fill all the fields!");

        String name = chatName.getText();
        ChatType type = chatType.getValue();
        if (type.equals(ChatType.PRIVATE)) {
            Main.getPlayerConnection().makePrivateChatWith(selectedUsers.get(selectedUsers.size() - 1));
        } else {
            Main.getPlayerConnection().makeGroupChat(name, selectedUsers);
        }
    }

    private void searchPlayers(TextField playersNameTextField, Text notFoundText) {
        if (playersNameTextField.getText() == null || playersNameTextField.getText().equals("")) return;
        String playersName = playersNameTextField.getText();
        User user;
        //TODO : dont use User Controller
        if ((user = UserController.findUserWithUsername(playersName)) == null || user.equals(currentUser)) {
            makeChatVBox.getChildren().remove(foundPlayer);
            notFoundText.setVisible(true);
            if (!makeChatVBox.getChildren().contains(notFoundText))
                makeChatVBox.getChildren().add(notFoundText);
        } else {
            makeChatVBox.getChildren().remove(foundPlayer);
            makeChatVBox.getChildren().remove(notFoundText);
            foundPlayer = makePlayersHBox(user);
            makeChatVBox.getChildren().add(foundPlayer);
        }
    }

    private HBox makePlayersHBox(User user) {
        ImageView avatar = user.getAvatar();
        avatar.setFitHeight(30);
        avatar.setFitWidth(30);

        Text username = new Text(user.getUsername());
        username.setFont(new Font("Arial", 20));
        if (selectedUsers.contains(user)) username.setFill(Color.RED);
        else username.setFill(Color.DARKBLUE);

        HBox hBox = new HBox(10, avatar, username);
        hBox.setOnMouseClicked(event -> {
            selectPlayer(user);
            username.setFill(Color.RED);
        });
        return hBox;
    }

    private void selectPlayer(User player) {
        selectedUsers.remove(player);
        selectedUsers.add(player);
    }

    private VBox createChatPreviewVBox(Chat chat) {
        VBox chatPreviewVBox = new VBox();
        chatPreviewVBox.setSpacing(10);
        chatPreviewVBox.setPrefWidth(200);
        chatPreviewVBox.setPrefHeight(50);
        chatPreviewVBox.getStyleClass().add("chat-preview-vbox");
        chatPreviewVBox.setOnMouseClicked(mouseEvent -> {
            currentChatMenu = new ChatMenu(chat, null);
            Main.setScene(currentChatMenu.getPane());
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
            timeline.stop();
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

    private void reset() {
        selectedUsers.clear();
        makeChatVBox = new VBox();
        initPane();
    }
}
