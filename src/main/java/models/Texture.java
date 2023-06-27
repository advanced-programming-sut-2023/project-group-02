package models;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public enum Texture {
    EARTH("earth", Colors.RED, "tiles/Tiles/Plain1.jpg"),
    EARTH_WITH_GRAVEL("earth with gravel", Colors.CYAN, "tiles/Tiles/Plain3.jpg"),
    SLATE("slate", Colors.BLACK, "tiles/slate.jpg"),
    ROCK("rock", Colors.BLACK, "tiles/rocky.jpg"),
    IRON("iron", Colors.PURPLE, "tiles/iron.jpg"),
    GRASS("grass", Colors.GREEN, "tiles/tile/grass_tile.jpg"),
    MEADOW("meadow", Colors.YELLOW, "tiles/meadow.jpg"),
    DENSE_MEADOW("dense meadow", Colors.YELLOW, "tiles/dense_meadow.jpg"),
    SEA("sea", Colors.BLUE, "tiles/tile/sea_tile.jpg"),
    BEACH("beach", Colors.YELLOW, "tiles/beach.jpg"), //units cant pass
    OIL("oil", Colors.RED, "tiles/oil.jpg"),
    SHALLOW_WATER("shallow water", Colors.BLUE, "tiles/tile/gulf_tile.jpg"), // units can pass
    PLAIN("plain", Colors.GREEN, "tiles/plain.jpg"), //kills units
    ;

    private final String name;
    private final Colors color;
    private final String path;

    private final static Texture[] allTextures = {EARTH, EARTH_WITH_GRAVEL, SLATE, ROCK, IRON, GRASS, MEADOW, DENSE_MEADOW
        , SEA, BEACH, OIL, SHALLOW_WATER, PLAIN};

    Texture(String name, Colors color, String path) {
        this.name = name;
        this.color = color;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public Colors getColor() {
        return color;
    }

    public String getPath() {
        return path;
    }

    public Paint getPaint() {
        if (path == null) {
            return Color.RED; // TODO
        }
        return new ImagePattern(new Image(getClass().getResource("/images/" + path).toExternalForm()));
    }

    public static Texture findTextureWithName(String name) {
        for (Texture texture : allTextures) {
            if (name.equals(texture.getName())) return texture;
        }
        return null;
    }

    public String getFontColorCodeWithTexture() {
        return Colors.getFontColorCode(color);
    }

    public String getBackGroundColorCodeWithTexture() {
        return Colors.getBackGroundColorCode(color);
    }
}
