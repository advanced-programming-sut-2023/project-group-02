package view.pregame;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import view.GameMenu;

public class InitMapMenu {
    private GameMenu gameMenu = new GameMenu();
    private Pane rootPane;
    private GridPane gridPane;

    public Pane getPane() {
        rootPane = gameMenu.getPane(true);
        gridPane = gameMenu.getGridPane();
        return rootPane;
    }
}
