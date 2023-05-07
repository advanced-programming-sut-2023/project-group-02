package models;

import java.util.HashMap;

public enum Texture {
    EARTH("earth",Colors.RED),
    EARTH_WITH_GRAVEL("earth with gravel",Colors.CYAN),
    SLATE("slate",Colors.BLACK),
    ROCK("rock",Colors.BLACK),
    IRON("iron",Colors.PURPLE),
    GRASS("grass",Colors.GREEN),
    MEADOW("meadow",Colors.YELLOW),
    DENSE_MEADOW("dense meadow",Colors.YELLOW);

    private final String name;
    private final Colors color;

    private final static Texture[] allTextures = {EARTH,EARTH_WITH_GRAVEL,SLATE,ROCK,IRON,GRASS,MEADOW,DENSE_MEADOW};

    Texture(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Colors getColor() {
        return color;
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
