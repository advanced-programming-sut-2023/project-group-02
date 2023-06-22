package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.units.Unit;
import utils.Utils;

public abstract class Building extends MapObject {
    protected BuildingType type;
    protected MaterialInstance[] buildingMaterials;
    protected final int initialHitpoint;
    protected int hitpoint;
    protected int workerCount = 0;
    protected int effectOnPopularity = 0;
    protected final String imagePath;
    protected Unit unit;

    public Building(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity) {
        this.name = name;
        this.type = type;
        this.buildingMaterials = buildingMaterials;
        this.initialHitpoint = initialHitpoint;
        this.hitpoint = initialHitpoint;
        this.workerCount = workerCount;
        this.effectOnPopularity = effectOnPopularity;
        this.imagePath = "/images/mainbuildings/" + Utils.toCamelCase(name) + ".png";
    }

    public String getName() {
        return name;
    }

    public BuildingType getType() {
        return type;
    }

    public MaterialInstance[] getBuildingMaterials() {
        return buildingMaterials;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public int getInitialHitpoint() {
        return initialHitpoint;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public int getEffectOnPopularity() {
        return effectOnPopularity;
    }

    public void decreaseHitpoint(int value) {
        hitpoint -= value;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageView getBuildingImage() {
        //TODO: after adding all images humanize this method
        String url = "";
        try {
            url = getClass().getResource(imagePath).toExternalForm();
        } catch (Exception e) {
            return null;
        }
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        return imageView;
    }
}
