package client.view.lobby;

import client.view.Main;
import client.view.pregame.PreGameMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.User;
import server.logic.Lobby;
import utils.Graphics;

import java.util.Objects;

public class LobbyMenu {
    private Lobby lobby;
    private Pane pane = new Pane();

    public LobbyMenu(Lobby lobby) {
        this.lobby = lobby;
        //update lobbies
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> {
            setLobby(Main.getPlayerConnection().getLobbyWithID(lobby.getID()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
        initPane(pane);
    }

    public Pane getPane() {
        pane = new Pane();
        initPane(pane);
        return pane;
    }

    private void initPane(Pane pane) {
        Pane profilePane = null;
        for (int i = pane.getChildren().size() - 1; i >= 0; i--) {
            if (pane.getChildren().get(i) instanceof Pane) {
                profilePane = (Pane) pane.getChildren().get(i);
                break;
            }
        }
        pane.getChildren().clear();
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/lobby-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        addPlayersVBox(pane);
        addExitButton(pane);
        addStartGame(pane);
        if (profilePane != null)
            pane.getChildren().add(profilePane);
    }

    private void addStartGame(Pane pane) {
        if (!Main.getPlayerConnection().getLoggedInUser().equals(lobby.getMembers().get(0)))
            return;
        Text start = new Text("Start Now");
        start.getStyleClass().add("title1-with-hover");
        start.setLayoutX(820);
        start.setLayoutY(75);
        start.setOnMouseClicked(mouseEvent -> {
            if (lobby.getMembers().size() == 1)
                Graphics.showMessagePopup("There should be at least 2 players to start!");
            //TODO start game otherwise :)
        });
        pane.getChildren().add(start);
    }

    private void addExitButton(Pane pane) {
        ImageView exit = JoinLobbiesMenu.createBackButton();
        exit.setOnMouseClicked(mouseEvent -> Main.setScene(new PreGameMenu().getPane()));
        Main.getPlayerConnection().quitLobby(lobby.getID());
        pane.getChildren().add(exit);
    }

    private void addPlayersVBox(Pane pane) {
        ImageView star = new ImageView(new Image(getClass().getResource("/images/others/star.png").toExternalForm()));
        star.setFitWidth(15);
        star.setFitHeight(15);
        star.setLayoutX(10);
        star.setLayoutY(50);
        VBox players = new VBox();
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
}
