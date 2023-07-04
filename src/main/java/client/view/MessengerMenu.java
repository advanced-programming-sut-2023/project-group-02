package client.view;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;
import server.ChatDatabase;
import server.ServerUserController;
import server.chat.Chat;
import server.chat.ChatType;
import utils.Graphics;

import java.util.ArrayList;
import java.util.Objects;

public class MessengerMenu {
    private Pane rootPane;
    private final User currentUser = Main.getPlayerConnection().getLoggedInUser();
    private ArrayList<Chat> chats = Main.getPlayerConnection().getPlayerChats();
    private VBox chatsVBox;
    private VBox makeChatVBox;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    HBox foundPlayer;
    Text notFoundText = new Text("Player with this username is not found!");

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
        initAddChat();
    }

    private void initChats() {
        chatsVBox = new VBox();
        chatsVBox.setSpacing(10);
        chatsVBox.setLayoutX(50);
        chatsVBox.setLayoutY(10);
        chatsVBox.setPrefWidth(860);
        chatsVBox.setPrefHeight(440);
        addChats();
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

    private void addChats() {
        // i think i should use player chats or ask about it
        for (Chat chat : ChatDatabase.getChats()) {
            chatsVBox.getChildren().add(createChatPreviewVBox(chat));
        }
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
        chatDetails.setAlignment(Pos.CENTER);

        TextField chatName = new TextField();
        chatName.setPromptText("Name");
        chatName.setMaxWidth(150);

        ChoiceBox<ChatType> chatType = new ChoiceBox<>();
        chatType.getItems().addAll(ChatType.PRIVATE, ChatType.PUBLIC, ChatType.ROOM);

        Button makeChat = new Button("Make Chat");
        makeChat.getStyleClass().add("button1");
        makeChat.setOnAction(event -> {
            //TODO : making chat
            initPane();
        });

        Text addPlayerText = new Text("Add Users:");
        TextField searchPlayers = new TextField();
        searchPlayers.setMaxWidth(200);
        searchPlayers.setPromptText("Search...");

        chatDetails.getChildren().addAll(chatName,chatType,makeChat,addPlayerText,searchPlayers);
        searchPlayers.textProperty().addListener((observable, oldValue, newValue) -> searchPlayers(searchPlayers,notFoundText));

        makeChatVBox.getChildren().add(chatDetails);
    }

    private void searchPlayers(TextField playersNameTextField, Text notFoundText) {
        if (playersNameTextField.getText() == null || playersNameTextField.getText().equals("")) return;
        String playersName = playersNameTextField.getText();
        User user;
        //TODO : dont use User Controller
        if ((user = UserController.findUserWithUsername(playersName)) == null) {
            makeChatVBox.getChildren().remove(foundPlayer);
            notFoundText.setVisible(true);
            if (!makeChatVBox.getChildren().contains(notFoundText))
                makeChatVBox.getChildren().add(notFoundText);
        } else {
            System.out.println("player is found bro");
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
        username.setFont(new Font("Arial",20));
        username.setFill(Color.DARKBLUE);

        HBox hBox = new HBox(10,avatar,username);
        hBox.setOnMouseClicked(event -> selectPlayer(user));
        return hBox;
    }

    private void selectPlayer(User player) {
        selectedUsers.remove(player);
        selectedUsers.add(player);
        System.out.println("selected players : " + selectedUsers);
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
        backButton.setOnMouseClicked(mouseEvent -> Main.setScene(new MainMenu().getPane()));
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
