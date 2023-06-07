package view.pregame;

import javafx.scene.layout.Pane;
import utils.Graphics;

import java.util.Objects;

public class InitGameMenu {
    public Pane getPane() {
        Pane initGamePane = new Pane();
        initPane(initGamePane);
        return initGamePane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.png"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);

    }
}
