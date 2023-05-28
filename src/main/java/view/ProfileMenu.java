package view;

import controllers.LoginMenuController;
import controllers.ProfileMenuController;
import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.Parser;
import view.enums.LoginMenuMessages;
import view.enums.ProfileMenuMessages;

import java.util.Objects;
import java.util.Scanner;

public class ProfileMenu {
    public Pane getPane() {
        Pane profileMenuPane = new Pane();
        initializePane(profileMenuPane);
        return profileMenuPane;
    }

    private void initializePane(Pane pane) {
        //TODO add a background
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/MainMenu.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        VBox buttons = new VBox();
        buttons.setTranslateX(400);
        buttons.setTranslateY(200);
        buttons.setSpacing(15);

        Button changeInformation = makeButton(buttons,"Change Information");
        changeInformation.setOnAction(event -> {
            //TODO
        });

        Button displayInformation = makeButton(buttons,"Display Information");
        displayInformation.setOnAction(event -> {
            //TODO
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
            if (parser.beginsWith("profile change slogan")) {
                changeSlogan(parser);
            } else if (parser.beginsWith("profile remove slogan")) {
                removeSlogan();
            } else if (parser.beginsWith("profile change password")) {
                changePassword(parser);
            } else if (parser.beginsWith("profile change")) {
                changeInfo(parser);
            } else if (parser.beginsWith("profile display highscore")) {
                showHighScore();
            } else if (parser.beginsWith("profile display rank")) {
                showRank();
            } else if (parser.beginsWith("profile display slogan")) {
                showSlogan();
            } else if (parser.beginsWith("profile display")) {
                showProfile();
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at ProfileMenu");
            } else if (parser.beginsWith("exit")) {
                System.out.println("You are at view.Main Menu");
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    void removeSlogan() {
        ProfileMenuMessages message = ProfileMenuController.removeSlogan();
        if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
            System.out.println("Slogan is removed successfully!");
        } else if (message.equals(ProfileMenuMessages.EMPTY_FIELD)) {
            System.out.println("Slogan field is already empty!");
        }
    }

    void changeSlogan(Parser parser) {
        ProfileMenuMessages message = ProfileMenuController.changeSlogan(parser.get("s"));

        if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
            System.out.println("Slogan is changed successfully!");
        } else {
            System.out.println("Invalid command!");
        }
    }

    void changeInfo(Parser parser) {
        ProfileMenuMessages message;
        if (parser.get("u") != null) {
            message = ProfileMenuController.changeUsername(parser.get("u"));
            if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
                System.out.println("Changing username was successful!");
            } else {
                System.out.println("Invalid username format!");
            }

        } else if (parser.get("n") != null) {
            message = ProfileMenuController.changeNickname(parser.get("n"));
            if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
                System.out.println("Changing nickname was successful!");
            } else {
                System.out.println("Invalid nickname!");
            }
        } else if (parser.get("e") != null) {
            message = ProfileMenuController.changeEmail(parser.get("e"));
            if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
                System.out.println("Email is changed successfully!");
            } else {
                System.out.println("Invalid email format!");
            }
        } else {
            System.out.println("Invalid command!");
        }
    }

    void showHighScore() {
        System.out.println("Highscore: " + UserController.getCurrentUser().getHighScore());
    }

    void showRank() {
        System.out.println("Rank: " + UserController.getUserRank(UserController.getCurrentUser()));
    }

    void showSlogan() {
        String slogan = UserController.getCurrentUser().getSlogan();
        if (slogan != null) {
            System.out.println("Slogan: " + slogan);
        } else {
            System.out.println("No slogan");
        }
    }

    void showProfile() {
        showRank();
        showHighScore();
        showSlogan();
    }

    void changePassword(Parser parser) {
        ProfileMenuMessages message = ProfileMenuController.changePassword(parser.get("o"), parser.get("n"));

        System.out.println(message);
//            case SAME_THING -> "Please enter a new password!";
//            case WEAK_NEW_PASSWORD -> "Your new pass is weak";
//            case INCORRECT_OLD_PASSWORD -> "Current password is incorrect!";
//            case SUCCESSFUL -> "Password is changed successfully";
//            default -> "";
//        });
    }
}
