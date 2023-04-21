package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class DamagingBuilding extends Building {
    private int damage;

    public DamagingBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity, int damage) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
