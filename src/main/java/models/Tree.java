package models;

public class Tree extends MapObject {
    private final TreeType type;

    public Tree(TreeType type) {
        this.type = type;
    }

    public TreeType getType() {
        return type;
    }
}
