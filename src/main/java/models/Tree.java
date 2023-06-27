package models;

public class Tree extends MapObject {
    private final TreeType type;

    public Tree(TreeType type) {
        this.type = type;
        this.name = type.getTreeName();
        this.owner = null;
        this.imagePath = "/images/plants/" + type.getTreeNameForPath() + ".png";
    }

    public TreeType getType() {
        return type;
    }
}
