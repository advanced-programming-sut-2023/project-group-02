package models;

import java.util.ArrayList;
import java.util.List;

public enum Material {
    WOOD(4,"wood"),
    STONE(6,"stone"),
    GOLD(1,"gold"),
    IRON(10,"iron"),
    TAR(3,"tar");

    private final int buyPrice;
    private final String materialName;
    private final String imagePath;
    private static ArrayList<Material> allMaterials = new ArrayList<>(List.of(STONE,WOOD,IRON));

    private Material(int price, String materialName) {
        this.buyPrice = price;
        this.materialName = materialName;
        imagePath = "/images/items/" + materialName + ".png";
    }

    public static ArrayList<Material> getAllMaterials() {
        return allMaterials;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return buyPrice/2;
    }

    public String getName() {
        return materialName;
    }

    public static Material findMaterialByName(String name) {
        for (Material material : allMaterials) {
            if (material.getName().equals(name))
                return material;
        }
        return null;
    }

    public String getImagePath() {
        return imagePath;
    }
}
