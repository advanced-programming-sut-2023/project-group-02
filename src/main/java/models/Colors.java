package models;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Colors {
    NONE("\u001B[0m", "\u001B[0m", null),
    BLUE("\u001B[34m", "\u001B[44m", Color.BLUE),
    RED("\u001B[31m", "\u001B[41m", Color.RED),
    YELLOW("\u001B[33m", "\u001B[43m", Color.YELLOW),
    GREEN("\u001B[32m", "\u001B[42m", Color.GREEN),
    BLACK("\u001B[30m", "\u001B[40m", Color.BLACK),
    WHITE("\u001B[47m", "\u001B[47m", Color.WHITE),
    PURPLE("\u001B[35m", "\u001B[45m", Color.PURPLE),
    CYAN("\u001B[36m", "\u001B[46m", Color.CYAN);

    private final String fontColor;
    private final String backGround;

    private final Paint paint;

    Colors(String fontColor, String backGround, Color color) {
        this.fontColor = fontColor;
        this.backGround = backGround;
        this.paint = color;
    }

    public Paint getPaint() {
        return paint;
    }

    private String getFontColor() {
        return fontColor;
    }

    private String getBackGround() {
        return backGround;
    }

    private final static Colors[] allColors = {BLUE, RED, YELLOW, GREEN, BLACK, WHITE, PURPLE, CYAN};

    public static String getFontColorCode(Colors myColor) {
        for (Colors color : allColors) {
            if (color.equals(myColor))
                return color.getFontColor();
        }
        return null;
    }

    public static String getBackGroundColorCode(Colors myColor) {
        for (Colors color : allColors) {
            if (color.equals(myColor))
                return color.getBackGround();
        }
        return null;
    }
}
