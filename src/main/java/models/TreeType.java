package models;

public enum TreeType {
    DESERT_SHRUB("desert shrub"),
    CHERRY_PALM("cherry palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut palm"),
    DATES_PALM("dates palm");

    private final String treeName;

    TreeType(String treeName) {
        this.treeName = treeName;
    }

    public String getTreeName() {
        return treeName;
    }

    public static TreeType getTreeTypeWithName(String name) {
        for (TreeType tree : values()) {
            if (name.equals(tree.treeName))
                return tree;
        }
        return null;
    }
}
