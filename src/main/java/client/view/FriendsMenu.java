package client.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.User;

import java.util.Objects;

public class FriendsMenu {

    public Pane getPane() {
        Pane pane = new Pane();
        initPane(pane);
        return pane;
    }

    private void initPane(Pane pane) {
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        pane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
        Text userSearchText = new Text("Username:");
        userSearchText.setLayoutX(250);
        userSearchText.setLayoutY(200);
        userSearchText.getStyleClass().add("title1");
        TextField userSearch = new TextField();
        userSearch.setLayoutX(390);
        userSearch.setLayoutY(175);
        userSearch.setPrefSize(200, 30);
        pane.getChildren().addAll(userSearchText, userSearch);
        addConfirmAndBack(pane, userSearch);
        addPendingFriendRequests(pane);
    }

    private void addPendingFriendRequests(Pane pane) {
        User currentUser = Main.getPlayerConnection().getLoggedInUser();
        VBox friendRequests = new VBox();
        friendRequests.setSpacing(10);
        for (User user : currentUser.getReceivedFriendRequests()) {
            friendRequests.getChildren().add(makeFriendRequestField(currentUser, user));
        }
        ScrollPane scrollPane = new ScrollPane(friendRequests);
        scrollPane.setPrefWidth(250);
        scrollPane.setPrefHeight(350);
        scrollPane.setLayoutY(100);
        pane.getChildren().add(scrollPane);
    }

    private HBox makeFriendRequestField(User currentUser, User user) {
        HBox requestField = new HBox();
        requestField.setSpacing(10);
        ImageView avatar = user.getAvatar();
        Text username = new Text(user.getUsername());
        username.getStyleClass().add("title1-with-hover");
        username.setOnMouseClicked(mouseEvent -> {
        });
        ImageView accept = new ImageView(new Image(getClass().getResource("/images/buttons/accept.png").toExternalForm()));
        accept.setFitWidth(10);
        accept.setFitHeight(10);
        ImageView reject = new ImageView(new Image(getClass().getResource("/images/buttons/reject.png").toExternalForm()));
        reject.setFitHeight(10);
        reject.setFitWidth(10);
        accept.setOnMouseClicked(mouseEvent -> {
        });
        reject.setOnMouseClicked(mouseEvent -> {
        });
        requestField.getChildren().addAll(avatar, username, accept, reject);
        return requestField;
    }

    private void addConfirmAndBack(Pane pane, TextField username) {
        ImageView backButton = new ImageView(new Image(getClass().getResource("/images/Messenger/back.png").toExternalForm()));
        backButton.setFitHeight(20);
        backButton.setFitWidth(20);
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        backButton.setOnMouseClicked(mouseEvent -> Main.setScene(new MainMenu().getPane()));
        Button confirm = new Button("Find User");
        confirm.setLayoutY(230);
        confirm.setLayoutX(420);
        confirm.getStyleClass().add("button1");
        confirm.setOnMouseClicked(mouseEvent -> {
            Main.getPlayerConnection().searchPlayer(pane, username.getText());
            username.setText("");
        });
        pane.getChildren().addAll(backButton, confirm);
    }
}
