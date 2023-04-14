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

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public MapObject getBuilding() {
        return object;
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

    @Override
    public String toString() {
        return null; // TODO the details for showing map
    }
}
