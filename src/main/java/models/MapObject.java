package models;

import controllers.GameMenuController;

public abstract class MapObject {
    private final User owner;
    private final int x, y;

    public MapObject(int x, int y, User owner) {
        this.x = x;
        this.y = y;
        GameMenuController.getCurrentGame().getMap().findCellWithXAndY(x,y).setObject(this);
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
