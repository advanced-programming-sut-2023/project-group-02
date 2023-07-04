package client.view;

import javafx.scene.layout.Pane;
import server.ChatDatabase;
import server.chat.Chat;
import utils.Graphics;

import java.util.Objects;

public class ChatMenu {
    private final Chat chat;
    private Pane rootPane;

    public ChatMenu(Chat chat) {
        this.chat = chat;
    }

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
