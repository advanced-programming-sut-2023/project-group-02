package models;

import models.units.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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

    public static Texture blocksTexture(ArrayList<Cell> cells) {
        ArrayList<Texture> textures = new ArrayList<>();
        for (Cell cell : cells) {
            if (!textures.contains(cell.getTexture()))
                textures.add(cell.getTexture());
        }
        if (textures.size() == 1)
            return textures.get(0);
        return null;
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
        Texture[] notPassableTextures = {Texture.SEA,Texture.ROCK};
        String[] notPassableObjectsNames = {"short wall", "tall wall"}; //TODO add other buildings and textures if its needed
        for (Texture texture1 : notPassableTextures) {
            if (texture1.equals(texture)) return false;
        }
        if (hasLadder) return true;
        for (String objectsName : notPassableObjectsNames) {
            if (object != null && object.getName().equals(objectsName)) return false;
        }
        return true;
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
        String answer = "";
        if (object instanceof Building)
            answer += "Building: " + object.getName() + " - owner: " + object.getOwner().getUsername() + "\n";
        else if (object instanceof Tree || object instanceof Rock)
            answer += "Object: " + object.getName() + "\n";

        for (Unit unit : units) {
            answer += unit.getName() + " - " + unit.getOwner().getUsername() + "\n";
        }

        answer += "Texture: " + texture.getName();
        return answer;
    }
}
