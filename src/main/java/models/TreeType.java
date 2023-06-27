package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public enum TreeType {
    DESERT_SHRUB("desert shrub", "shrub.png"),
    CHERRY_PALM("cherry palm", "cherry_palm.png"),
    OLIVE_TREE("olive tree", "olive.png"),
    COCONUT_PALM("coconut palm", "coconut_palm.png"),
    DATES_PALM("dates palm", "dates_palm.png");

    private final String treeName;
    private final String path;

    TreeType(String treeName, String path) {
        this.treeName = treeName;
        this.path = path;
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

    public Paint getPaint() {
        return new ImagePattern(new Image(getClass().getResource("/images/plants/" + path).toExternalForm()));
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/plants/" + path).toExternalForm()));
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        return imageView;
    }

    public static ArrayList<String> getTreeNamesList() {
        ArrayList<String> treeNames = new ArrayList<>();
        for (TreeType treeType : TreeType.values()) {
            treeNames.add(treeType.getTreeName());
        }
        return treeNames;
    }

    public String getTreeNameForPath() {
        if (treeName.equals("desert shrub"))
            return "shrub";
        else if (treeName.equals("olive tree"))
            return "olive";
        return treeName.replaceAll(" ", "_");
    }
}
