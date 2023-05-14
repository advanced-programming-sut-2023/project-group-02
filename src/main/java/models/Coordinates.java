package models;

public class Coordinates {
    public final int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Coordinates))
            return false;
        Coordinates other = (Coordinates) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 1; // this is totally legal and safe
    }

    public Coordinates getNeighbour(Directions direction) {
        return switch (direction) {
            case NORTH -> new Coordinates(x, y - 1);
            case EAST -> new Coordinates(x + 1, y);
            case SOUTH -> new Coordinates(x, y + 1);
            case WEST -> new Coordinates(x - 1, y);
        };
    }

    public Cell getCellWithCoordinates(Map map) {
        return map.findCellWithXAndY(x, y);
    }
}
