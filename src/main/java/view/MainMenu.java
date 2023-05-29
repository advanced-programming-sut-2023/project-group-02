package view;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Game;
import utils.Graphics;
import utils.Parser;
import view.enums.MainMenuMessages;

import java.util.Objects;
import java.util.Scanner;

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
        enterGameMenu.setOnAction(event -> {
            Main.setScene(new GameMenu().getPane());
            //TODO enter game menu in a clean way
        });

        Button logout = makeButton(buttons, "logout");
        logout.setOnAction(event -> {
            MainMenuMessages output = logout();
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

    public static MainMenuMessages logout() {
        if (UserController.getCurrentUser() == null)
            return MainMenuMessages.ALREADY_LOGGED_OUT;
        UserController.logout();
        return MainMenuMessages.LOGOUT_SUCCESSFUL;
    }
}
