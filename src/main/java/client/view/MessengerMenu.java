package client.view;

import javafx.scene.layout.Pane;
import models.User;
import utils.Graphics;

import java.util.Objects;

public class MessengerMenu {
    Pane rootPane;
    private final User currentUser = Main.getPlayerConnection().getLoggedInUser();

    public Pane getPane() {
        rootPane = new Pane();
        initPane();
        return rootPane;
    }

    private void initPane() {
        rootPane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/messenger_menu.png"))));
        rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Messenger.css")).toExternalForm());
        rootPane.setPrefSize(960, 540);
        
    }
}
