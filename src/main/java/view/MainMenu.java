package view;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.User;
import utils.Graphics;
import view.pregame.PreGameMenu;

import java.util.Objects;

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
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/score-board.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);

        VBox players = new VBox();
        players.setStyle("-fx-background-color: maroon");
        for (int i = 0; i < UserController.getUsersSorted().size(); i++) {
            User player = UserController.getUsersSorted().get(i);
            players.getChildren().add(playersInfoInHBox(player));
        }

        ScrollPane scrollPane = new ScrollPane(players);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setTranslateX(300);
        scrollPane.setTranslateY(15);
        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(510);

        Button backButton = new Button("back");
        backButton.setOnAction(event -> Main.setScene(getPane()));
        backButton.getStyleClass().add("button1");
        backButton.setTranslateY(5);
        backButton.setTranslateY(500);

        pane.getChildren().addAll(scrollPane, backButton);
    }

    private HBox playersInfoInHBox(User user) {
        ImageView avatar = Graphics.getAvatarWithPath(user.getAvatarPath());
        avatar.setFitWidth(30);
        avatar.setFitHeight(30);

        Text rank = makeTextWithColor("" + user.getRank(), Color.GREEN);
        Text username = makeTextWithColor(user.getUsername(), Color.YELLOW);
        Text nickname = makeTextWithColor(user.getNickname(), Color.BLUE);
        Text highScore = makeTextWithColor("" + user.getHighScore(), Color.WHITE);

        HBox hBox = new HBox(rank, avatar, username, nickname, highScore);
        hBox.getStyleClass().add("button1");
        hBox.setSpacing(10);
        return hBox;
    }

    private Text makeTextWithColor(String content, Color color) {
        Text text = new Text(content);
        text.getStyleClass().add("button1");
        text.setFill(color);
        return text;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.png"))));
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

        Button enterGameMenu = makeButton(buttons, "Enter Game Menu");
        enterGameMenu.setOnAction(event -> Main.setScene(new PreGameMenu().getPane()));

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
    }
}
