package models;

public class Tree extends MapObject {
    private final TreeType type;

    public Tree(int x, int y,TreeType type) {
        super(x,y,null);
        this.type = type;
    }

    public TreeType getType() {
        return type;
    }
}
