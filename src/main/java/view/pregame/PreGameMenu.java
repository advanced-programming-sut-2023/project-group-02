package view.pregame;

import controllers.GameMenuController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.Graphics;
import view.GameMenu;
import view.Main;
import view.MainMenu;
import view.ProfileMenu;

import java.util.Objects;

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

        Button startNewGame = MainMenu.makeButton(buttons, "Start New Game");
        startNewGame.setOnAction(event -> Main.setScene(new InitGameMenu().getPane()));

        Button loadGameButton = MainMenu.makeButton(buttons, "Load Existing Game");
        loadGameButton.setOnAction(event -> loadGame(loadGameButton));

        Button back = MainMenu.makeButton(buttons, "Back");
        back.setOnAction(event -> Main.setScene(new MainMenu().getPane()));

        pane.getChildren().add(buttons);
    }

    private void loadGame(Button button) {
        if (GameMenuController.loadGame()) {
            Main.setScene(new GameMenu().getPane(false));
        } else {
            Graphics.showMessagePopup("There is no saved game!");
            button.setCancelButton(true);
        }
    }
}
