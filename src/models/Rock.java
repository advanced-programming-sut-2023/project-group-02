package models;

public class Rock {
    public enum Direction {
        NORTH,
        EAST,
        WEST,
        SOUTH,
        RANDOM,
    }

    private final Direction direction;
    private final int x;
    private final int y;

    public Rock(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        //TODO changing the texture of the cell using a function which finds the cell with x and y
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
