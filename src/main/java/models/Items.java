package models;

import java.util.ArrayList;
import java.util.EnumSet;

enum ItemType {
    MATERIAL,
    FOOD,

}

public enum Items {
    IRON("iron", 8, ItemType.MATERIAL),
    WOOD("wood", 4, ItemType.MATERIAL),
    GOLD("gold", 0, ItemType.MATERIAL),
    STONE("stone", 6, ItemType.MATERIAL),
    TAR("tar", 2, ItemType.MATERIAL),
    BREAD("bread", 3, ItemType.FOOD),
    APPLE("apple", 4, ItemType.FOOD),
    MEAT("meat", 7, ItemType.FOOD),
    WHEAT("wheat", 4, ItemType.FOOD),
    BARLEY("barley", 4, ItemType.FOOD),
    BEER("beer", 3, ItemType.FOOD),
    CHEESE("cheese", 4, ItemType.FOOD),
    FLOUR("flour", 4, ItemType.FOOD),
    ;

    private final String name;
    private final int price;
    private final ItemType itemType;

    Items(String name, int price, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public int getBuyPrice() {
        return price;
    }

    public int getSellPrice() {
        return price / 2;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public static ArrayList<Items> getAllItems() {
        return new ArrayList<Items>(EnumSet.allOf(Items.class));
    }

    public static ArrayList<Items> getAllFoods() {
        ArrayList<Items> allFoods = new ArrayList<>();
        for (Items item : Items.getAllItems()) {
            if (item.getItemType().equals(ItemType.FOOD))
                allFoods.add(item);
        }
        return allFoods;
    }

    public static ArrayList<Items> getAllMaterials() {
        ArrayList<Items> allMaterials = new ArrayList<>();
        for (Items item : Items.getAllItems()) {
            if (item.getItemType().equals(ItemType.MATERIAL))
                allMaterials.add(item);
        }
        return allMaterials;
    }

    public static Items findFoodByName(String name) {
        for (Items food : Items.getAllFoods()) {
            if (food.getName().equals(name))
                return food;
        }
        return null;
    }

    public static Items findMaterialByName(String name) {
        for (Items material : Items.getAllMaterials()) {
            if (material.getName().equals(name))
                return material;
        }
        return null;
    }
}
