package utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import client.view.Main;
import client.view.enums.ProfileMenuMessages;
import models.User;

public class Graphics {
    public static Background getBackground(URL url) {
        return new Background(new BackgroundFill(
            new ImagePattern(new Image(Objects.requireNonNull(url.toExternalForm()))),
            null,
            null)
        );
    }

    public static void showMessagePopup(String message) {
        Popup popup = new Popup();
        popup.setHeight(100);
        popup.setWidth(100);
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-background-color: white");
        messageLabel.setFont(new Font("Arial", 30));
        messageLabel.setText(message);
        popup.getContent().add(messageLabel);
        popup.setAutoHide(true);
        popup.show(Main.getStage());
    }

    public static ImageView getAvatarWithPath(String path) {
        ImageView imageView = new ImageView();

        try {
            if (path.startsWith("file:/")) {
                File file = new File(path.substring(5));
                imageView.setImage(new Image(file.toURI().toURL().toExternalForm()));
            } else {
                imageView.setImage(new Image(Graphics.class.getResource(path).toExternalForm()));
            }
        } catch (Exception e) {
        }

        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        return imageView;
    }

    public static ImageView[] getDefaultAvatars() {
        ImageView[] defaultAvatars = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            defaultAvatars[i] = getAvatarWithPath(Graphics.class.getResource("/images/avatars/" + (i + 1) + ".png").toExternalForm());
        }
        return defaultAvatars;
    }

    public static Captcha generateCaptcha(double layoutX, double layoutY) throws IOException {
        File randomCaptcha = Randoms
            .getRandomFileFromDirectory(Objects.requireNonNull(Graphics.class.getResource("/images/captcha")));
        Captcha captcha = new Captcha(randomCaptcha.getName().substring(0, 4), layoutX, layoutY);
        return captcha;
    }

    public static void forceTextFieldsAcceptNumbersOnly(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                return;
            }
            if (!Utils.isInteger(newValue) || newValue.length() > maxLength) {
                textField.setText(oldValue);
            }
        });
    }

    public static void showProfile(Pane currentPane, User currentUser, User userToSearch) {
        Pane profilePane = new Pane();
        profilePane.getStylesheets().add(Objects.requireNonNull(Graphics.class.getResource("/CSS/Menus.css")).toExternalForm());
        profilePane.setPrefSize(600, 400);
        profilePane.setLayoutX(180);
        profilePane.setLayoutY(70);
        profilePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        currentPane.getChildren().add(profilePane);
        profilePane.requestFocus();

        ImageView imageView = new ImageView(new Image(Graphics.class.getResource("/images/buttons/cancel.png").toExternalForm()));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setLayoutX(575);
        imageView.setOnMouseClicked(mouseEvent -> currentPane.getChildren().remove(profilePane));
        profilePane.getChildren().add(imageView);
        initProfilePane(currentPane, profilePane, currentUser, userToSearch);
    }

    private static void initProfilePane(Pane currentPane, Pane profilePane, User currentUser, User userToSearch) {
        profilePane.getChildren().add(getUserDetails(currentPane, userToSearch.getUsername()));
        Text friendsText = new Text("List of Friends:");
        friendsText.getStyleClass().add("title1");
        friendsText.setLayoutY(100);
        friendsText.setLayoutX(20);
        VBox friends = new VBox();
        friends.setSpacing(5);
        for (String friendUsername : userToSearch.getFriends()) {
            friends.getChildren().add(getUserDetails(currentPane, friendUsername));
        }
        ScrollPane scrollPane = new ScrollPane(friends);
        scrollPane.setLayoutY(120);
        scrollPane.setLayoutX(20);
        scrollPane.setPrefWidth(150);
        scrollPane.setPrefHeight(250);

        profilePane.getChildren().addAll(friendsText, scrollPane);
        addFriendRequestFields(profilePane, currentUser, userToSearch);
    }

    private static void addFriendRequestFields(Pane profilePane, User currentUser, User userToSearch) {
        if (currentUser.getUsername().equals(userToSearch.getUsername()))
            return;
        Text alreadyFriends = new Text("You have sent a friend request");
        alreadyFriends.setLayoutX(250);
        alreadyFriends.setLayoutY(120);
        alreadyFriends.getStyleClass().add("title1");
        profilePane.getChildren().add(alreadyFriends);
        for (String friendUsername : currentUser.getFriends()) {
            if (friendUsername.equals(userToSearch.getUsername())) {
                alreadyFriends.setText("You are friend of this user");
                return;
            }
        }
        for (User friend : userToSearch.getReceivedFriendRequests()) {
            if (friend.getUsername().equals(currentUser.getUsername()))
                return;
        }
        profilePane.getChildren().remove(alreadyFriends);
        Text requestText = new Text("request friendship");
        requestText.setLayoutX(300);
        requestText.setLayoutY(120);
        requestText.getStyleClass().add("title1-with-hover");
        ImageView addFriend = new ImageView(new Image(Graphics.class.getResource("/images/Messenger/plus.png").toExternalForm()));
        addFriend.setLayoutX(270);
        addFriend.setLayoutY(105);
        addFriend.setFitWidth(20);
        addFriend.setFitHeight(20);
        requestText.setOnMouseClicked(mouseEvent -> {
            profilePane.getChildren().removeAll(requestText, addFriend);
            profilePane.getChildren().add(alreadyFriends);
            Main.getPlayerConnection().sendFriendRequest(userToSearch);
        });
        profilePane.getChildren().addAll(requestText, addFriend);
    }

    private static HBox getUserDetails(Pane currentPane, String username) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        ImageView avatar = Graphics.getAvatarWithPath("/images/avatars/0.jpg");
        avatar.setFitHeight(50);
        avatar.setFitWidth(50);
        avatar.setLayoutX(15);
        avatar.setLayoutY(20);
        Text usernameText = new Text(username);
        usernameText.setLayoutX(75);
        usernameText.setLayoutY(40);
        usernameText.getStyleClass().add("title1-with-hover");
        usernameText.setOnMouseClicked(mouseEvent -> {
            Main.getPlayerConnection().searchPlayer(currentPane, username);
        });
        hbox.getChildren().addAll(avatar, usernameText);
        return hbox;
    }
}
