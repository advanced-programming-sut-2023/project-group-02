package models;

public enum TreeType {
    DESERT_SHRUB,
    CHERRY_PALM,
    OLIVE_TREE,
    COCONUT_PALM,
    DATES_PALM;

    public static TreeType getTreeTypeWithName(String name) {
        if (name.equals("desert shrub")) return DESERT_SHRUB;
        if (name.equals("cherry palm")) return CHERRY_PALM;
        if (name.equals("olive tree")) return OLIVE_TREE;
        if (name.equals("coconut palm")) return COCONUT_PALM;
        if (name.equals("dates palm")) return DATES_PALM;
        return null;
    }
}
