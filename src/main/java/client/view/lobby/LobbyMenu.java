package client.view.lobby;

import client.view.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.User;
import server.logic.Lobby;
import utils.Graphics;

import java.util.Objects;

public class LobbyMenu {
    private Lobby lobby;
    private Pane pane = new Pane();

    public LobbyMenu(Lobby lobby) {
        this.lobby = lobby;
        ReceiveLobbyUpdates receiveLobbyUpdates = new ReceiveLobbyUpdates(this, lobby.getID());
        receiveLobbyUpdates.start();
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
        addPlayersHBox(pane);

    }

    private void addPlayersHBox(Pane pane) {
        ImageView star = new ImageView(new Image(getClass().getResource("/images/others/star.png").toExternalForm()));
        star.setFitWidth(15);
        star.setFitHeight(15);
        star.setLayoutX(10);
        star.setLayoutY(50);
        HBox players = new HBox();
        players.setSpacing(15);
        players.setLayoutX(30);
        players.setLayoutY(50);
        players.setPrefWidth(200);
        players.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        for (User player : lobby.getMembers()) {
            players.getChildren().add(Graphics.getUserDetails(pane, player.getUsername(), player.getNickname()));
        }
        pane.getChildren().addAll(players, star);
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
        Main.setScene(getPane());
    }
}
