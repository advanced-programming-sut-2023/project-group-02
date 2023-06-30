package controllers;

import models.Food;
import models.MartialEquipment;
import models.Material;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import client.view.enums.ShopMenuMessages;

public class ShopMenuController {
    public static ShopMenuMessages buyItem(Object item, int amount) {
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        int price = ItemsController.getItemBuyPrice(item);

        if (price * amount > GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(Material.GOLD))
            return ShopMenuMessages.NOT_ENOUGH_GOLD;

        buyOperation(item,amount,price);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    public static ShopMenuMessages sellItem(Object item, int amount) {
        if (amount < 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        if (amount > GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(item))
            return ShopMenuMessages.NOT_ENOUGH_MATERIAL;

        sellOperation(item,amount);
        return ShopMenuMessages.DONE_SUCCESSFULLY;
    }

    public static void sellOperation(Object item, int amount) {
        int price = ItemsController.getItemSellPrice(item);
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseItem(Material.GOLD,price * amount);
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceItem(item,amount);
    }

    private static void buyOperation(Object item, int amount, int price) {
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceItem(Material.GOLD,price * amount);
        GameMenuController.getCurrentGame().getCurrentPlayersGovernment().increaseItem(item,amount);
    }
}
