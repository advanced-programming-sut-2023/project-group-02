package client.view.lobby;

import javafx.scene.layout.Pane;
import server.logic.Lobby;
import utils.Graphics;

import java.util.Objects;

public class LobbyMenu {
    private final Lobby lobby;
    private Pane pane;

    public LobbyMenu(Lobby lobby) {
        this.lobby = lobby;
    }

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
