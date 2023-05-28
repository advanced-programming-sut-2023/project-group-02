package view;

import controllers.LoginMenuController;
import controllers.ProfileMenuController;
import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
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
        buttons.setTranslateY(10);
        buttons.setSpacing(15);

        Text info = new Text(showProfile());
        info.getStyleClass().add("text-content2");
        buttons.getChildren().add(info);

        Button changeUsernameButton = makeButton(buttons,"Change Username");
        changeUsernameButton.setOnAction(event -> {
            Popup changeUsernamePopup = new Popup();
        });

        Button changePasswordButton = makeButton(buttons,"Change Password");
        changePasswordButton.setOnAction(event -> {

        });

        Button changeNickNameButton = makeButton(buttons,"Change Nickname");
        changeNickNameButton.setOnAction(event -> {

        });

        Button changeAvatarButton = makeButton(buttons,"Change Avatar");
        changeAvatarButton.setOnAction(event -> {

        });

        Button changeSloganButton = makeButton(buttons,"Change Slogan");
        changeSloganButton.setOnAction(event -> {

        });

        Button removeSloganButton = makeButton(buttons,"Remove Slogan");
        removeSloganButton.setOnAction(event -> {

        });

        Button back = makeButton(buttons,"Back");
        back.setOnAction(event -> {
            Main.getStage().setScene(new Scene(new MainMenu().getPane()));
        });

        pane.getChildren().add(buttons);
    }

    private Button makeButton(VBox buttons, String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button1");
        buttons.getChildren().add(button);
        return button;
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

    String showHighScore() {
        return "HighScore: " + UserController.getCurrentUser().getHighScore();
    }

    String showRank() {
        return "Rank: " + UserController.getUserRank(UserController.getCurrentUser());
    }

    String showSlogan() {
        String slogan = UserController.getCurrentUser().getSlogan();
        if (slogan != null) {
            return "Slogan: " + slogan;
        } else {
            return "No slogan";
        }
    }

    String showProfile() {
        //TODO players avatar
        return UserController.getCurrentUser().getUsername() + "\n" +
             showRank() + "\n" + showHighScore() + "\n" + showSlogan();
    }

    void changePassword(Parser parser) {
        ProfileMenuMessages message = ProfileMenuController.changePassword(parser.get("o"), parser.get("n"));

        System.out.println(
        switch (message) {
            case SAME_THING -> "Please enter a new password!";
            case WEAK_NEW_PASSWORD -> "Your new pass is weak";
            case INCORRECT_OLD_PASSWORD -> "Current password is incorrect!";
            case SUCCESSFUL -> "Password is changed successfully";
            default -> "";
        });
    }
}
