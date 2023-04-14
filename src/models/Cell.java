package models;

import models.units.Unit;

import java.util.ArrayList;

public class Cell {
    private Texture texture;
    private MapObject object; // a building, a tree, a rock, ...
    private ArrayList<Unit> units;

    public Cell(Texture texture) {
        this.texture = texture;
        this.units = new ArrayList<>();
    }

    Texture getTexture() {
        return texture;
    }

    void setTexture(Texture texture) {
        this.texture = texture;
    }

    MapObject getBuilding() {
        return object;
    }

    boolean isOccupied() {
        return object != null;
    }

    ArrayList<Unit> getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return null; // TODO the details for showing map
    }
}
