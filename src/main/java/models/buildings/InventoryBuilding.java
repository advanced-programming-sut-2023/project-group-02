package models.buildings;

import java.util.ArrayList;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class InventoryBuilding<T> extends Building {
    private int capacity;
    private ArrayList<T> items;

    public InventoryBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity, int capacity) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void add(T item) {
        if (items.size() < capacity) {
            items.add(item);
        }
    }

    public int getCount() {
        return items.size();
    }
}
