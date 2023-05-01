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

    public void addIncreaseItem(T item, int amount) {
        for (int i = 0; i < amount; i++) {
            add(item);
        }
    }

    /**
     * Checks if a building is an InventoryBuilding and the type of its contained
     * item
     * matches
     * that of `dummyItem`.
     *
     * @param <R>
     * @param building
     * @param dummyItem
     * @return the building if the conditions are met, null otherwise
     */
    public static <R> InventoryBuilding<R> castIfPossible(Building building, R dummyItem) {
        // why is java so stupid
        class DummyClass extends InventoryBuilding<R> {
            public DummyClass(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
                    int workerCount, int effectOnPopularity, int capacity) {
                super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, capacity);
            }
        }

        try {
            InventoryBuilding<R> inventoryBuilding = DummyClass.class.cast(building);
            return inventoryBuilding;
        } catch (ClassCastException exception) {
            return null;
        }
    }
}
