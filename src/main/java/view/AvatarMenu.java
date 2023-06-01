package view;

import controllers.UserController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.User;
import utils.Graphics;

import java.io.File;
import java.util.Objects;

public class AvatarMenu {
    public Pane getPane() {
        Pane avatarMenuPane = new Pane();
        initializePane(avatarMenuPane);
        return avatarMenuPane;
    }

    private void initializePane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/avatar-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);

        ImageView currentAvatar = UserController.getCurrentUser().getAvatar();

        HBox hBoxOfDefaultAvatars = makeHBoxOfDefaultAvatars();

        Button chooseFileFromPC = makeFileChooser();

        VBox leftVbox = new VBox(makeText("Your Current Avatar"),currentAvatar,
            makeText("Choose a default avatar"),hBoxOfDefaultAvatars,
            makeText("Or choose from your PC"),chooseFileFromPC,
            makeBackButton());
        leftVbox.setSpacing(20);
        leftVbox.setTranslateX(10);
        leftVbox.setTranslateY(5);

        VBox rightVbox = new VBox(makeText("You can choose other players' avatars!"));
        makeListOfPlayers(rightVbox);

        HBox generalHBox = new HBox(leftVbox,rightVbox);
        generalHBox.setSpacing(10);
        pane.getChildren().add(generalHBox);
    }

    private HBox makeHBoxOfDefaultAvatars() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        for (ImageView defaultAvatar : Graphics.getDefaultAvatars()) {
            hBox.getChildren().add(defaultAvatar);
            defaultAvatar.setFitWidth(100);
            defaultAvatar.setFitHeight(100);
            defaultAvatar.setOnMouseClicked(event -> {
                UserController.changeAvatar(UserController.getCurrentUser(),defaultAvatar.getImage().getUrl());
                Main.setScene(getPane());
            });
        }
        return hBox;
    }

    private Button makeFileChooser() {
        Button button = new Button("Choose File");
        button.getStyleClass().add("button1");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));

        button.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(Main.getStage());
            if (selectedFile != null) {
                String imagePath = selectedFile.toURI().toString();
                UserController.changeAvatar(UserController.getCurrentUser(),imagePath);
                Main.setScene(getPane());
            }
        });
        return button;
    }

    private Text makeText(String content) {
        Text text = new Text(content);
        text.setFont(new Font("Arial",20));
        text.setFill(Color.BLUE);
        return text;
    }

    private Button makeBackButton() {
        Button button = new Button("Back");
        button.getStyleClass().add("button1");
        button.setOnAction(event -> Main.setScene(new ProfileMenu().getPane()));
        return button;
    }

    private void makeListOfPlayers(VBox rightVbox) {
        for (User user : UserController.getUsers()) {
            if (user.equals(UserController.getCurrentUser())) continue;
            ImageView avatar = user.getAvatar();
            avatar.setFitHeight(50);
            avatar.setFitWidth(50);
            Text name = new Text(user.getUsername());
            name.setFont(new Font("Arial",20));
            name.setFill(Color.WHITE);
            HBox playerHBox = new HBox(avatar,name);
            playerHBox.setSpacing(5);
            rightVbox.getChildren().add(playerHBox);
            playerHBox.setOnMouseClicked(event -> {
                UserController.changeAvatar(UserController.getCurrentUser(), user.getAvatarPath());
                Main.setScene(getPane());
            });
        }
    }
}
