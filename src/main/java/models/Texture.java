package models;

public enum Texture {
    EARTH("earth"),
    EARTH_WITH_GRAVEL("earth with gravel"),
    SLATE("slate"),
    ROCK("rock"),
    IRON("iron"),
    GRASS("grass"),
    MEADOW("meadow"),
    DENSE_MEADOW("dense meadow");

    private final String name;

    private final static Texture[] allTextures = {EARTH,EARTH_WITH_GRAVEL,SLATE,ROCK,IRON,GRASS,MEADOW,DENSE_MEADOW};

    Texture(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Texture findTextureWithName(String name) {
        for (Texture texture : allTextures) {
            if (name.equals(texture.getName())) return texture;
        }
        return null;
    }
}
