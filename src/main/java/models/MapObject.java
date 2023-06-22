package models;

import javafx.scene.image.ImageView;

public abstract class MapObject {
    protected String name;
    protected User owner;
    private int x, y;
    protected String imagePath;

    public String getImagePath() {
        return imagePath;
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

    public ImageView getImage() {
        if (this instanceof Building) return ((Building) this).getBuildingImage();
        else {
            //TODO when tree and rocks are added
        }
        return null;
    }
}
