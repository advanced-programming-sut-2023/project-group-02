package client.view.lobby;

import client.view.Main;
import client.view.pregame.PreGameMenu;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import server.logic.Lobby;
import utils.Graphics;

import java.util.ArrayList;
import java.util.Objects;

public class JoinLobbiesMenu {
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
        pane.getChildren().add(createBackButton());
        addLobbiesVBox(pane);
        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                Main.setScene(new JoinLobbiesMenu().getPane());
            }
        });
    }

    private void addLobbiesVBox(Pane pane) {
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setLayoutX(320);
        vBox.setLayoutY(120);
        ArrayList<Lobby> lobbies = Main.getPlayerConnection().getAvailableLobbies();
        for (Lobby lobby : lobbies) {
            vBox.getChildren().add(createLobbyHBox(lobby));
        }
        pane.getChildren().add(vBox);
    }

    private HBox createLobbyHBox(Lobby lobby) {
        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setPrefSize(300, 50);
        int numberOfActivePlayers = lobby.getMembers().size();
        Text details = new Text("ID: #" + lobby.getID() + " ____ Players: " + numberOfActivePlayers +
            "/" + lobby.getNumberOfPlayers());
        details.getStyleClass().add("title3");
        Button join = new Button("Join");
        join.setPrefSize(70, 30);
        join.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        join.setOnMouseClicked(mouseEvent -> {
            Main.getPlayerConnection().joinLobby(lobby.getID());
        });
        hBox.getChildren().addAll(details, join);
        return hBox;
    }

    public static ImageView createBackButton() {
        javafx.scene.image.ImageView backButton = new javafx.scene.image.ImageView(
            new Image(JoinLobbiesMenu.class.getResource("/images/Messenger/back.png").toExternalForm()));
        backButton.setFitHeight(20);
        backButton.setFitWidth(20);
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        backButton.setOnMouseClicked(mouseEvent -> Main.setScene(new PreGameMenu().getPane()));
        return backButton;
    }
}
