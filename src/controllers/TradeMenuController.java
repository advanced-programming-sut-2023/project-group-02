package controllers;

import models.Trade;
import view.enums.TradeMenuMessages;

import java.util.ArrayList;

public class TradeMenuController {
    private static ArrayList<Trade> allTrades = new ArrayList<>();

    public static ArrayList<Trade> getAllTrades() {
        return allTrades;
    }

    public static TradeMenuMessages tradeRequest(String recipientName, String resourceType, int amount, int price, String message) {
        return null;
    }

    public static TradeMenuMessages acceptTrade(int id, String message) {
        return null;
    }


}
