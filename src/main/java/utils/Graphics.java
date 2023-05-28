package utils;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;

public class Graphics {
    public static Background getBackground(URL url) {
        return new Background(new BackgroundFill(
            new ImagePattern(new Image(Objects.requireNonNull(url.toExternalForm()))),
            null,
            null)
        );
    }
}
