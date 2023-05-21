package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.MainMenu;
import view.SignupMenu;

import java.io.IOException;
import java.util.Scanner;

import controllers.UserController;
import controllers.database.Database;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        try {
            Database.init();
        } catch (IOException e) {
        }

        UserController.loadUsersFromFile();
        UserController.loadCurrentUserFromFile();

        Scanner scanner = new Scanner(System.in);
        if (UserController.isAuthorized()) {
            new MainMenu().run(scanner);
        } else {
            new SignupMenu().run(scanner);
        }
        scanner.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // stage.show();
    }
}

/*
 * samples
 * user create -u danial -p Danial01* Danial01* --email danielparnian@gmail.com -n Dani -s random
 * question pick -q 2 -a jack -c jack
 * enter login menu
 * user login -u danial -p Danial01*
 */
