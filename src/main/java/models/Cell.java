package models;

import models.units.Unit;

import java.util.ArrayList;

public class Cell {
    private Texture texture;
    private MapObject object; // a building, a tree, a rock, ...
    private ArrayList<Unit> units;
    private boolean hasLadder;

    public Cell(Texture texture) {
        this.texture = texture;
        this.units = new ArrayList<>();
    }

    public Texture getTexture() {
        return texture;
    }

    public static boolean isABlockOccupied(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            if (cell.isOccupied())
                return true;
        }
        return false;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public static void setBlocksTexture(ArrayList<Cell> cells, Texture texture) {
        for (Cell cell : cells) {
            cell.setTexture(texture);
        }
    }

    public Building getBuilding() {
        if (object instanceof Building)
            return (Building) object;
        return null;
    }

    public MapObject getObject() {
        return object;
    }

    public void setObject(MapObject object) {
        this.object = object;
    }

    public boolean isOccupied() {
        return object != null;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean isPassable() {
        // some ifs
        return true;
    }

    public String cellInfo() {
        return null;
    }

    public void clear() {
        object = null;
        units.clear();
        texture = Texture.EARTH;
        hasLadder = false;
        //Other things later
    }

    @Override
    public String toString() {
        return null; // TODO the details for showing map
    }
}
