package view;

import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.Graphics;
import utils.Parser;
import view.enums.MainMenuMessages;

import java.util.Objects;
import java.util.Scanner;

public class MainMenu {
    public Pane getPane() {
        Pane MainMenuPane = new Pane();
        initPane(MainMenuPane);
        return MainMenuPane;
    }

    private void initPane(Pane pane) {
        //TODO add a background
        //TODO add a stylesheet
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        VBox buttons = new VBox();
        buttons.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        buttons.setSpacing(15);
        buttons.setTranslateX(400);
        buttons.setTranslateY(100);
        Button enterProfileMenuButton = makeButton(buttons,"Enter Profile Menu");
        enterProfileMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO enter profile menu in a clean way
            }
        });
        Button enterGameMenu = makeButton(buttons,"Enter Game Menu");
        enterGameMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO enter game menu in a clean way
            }
        });
        Button logout = makeButton(buttons,"logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainMenuMessages output = logout();
                //TODO going to sign up menu in a clean way
            }
        });

        pane.getChildren().add(buttons);
    }

    private Button makeButton(VBox buttons, String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button1");
        buttons.getChildren().add(button);
        return button;
    }

    public void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("enter profile menu")) {
                System.out.println("You successfully entered ProfileMenu!");
                new ProfileMenu().run(scanner);
            } else if (parser.beginsWith("enter game menu")) {
                System.out.println("You successfully entered GameMenu!");
                new GameMenu().run(scanner);
            } else if (parser.beginsWith("user logout")) {
                MainMenuMessages output = logout();
                System.out.println(output.getMessage());
                if (output == MainMenuMessages.LOGOUT_SUCCESSFUL) {
                    new SignupMenu().run(scanner);
                }
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at MainMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static MainMenuMessages logout() {
        if (UserController.getCurrentUser() == null)
            return MainMenuMessages.ALREADY_LOGGED_OUT;
        UserController.logout();
        return MainMenuMessages.LOGOUT_SUCCESSFUL;
    }
}
