package models;

import java.util.ArrayList;
import java.util.List;

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

    private final static ArrayList<TreeType> allTrees = new ArrayList<>(
        List.of(DESERT_SHRUB,CHERRY_PALM,COCONUT_PALM,OLIVE_TREE,DATES_PALM)
    );

    public static TreeType getTreeTypeWithName(String name) {
        for (TreeType tree : allTrees) {
            if (name.equals(tree.treeName))
                return tree;
        }
        return null;
    }
}
