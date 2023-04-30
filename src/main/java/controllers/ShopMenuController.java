package controllers;

import models.Material;
import view.enums.ShopMenuMessages;

import java.util.ArrayList;
import java.util.List;

public class ShopMenuController {
    private static final ArrayList<Material> materialsToBuy = new ArrayList<>(List.of(Material.IRON,Material.WOOD,Material.STONE));

    public static String showPrice() {
        String answer = "";
        for (Material material : materialsToBuy) {
            answer += material.getMaterialName() + " Buy: " + material.getBuyPrice() + " Sell: " + material.getSellPrice() + "\n";
        }
        return answer.trim();
    }

    public static ShopMenuMessages buyItem(String itemName, int amount) {
        Material material;
        if ((material = Material.findMaterialByName(itemName)) == null)
            return ShopMenuMessages.INVALID_NAME;
        if (!materialsToBuy.contains(material))
            return ShopMenuMessages.NOT_TO_SELL;
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;

        GameMenuController.currentGame.getCurrentPlayersGovernment().reduceMaterial(Material.GOLD,amount * material.getBuyPrice());
        GameMenuController.currentGame.getCurrentPlayersGovernment().increaseMaterial(material,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    public static ShopMenuMessages sellItem(String itemName, int amount) {
        Material material;
        if ((material = Material.findMaterialByName(itemName)) == null)
            return ShopMenuMessages.INVALID_NAME;
        if (!materialsToBuy.contains(material))
            return ShopMenuMessages.NOT_TO_SELL;
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;

        GameMenuController.currentGame.getCurrentPlayersGovernment().increaseMaterial(Material.GOLD,amount * material.getSellPrice());
        GameMenuController.currentGame.getCurrentPlayersGovernment().reduceMaterial(material,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }
}
