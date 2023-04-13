package models;

public class Rock {
    private final Directions direction;
    private final int x;
    private final int y;

    public Rock(int x, int y, Directions direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        //TODO changing the texture of the cell using a function which finds the cell with x and y
    }

    public Directions getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
