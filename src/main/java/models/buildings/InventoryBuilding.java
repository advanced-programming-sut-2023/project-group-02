package models.buildings;

import java.util.HashMap;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class InventoryBuilding<T> extends Building {
    private int capacity;
    private HashMap<T, Integer> items;

    public InventoryBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity, int capacity) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void add(T item) {
        if (items.containsKey(item)) {
            int newAmount = items.get(item) + 1;
            if (newAmount < capacity) {
                items.put(item, newAmount);
            }
        } else {
            items.put(item, 1);
        }
    }

    public int getAmount(T item) {
        return items.get(item);
    }
}
