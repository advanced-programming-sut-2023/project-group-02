package models;

import controllers.GameMenuController;

public class Rock extends MapObject {
    private final Directions direction;

    public Rock(int x, int y, Directions direction) {
        super(x,y,null);
        this.direction = direction;
        // TODO changing the texture of the cell using a function which finds the cell
        // with x and y
    }

    public Directions getDirection() {
        return direction;
    }
}
