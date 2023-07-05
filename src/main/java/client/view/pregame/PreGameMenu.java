package client.view.pregame;

import client.view.lobby.JoinLobbiesMenu;
import controllers.GameMenuController;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.Graphics;

import java.util.Objects;

import client.view.GameMenu;
import client.view.Main;
import client.view.MainMenu;

public class PreGameMenu {
    public Pane getPane() {
        Pane preGameMenuPane = new Pane();
        initPane(preGameMenuPane);
        return preGameMenuPane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        VBox buttons = new VBox();
        buttons.setSpacing(15);
        buttons.setTranslateX(400);
        buttons.setTranslateY(170);

        Button startNewGame = MainMenu.makeButton(buttons, "Make a new game");
        startNewGame.setOnAction(event -> Main.setScene(new InitGameMenu().getPane()));

        Button loadGameButton = MainMenu.makeButton(buttons, "Join a game");
        loadGameButton.setOnAction(event -> Main.setScene(new JoinLobbiesMenu().getPane()));

        Button back = MainMenu.makeButton(buttons, "Back");
        back.setOnAction(event -> Main.setScene(new MainMenu().getPane()));

        pane.getChildren().add(buttons);
    }

    private void loadGame(Button button) {
        if (GameMenuController.loadGame()) {
            Main.setScene(new GameMenu().getPane(false, null));
            Main.getStage().setFullScreen(true);
        } else {
            Graphics.showMessagePopup("There is no saved game!");
            button.setCancelButton(true);
        }
    }
}
