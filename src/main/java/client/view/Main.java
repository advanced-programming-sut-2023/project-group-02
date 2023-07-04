package client.view;

import client.PlayerConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Graphics;
import utils.Utils;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import client.view.MainMenu;
import client.view.SignupMenu;
import controllers.UserController;
import controllers.database.Database;

public class Main extends Application {
    private static Stage stage;
    private static PlayerConnection playerConnection;

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) throws IOException {
        playerConnection = new PlayerConnection("localhost", 8080);
        try {
            Database.init();
        } catch (IOException e) {
        }

        try {
            Graphics.generateCaptcha(10, 20);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserController.loadUsersFromFile();
//        UserController.loadCurrentUserFromFile();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        playerConnection.tryToAuthenticate();
        Main.stage = stage;
//        if (UserController.isAuthorized()) {
//            setScene(new MainMenu().getPane());
//        } else {
        //setScene(Main.getTitlePane());
//        }
//        Main.getPlayerConnection().requestLogin("danial", "Danial01*");
        setScene(new MainMenu().getPane());
        //TODO remove this
    }

    public static void setScene(Pane pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pane.requestFocus();
        stage.show();
    }

    public static Pane getTitlePane() {
        Pane pane = new Pane();
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(MainMenu.class.getResource(
            "/images/backgrounds/signup-menu.jpg"))));
        pane.getStylesheets()
            .add(Objects.requireNonNull(MainMenu.class.getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        initTitlePane(pane);
        return pane;
    }

    private static void initTitlePane(Pane pane) {
        Text title = new Text("Stronghold Crusader");
        title.getStyleClass().add("text-title");
        title.setLayoutX(100);
        title.setLayoutY(100);
        Button signup = new Button("Sign up");
        signup.getStyleClass().add("button2");
        signup.setLayoutX(100);
        signup.setLayoutY(200);
        Button login = new Button("Login");
        login.getStyleClass().add("button2");
        login.setLayoutX(100);
        login.setLayoutY(250);
        Button exit = new Button("Exit");
        exit.getStyleClass().add("button2");
        exit.setLayoutX(100);
        exit.setLayoutY(300);
        signup.setOnAction(event -> {
            setScene(new SignupMenu().getPane());
        });
        login.setOnAction(event -> {
            setScene(new LoginMenu().getPane());
        });
        exit.setOnAction(event -> {
            System.exit(0);
        });
        pane.getChildren().addAll(title, signup, login, exit);
    }

    public static PlayerConnection getPlayerConnection() {
        return playerConnection;
    }
}
