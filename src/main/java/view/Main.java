package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.MainMenu;
import view.SignupMenu;

import java.io.IOException;
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
            setScene(new SignupMenu().getPane());
        }
    }

    public void setScene(Pane pane) {
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
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
