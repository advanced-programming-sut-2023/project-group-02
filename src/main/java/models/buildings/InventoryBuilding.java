package models.buildings;

import java.util.HashMap;

import models.*;

public class InventoryBuilding extends Building {
    private int capacity;
    private HashMap<Object, Integer> items;
    private Class<?> itemClass;

    public InventoryBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity, Class<?> itemClass, int capacity) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
        this.capacity = capacity;
        this.itemClass = itemClass;
        items = new HashMap<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAmount(Object item) {
        if (!items.containsKey(item))
            return 0;
        return items.get(item);
    }

    public boolean isItemTypeSuitable(Object item) {
        return itemClass.isInstance(item);
    }

    public int increaseItem(Object item, int amount) {
        int currentAmount = 0;
        if (items.containsKey(item)) {
            currentAmount = items.get(item);
        } else if (isItemTypeSuitable(item)) {
            items.put(item,0);
        } else {
            return amount;
        }
        if (currentAmount + amount > capacity) {
            items.put(item,capacity);
            return amount + currentAmount - capacity;
        } else {
            items.put(item,currentAmount + amount);
            return 0;
        }
    }

    public int decreaseItem(Object item, int amount) {
        int currentAmount = 0;
        if (items.containsKey(item)) {
            currentAmount = items.get(item);
        }
        if (amount > currentAmount) {
            items.put(item,0);
            return amount - currentAmount;
        } else {
            items.put(item,currentAmount - amount);
            return 0;
        }
    }
}
