package view;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Game;
import utils.Graphics;
import view.enums.MainMenuMessages;

import java.util.Objects;

public class MainMenu {
    public Pane getPane() {
        Pane MainMenuPane = new Pane();
        initPane(MainMenuPane);
        return MainMenuPane;
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
            Main.getStage().setScene(new Scene(new ProfileMenu().getPane()));
            Main.getStage().show();
        });

        Button enterGameMenu = makeButton(buttons, "Enter Game Menu");
        enterGameMenu.setOnAction(event -> Main.setScene(new GameMenu().getPane()));

        Button scoreBoard = makeButton(buttons,"ScoreBoard");
        scoreBoard.setOnAction(event -> System.out.println(UserController.getUsersSorted()));


        Button logout = makeButton(buttons, "logout");
        logout.setOnAction(event -> {
            logout();
            Main.setScene(Main.getTitlePane());
        });

        pane.getChildren().add(buttons);
    }

    private Button makeButton(VBox buttons, String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button1");
        buttons.getChildren().add(button);
        return button;
    }

    public static void logout() {
        UserController.logout();
    }
}
