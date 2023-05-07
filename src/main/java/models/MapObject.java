package models;

import controllers.GameMenuController;
import controllers.UserController;

public abstract class MapObject {
    protected String name;
    protected User owner;
    private int x, y;

    public MapObject() {
        this.owner = UserController.getCurrentUser();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
