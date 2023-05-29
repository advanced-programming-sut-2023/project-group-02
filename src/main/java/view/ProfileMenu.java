package view;

import controllers.LoginMenuController;
import controllers.ProfileMenuController;
import controllers.UserController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Pair;
import utils.Graphics;
import utils.Parser;
import utils.Validation;
import view.enums.LoginMenuMessages;
import view.enums.ProfileMenuMessages;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class ProfileMenu {
    public Pane getPane() {
        Pane profileMenuPane = new Pane();
        initializePane(profileMenuPane);
        return profileMenuPane;
    }

    private void initializePane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.png"))));
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

        Button changeUsernameButton = makeButton(buttons, "Change Username");
        changeUsernameButton.setOnAction(event -> {
            GridPane gridPane = makeGridPane();
            Dialog<String> dialog = makeDialog();
            dialog.setWidth(400);
            dialog.setResultConverter(dialogButton -> getResultOfDialog(dialog, dialogButton));
            addTextFieldToDialog2(dialog, "Username", gridPane);
            dialog.getDialogPane().setContent(gridPane);
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> {
                ProfileMenuMessages message = ProfileMenuController.changeUsername(value);
                showMessagePopup(message, "Changing Username was successful!", "Invalid Username!");
            });
            info.setText(showProfile());
        });

        Button changePasswordButton = makeButton(buttons, "Change Password");
        changePasswordButton.setOnAction(event -> {
            GridPane gridPane = makeGridPane();

        });

        Button changeNickNameButton = makeButton(buttons, "Change Nickname");
        changeNickNameButton.setOnAction(event -> {
            GridPane gridPane = makeGridPane();
            Dialog<String> dialog = makeDialog();
            dialog.setResultConverter(dialogButton -> getResultOfDialog(dialog, dialogButton));
            addTextFieldToDialog1(dialog, "Slogan", gridPane);
            dialog.getDialogPane().setContent(gridPane);
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> {
                ProfileMenuMessages message = ProfileMenuController.changeNickname(value);
                showMessagePopup(message, "Changing nickname was successful!", "Invalid Nickname!");
            });
            info.setText(showProfile());
        });

        Button changeAvatarButton = makeButton(buttons, "Change Avatar");
        changeAvatarButton.setOnAction(event -> {

        });

        Button changeSloganButton = makeButton(buttons, "Change Slogan");
        changeSloganButton.setOnAction(event -> {
            GridPane gridPane = makeGridPane();
            Dialog<String> dialog = makeDialog();
            addTextFieldToDialog1(dialog, "Slogan", gridPane);
            dialog.getDialogPane().setContent(gridPane);
            dialog.setResultConverter(dialogButton -> getResultOfDialog(dialog, dialogButton));
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> {
                ProfileMenuMessages message = ProfileMenuController.changeSlogan(value);
                showMessagePopup(message, "Changing slogan was successful!", "Invalid Slogan!");
            });
            info.setText(showProfile());
        });

        Button removeSloganButton = makeButton(buttons, "Remove Slogan");
        removeSloganButton.setOnAction(event -> {
            ProfileMenuMessages message = ProfileMenuController.removeSlogan();
            showMessagePopup(message, "Removing slogan was successful!", "You don't have a Slogan!");
            info.setText(showProfile());
        });

        Button back = makeButton(buttons, "Back");
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

    private GridPane makeGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        return gridPane;
    }

    private Dialog<String> makeDialog() {
        Dialog<String> dialog = new Dialog<>();
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        return dialog;
    }

    private void addTextFieldToDialog1(Dialog<String> dialog, String changingThing, GridPane gridPane) {
        dialog.setTitle("Change " + changingThing);
        TextField textField = new TextField();
        textField.setPromptText("New " + changingThing);
        gridPane.add(textField, 0, 0);
        Platform.runLater(textField::requestFocus);
    }

    private void addTextFieldToDialog2(Dialog<String> dialog, String changingThing, GridPane gridPane) {
        dialog.setTitle("Change " + changingThing);
        TextField textField = new TextField();
        Text errorText = new Text();
        textField.setPromptText("New " + changingThing);
        gridPane.add(textField, 0, 0);
        gridPane.add(errorText, 0, 1);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Validation.isValidUsername(newValue) || newValue.equals("")) errorText.setText("");
            else errorText.setText("forbidden character is used.");
        });
    }

    private String getResultOfDialog(Dialog<String> dialog, ButtonType dialogButton) {
        if (dialogButton == dialog.getDialogPane().getButtonTypes().get(0)) {
            if (dialog.getDialogPane().getContent() instanceof Pane &&
                ((Pane) dialog.getDialogPane().getContent()).getChildren().get(0) instanceof TextField)
                return ((TextField) ((Pane) dialog.getDialogPane().getContent()).getChildren().get(0)).getText();
        }
        return null;
    }

    private void showMessagePopup(ProfileMenuMessages message, String firstMessage, String secondMessage) {
        Popup popup = new Popup();
        popup.setHeight(100);
        popup.setWidth(100);
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-background-color: white");
        messageLabel.setFont(new Font("Arial", 30));
        if (message.equals(ProfileMenuMessages.SUCCESSFUL)) {
            messageLabel.setText(firstMessage);
        } else {
            messageLabel.setText(secondMessage);
        }
        popup.getContent().add(messageLabel);
        popup.setAutoHide(true);
        popup.show(Main.getStage());
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
        if (slogan != null && !slogan.isEmpty()) {
            return "Slogan: " + slogan;
        } else {
            return "No slogan";
        }
    }

    String showProfile() {
        //TODO players avatar
        return UserController.getCurrentUser().getUsername() + "\n" + UserController.getCurrentUser().getNickname() + "\n" +
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
