package view.pregame;

import controllers.SignUpMenuController;
import controllers.UserController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import models.User;
import utils.Graphics;
import utils.Utils;
import view.GameMenu;
import view.Main;
import view.enums.SignUpMenuMessages;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Objects;

public class InitGameMenu {
    private final Text numberOfTurnsText = new Text("Number of turns: ");
    private final TextField numberField = new TextField();
    private final Text mapWidthText = new Text("Map width: ");
    private final TextField mapWidthField = new TextField();
    private final Text mapHeightText = new Text("Map height: ");
    private final TextField mapHeightField = new TextField();
    private final TextField numberOfPlayersField = new TextField();
    private final Text usernames = new Text(600, 120, "Username of Players");
    private final TextField[] playersUsernamesFields = new TextField[10];
    private final ArrayList<User> players = new ArrayList<>();

    public Pane getPane() {
        Pane initGamePane = new Pane();
        initPane(initGamePane);
        return initGamePane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        initNumberOfTurnsFields(pane);
        initMapSizeFields(pane);
        initNumberOfPlayersField(pane);
        addConfirmButton(pane);
        addBackButton(pane);
    }

    private void initNumberOfPlayersField(Pane pane) {
        usernames.setVisible(false);
        for (int i = 0; i < playersUsernamesFields.length; i++) {
            TextField playersUsernameField = new TextField();
            playersUsernameField.setLayoutX(600);
            playersUsernameField.setLayoutY(150 + 40 * i);
            playersUsernameField.setVisible(false);
            playersUsernameField.setMaxWidth(160);
            playersUsernamesFields[i] = playersUsernameField;
            pane.getChildren().add(playersUsernamesFields[i]);
        }

        Text numberOfPlayersText = new Text(100, 240, "Number of players: ");
        numberOfPlayersText.getStyleClass().add("title2");
        numberOfPlayersField.setLayoutX(320);
        numberOfPlayersField.setLayoutY(220);
        numberOfPlayersField.setMaxWidth(45);
        numberOfPlayersField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                initPlayersFields(pane, 0);
                return;
            }
            if (!Utils.isInteger(newValue) || newValue.length() > 1 || Integer.parseInt(newValue) > 8) {
                numberOfPlayersField.setText(oldValue);
                return;
            }
            initPlayersFields(pane, Integer.parseInt(newValue));
        });
        pane.getChildren().addAll(usernames, numberOfPlayersText, numberOfPlayersField);
    }

    private void initPlayersFields(Pane pane, int numberOfFields) {
        usernames.getStyleClass().add("title2");
        if (numberOfFields == 0) {
            usernames.setVisible(false);
            for (int i = 0; i < 10; i++) {
                playersUsernamesFields[i].setVisible(false);
            }
            return;
        }
        usernames.setVisible(true);
        for (int i = 0; i < numberOfFields; i++) {
            playersUsernamesFields[i].setVisible(true);
        }
    }

    private void initNumberOfTurnsFields(Pane pane) {
        numberOfTurnsText.setLayoutX(100);
        numberOfTurnsText.setLayoutY(120);
        numberOfTurnsText.getStyleClass().add("title2");
        numberField.setLayoutX(320);
        numberField.setLayoutY(100);
        numberField.setMaxWidth(45);
        Graphics.forceTextFieldsAcceptNumbersOnly(numberField, 3);
        pane.getChildren().addAll(numberOfTurnsText, numberField);
    }

    private void initMapSizeFields(Pane pane) {
        mapWidthText.setLayoutX(100);
        mapWidthText.setLayoutY(160);
        mapWidthText.getStyleClass().add("title2");
        mapHeightText.setLayoutX(100);
        mapHeightText.setLayoutY(200);
        mapHeightText.getStyleClass().add("title2");

        mapWidthField.setLayoutX(320);
        mapWidthField.setLayoutY(140);
        mapWidthField.setMaxWidth(45);
        mapHeightField.setLayoutX(320);
        mapHeightField.setLayoutY(180);
        mapHeightField.setMaxWidth(45);
        Graphics.forceTextFieldsAcceptNumbersOnly(mapWidthField, 3);
        Graphics.forceTextFieldsAcceptNumbersOnly(mapHeightField, 3);

        pane.getChildren().addAll(mapWidthText, mapWidthField, mapHeightText, mapHeightField);
    }

    private void addConfirmButton(Pane pane) {
        Text confirm = new Text("Confirm");
        confirm.setLayoutX(240);
        confirm.setLayoutY(350);
        confirm.getStyleClass().add("title2-with-hover");
        confirm.setOnMouseClicked(event -> {
            //TODO complete this
            if (numberField.getText().isEmpty() || mapWidthField.getText().isEmpty() || mapHeightField.getText().isEmpty() || numberOfPlayersField.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Some fields are empty").showAndWait();
                return;
            }
            if (!checkAndAddAllUsernames()) return;

            Main.setScene(new GameMenu().getPane());
            Main.getStage().setFullScreen(true);
        });
        pane.getChildren().add(confirm);
    }

    private boolean checkAndAddAllUsernames() {
        players.clear();
        for (int i = 0; i < Integer.parseInt(numberOfPlayersField.getText()); i++) {
            String username = playersUsernamesFields[i].getText();
            if (username.isEmpty() || !UserController.userWithUsernameExists(username)) {
                new Alert(Alert.AlertType.ERROR, "Some usernames are not valid").showAndWait();
                return false;
            }
            for (User player2 : players) {
                if (player2.getUsername().equals(username)) {
                    new Alert(Alert.AlertType.ERROR, "Some usernames are repeated more than once").showAndWait();
                    return false;
                }
            }
            players.add(UserController.findUserWithUsername(username));
        }
        return true;
    }

    private void addBackButton(Pane pane) {
        Text back = new Text("Back");
        back.setLayoutX(100);
        back.setLayoutY(350);
        back.getStyleClass().add("title2-with-hover");
        back.setOnMouseClicked(event -> {
            Main.setScene(new PreGameMenu().getPane());
        });
        pane.getChildren().add(back);
    }
}
