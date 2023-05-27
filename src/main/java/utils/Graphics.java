package utils;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.ImagePattern;

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
