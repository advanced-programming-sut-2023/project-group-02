package client.view;

import controllers.UserController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.User;
import utils.Graphics;

import java.util.Objects;

import client.view.pregame.PreGameMenu;

public class MainMenu {
    public Pane getPane() {
        Pane MainMenuPane = new Pane();
        initPane(MainMenuPane);
        return MainMenuPane;
    }

    public Pane getScoreBoardPane() {
        Pane scoreBoardPane = new Pane();
        initScoreBoardPane(scoreBoardPane);
        return scoreBoardPane;
    }

    private void initScoreBoardPane(Pane pane) {
        User[] scoreboard = Main.getPlayerConnection().getScoreboard();

        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/score-board.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);

        VBox players = new VBox();
        players.setStyle("-fx-background-color: maroon");
        for (int i = 0; i < scoreboard.length; i++) {
            User player = scoreboard[i];
            players.getChildren().add(playersInfoInHBox(player, i));
        }

        ScrollPane scrollPane = new ScrollPane(players);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setTranslateX(300);
        scrollPane.setTranslateY(15);
        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(510);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            User[] newScoreboard = Main.getPlayerConnection().getScoreboard();
            players.getChildren().clear();
            for (int i = 0; i < newScoreboard.length; i++) {
                User player = newScoreboard[i];
                players.getChildren().add(playersInfoInHBox(player, i));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            timeline.stop();
            Main.setScene(getPane());
        });
        backButton.getStyleClass().add("button1");
        backButton.setTranslateY(5);
        backButton.setTranslateY(500);

        pane.getChildren().addAll(scrollPane, backButton);
    }

    private HBox playersInfoInHBox(User user, int index) {
        ImageView avatar = Graphics.getAvatarWithPath(user.getAvatarPath());
        avatar.setFitWidth(30);
        avatar.setFitHeight(30);

        Text rank = makeTextWithColor("" + (index + 1), Color.GREEN);
        Text username = makeTextWithColor(user.getUsername(), Color.YELLOW);
        Text nickname = makeTextWithColor(user.getNickname(), Color.BLUE);
        Text highScore = makeTextWithColor("" + user.getHighScore(), Color.WHITE);

        ImageView onlineStatus = new ImageView(getClass()
            .getResource("/images/others/" + (user.isOnline() ? "check" : "cross") + ".jpg").toExternalForm());
        onlineStatus.setFitWidth(20);
        onlineStatus.setFitHeight(20);

        Text lastSeen = makeTextWithColor("", Color.GREY);
        if (user.isOnline())
            lastSeen.setText("online");
        else if (user.getLastSeen() != null)
            lastSeen.setText(user.getLastSeen().toString());
        else
            lastSeen.setText("never");

        HBox hBox = new HBox(rank, avatar, username, nickname, highScore, onlineStatus, lastSeen);
        hBox.getStyleClass().add("button1");
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    private Text makeTextWithColor(String content, Color color) {
        Text text = new Text(content);
        text.getStyleClass().add("button1");
        text.setFill(color);
        return text;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/MainMenu.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        VBox buttons = new VBox();
        buttons.setSpacing(15);
        buttons.setTranslateX(400);
        buttons.setTranslateY(170);

        Button enterProfileMenuButton = makeButton(buttons, "Enter Profile Menu");
        enterProfileMenuButton.setOnAction(event -> {
            Main.setScene(new ProfileMenu().getPane());
        });

        Button enterGameMenu = makeButton(buttons, "Enter Lobby Menu");
        enterGameMenu.setOnAction(event -> {
            Main.setScene(new PreGameMenu().getPane());
        });

        Button enterMessenger = makeButton(buttons, "Enter Messenger");
        enterMessenger.setOnAction(event -> {
            Main.setScene(new MessengerMenu().getPane());
        });

        Button friends = makeButton(buttons, "Friends");
        friends.setOnAction(event -> {
            Main.setScene(new FriendsMenu().getPane());
        });

        Button scoreBoard = makeButton(buttons, "ScoreBoard");
        scoreBoard.setOnAction(event -> Main.setScene(getScoreBoardPane()));


        Button logout = makeButton(buttons, "logout");
        logout.setOnAction(event -> {
            logout();
            Main.setScene(Main.getTitlePane());
        });

        pane.getChildren().add(buttons);
    }

    public static Button makeButton(VBox buttons, String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button1");
        buttons.getChildren().add(button);
        return button;
    }

    public static void logout() {
        UserController.logout();
        Main.getPlayerConnection().logout();
    }
}
