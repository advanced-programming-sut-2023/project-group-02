package controllers;

import models.Food;
import models.MartialEquipment;
import models.Material;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ItemsController {
    private final static ArrayList<Material> allMaterials = new ArrayList<>(EnumSet.allOf(Material.class));
    private final static ArrayList<Food> allFoods = new ArrayList<>(EnumSet.allOf(Food.class));
    private final static ArrayList<MartialEquipment> allMaterialEquipments = new ArrayList<>(EnumSet.allOf(MartialEquipment.class));

    public static ArrayList<Object> getAllItems() {
        ArrayList<Object> allItems = new ArrayList<>();
        allItems.addAll(allMaterials);
        allItems.addAll(allFoods);
        allItems.addAll(allMaterialEquipments);
        return allItems;
    }

    public static ArrayList<Food> getAllFoods() {
        return allFoods;
    }

    public static ArrayList<Material> getAllMaterials() {
        return allMaterials;
    }

    public static ArrayList<MartialEquipment> getAllMaterialEquipments() {
        return allMaterialEquipments;
    }

    public static Object findItemWithName(String name) {
        for (Object item : getAllItems()) {
            if ((item instanceof Food && ((Food) item).getName().equals(name)) || (item instanceof Material && ((Material) item).getName().equals(name))
            || (item instanceof MartialEquipment && ((MartialEquipment) item).getName().equals(name)))
                return item;
        }
        return null;
    }
}
