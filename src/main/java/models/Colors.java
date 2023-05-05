package models;

public enum Colors {
    BLUE("\u001B[34m","\u001B[44m"),
    RED("\u001B[31m","\u001B[41m"),
    YELLOW("\u001B[33m","\u001B[43m"),
    GREEN("\u001B[32m","\u001B[42m"),
    BLACK("\u001B[30m","\u001B[40m"),
    WHITE("\u001B[47m","\u001B[47m"),
    PURPLE("\u001B[35m","\u001B[45m"),
    CYAN("\u001B[36m","\u001B[46m");

    private final String fontColor;
    private final String backGround;

    Colors(String fontColor, String backGround) {
        this.fontColor = fontColor;
        this.backGround = backGround;
    }

    private String getFontColor() {
        return fontColor;
    }

    private String getBackGround() {
        return backGround;
    }

    private final static Colors[] allColors = {BLUE,RED,YELLOW,GREEN,BLACK,WHITE,PURPLE,CYAN};

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
