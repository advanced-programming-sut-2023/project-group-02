package client.view.lobby;

import javafx.scene.layout.Pane;
import utils.Graphics;

import java.util.Objects;

public class joinLobbiesMenu {
    private Pane pane;

    public Pane getPane() {
        pane = new Pane();
        initPane(pane);
        return pane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/lobby-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
    }
}
