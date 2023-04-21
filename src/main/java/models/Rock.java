package models;

public class Rock extends MapObject {
    private final Directions direction;

    public Rock(Directions direction) {
        this.direction = direction;
        // TODO changing the texture of the cell using a function which finds the cell
        // with x and y
    }

    public Directions getDirection() {
        return direction;
    }
}
