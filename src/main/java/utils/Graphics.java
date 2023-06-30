package utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import client.view.Main;
import client.view.enums.ProfileMenuMessages;

public class Graphics {
    public static Background getBackground(URL url) {
        return new Background(new BackgroundFill(
            new ImagePattern(new Image(Objects.requireNonNull(url.toExternalForm()))),
            null,
            null)
        );
    }

    public static void showMessagePopup(String message) {
        Popup popup = new Popup();
        popup.setHeight(100);
        popup.setWidth(100);
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-background-color: white");
        messageLabel.setFont(new Font("Arial", 30));
        messageLabel.setText(message);
        popup.getContent().add(messageLabel);
        popup.setAutoHide(true);
        popup.show(Main.getStage());
    }

    public static ImageView getAvatarWithPath(String path) {
        return new ImageView(new Image(path, 160, 160, false, false));
    }

    public static ImageView[] getDefaultAvatars() {
        ImageView[] defaultAvatars = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            defaultAvatars[i] = getAvatarWithPath(Graphics.class.getResource("/images/avatars/" + (i + 1) + ".png").toExternalForm());
        }
        return defaultAvatars;
    }

    public static Captcha generateCaptcha(double layoutX, double layoutY) throws IOException {
        File randomCaptcha = Randoms.getRandomFileFromDirectory(Objects.requireNonNull(Graphics.class.getResource("/images/captcha/")));
        Captcha captcha = new Captcha(randomCaptcha.getName().substring(0, 4), layoutX, layoutY);
        return captcha;
    }

    public static void forceTextFieldsAcceptNumbersOnly(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                return;
            }
            if (!Utils.isInteger(newValue) || newValue.length() > maxLength) {
                textField.setText(oldValue);
            }
        });
    }
}
