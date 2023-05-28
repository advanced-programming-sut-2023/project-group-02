package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Graphics;
import utils.Utils;
import view.MainMenu;
import view.SignupMenu;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import controllers.UserController;
import controllers.database.Database;

public class Main extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        try {
            Database.init();
        } catch (IOException e) {
        }

        UserController.loadUsersFromFile();
        UserController.loadCurrentUserFromFile();

        launch(args);

//        Scanner scanner = new Scanner(System.in);
//        if (UserController.isAuthorized()) {
//            new MainMenu().run(scanner);
//        } else {
//            new SignupMenu().run(scanner);
//        }
//        scanner.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        if (UserController.isAuthorized()) {
            setScene(new MainMenu().getPane());
        } else {
            setScene(Main.getTitlePane());
        }
    }

    public static void setScene(Pane pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private static Pane getTitlePane() {
        Pane pane = new Pane();
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(MainMenu.class.getResource(
            "/images/backgrounds/signup-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(MainMenu.class.getResource("/CSS/Menus.css")).toExternalForm());
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
}

/*
 * samples
 * user create -u danial -p Danial01* Danial01* --email danielparnian@gmail.com -n Dani -s random
 * question pick -q 2 -a jack -c jack
 * enter login menu
 * user login -u danial -p Danial01*
 *
 */
