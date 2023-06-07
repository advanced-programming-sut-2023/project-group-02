package models;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public enum Texture {
    EARTH("earth", Colors.RED, "tiles/tile/desert_tile.jpg"),
    EARTH_WITH_GRAVEL("earth with gravel", Colors.CYAN, "tiles/Tile/Plain3.jpg"),
    SLATE("slate",Colors.BLACK),
    ROCK("rock",Colors.BLACK),
    IRON("iron",Colors.PURPLE),
    GRASS("grass", Colors.GREEN, "tiles/tile/grass_tile.jpg"),
    MEADOW("meadow",Colors.YELLOW),
    DENSE_MEADOW("dense meadow",Colors.YELLOW),
    SEA("sea", Colors.BLUE, "tiles/tile/sea_tile.jpg"),
    BEACH("beach",Colors.YELLOW), //units cant pass
    OIL("oil",Colors.RED),
    SHALLOW_WATER("shallow water", Colors.BLUE, "tiles/tile/gulf_tile.jpg"), // units can pass
    PLAIN("plain",Colors.GREEN), //kills units
    ;

    private final String name;
    private final Colors color;
    private final String path;

    private final static Texture[] allTextures = {EARTH,EARTH_WITH_GRAVEL,SLATE,ROCK,IRON,GRASS,MEADOW,DENSE_MEADOW
    ,SEA,BEACH,OIL,SHALLOW_WATER,PLAIN};

    Texture(String name, Colors color) {
        this(name, color, null);
    }

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
