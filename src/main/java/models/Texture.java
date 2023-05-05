package models;

import java.util.HashMap;

public enum Texture {
    EARTH("earth",Colors.BLACK),
    EARTH_WITH_GRAVEL("earth with gravel",Colors.PURPLE),
    SLATE("slate",Colors.CYAN),
    ROCK("rock",Colors.BLUE),
    IRON("iron",Colors.RED),
    GRASS("grass",Colors.GREEN),
    MEADOW("meadow",Colors.YELLOW),
    DENSE_MEADOW("dense meadow",Colors.YELLOW);

    private final String name;
    private final Colors color;

    private final static Texture[] allTextures = {EARTH,EARTH_WITH_GRAVEL,SLATE,ROCK,IRON,GRASS,MEADOW,DENSE_MEADOW};

    private HashMap<Texture, Colors> texturesColor = initialize();

    private HashMap<Texture, Colors> initialize() {
        HashMap<Texture, Colors> texturesColor = new HashMap<>();
        texturesColor.put(EARTH,Colors.BLACK);
        texturesColor.put(EARTH_WITH_GRAVEL,Colors.PURPLE);
        texturesColor.put(SLATE,Colors.CYAN);
        texturesColor.put(ROCK,Colors.BLUE);
        texturesColor.put(IRON,Colors.RED);
        texturesColor.put(GRASS,Colors.GREEN);
        texturesColor.put(MEADOW,Colors.YELLOW);
        texturesColor.put(DENSE_MEADOW,Colors.YELLOW);
        return texturesColor;
    }

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
