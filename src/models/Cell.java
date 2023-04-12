package models;

import java.util.ArrayList;

public class Cell {
    private Texture texture;
    private Building building;
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

    Building getBuilding() {
        return building;
    }

    boolean isOccupied() {
        return building != null;
    }

    ArrayList<Unit> getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return null; //TODO the details for showing map
    }
}
