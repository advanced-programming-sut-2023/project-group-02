package models;

public class Rock extends MapObject {
    private final Directions direction;

    public Rock(Directions direction) {
        this.direction = direction;
        this.name = "rock";
        this.owner = null;
    }

    public Directions getDirection() {
        return direction;
    }
}
