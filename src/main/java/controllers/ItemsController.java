package controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Food;
import models.MartialEquipment;
import models.Material;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

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

    public static ImageView getItemsImage(Object item) {
        String imagePath = null;
        if (item instanceof Food) imagePath = ((Food) item).getImagePath();
        else if (item instanceof Material) imagePath = ((Material) item).getImagePath();
        else if (item instanceof MartialEquipment) imagePath = ((MartialEquipment) item).getImagePath();

        //TODO: after adding all images humanize this method
        String url = "";
        try {
            url = ItemsController.class.getResource(Objects.requireNonNull(imagePath)).toExternalForm();
        } catch (Exception e) {
            return null;
        }
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        return imageView;
    }

    public static ArrayList<Object> getImagedItems() {
        ArrayList<Object> imagedItems = new ArrayList<>();
        for (Object item : getAllItems()) {
            if (getItemsImage(item) != null) {
                imagedItems.add(item);
            }
        }
        return imagedItems;
    }

    public static ArrayList<Object> getImagedItemsWithType(String itemType) {
        ArrayList<Object> items = new ArrayList<>();
        for (Object item : getImagedItems()) {
            if (itemType.equals("material") && item instanceof Material) items.add(item);
            else if (itemType.equals("food") && item instanceof Food) items.add(item);
            else if (itemType.equals("martialEquipment") && item instanceof MartialEquipment) items.add(item);
        }
        return items;
    }

    public static int getItemSellPrice(Object item) {
        if (item instanceof Food) return ((Food) item).getSellPrice();
        else if (item instanceof Material) return ((Material) item).getSellPrice();
        else if (item instanceof MartialEquipment) return ((MartialEquipment) item).getSellPrice();
        return 0;
    }

    public static int getItemBuyPrice(Object item) {
        if (item instanceof Food) return ((Food) item).getBuyPrice();
        else if (item instanceof Material) return ((Material) item).getBuyPrice();
        else if (item instanceof MartialEquipment) return ((MartialEquipment) item).getBuyPrice();
        return 0;
    }

    public static String getItemName(Object item) {
        if (item instanceof Food) return ((Food) item).getName();
        else if (item instanceof Material) return ((Material) item).getName();
        else if (item instanceof MartialEquipment) return ((MartialEquipment) item).getName();
        return null;
    }
}
