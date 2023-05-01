package controllers;

import models.Material;
import models.Trade;
import models.User;
import view.enums.TradeMenuMessages;

import java.util.ArrayList;

public class TradeMenuController {
    private static ArrayList<Trade> allTrades = new ArrayList<>();

    private static Trade findTradeById(int id) {
        for (Trade trade : allTrades) {
            if (trade.getId() == id)
                return trade;
        }
        return null;
    }

    public static String showAllTrades() {
        if (allTrades.size() == 0) return "There are no trades yet!";
        StringBuilder answer = new StringBuilder();
        for (Trade trade : allTrades) {
            answer.append(trade.toString()).append("\n");
        }
        return answer.toString().trim();
    }

    public static TradeMenuMessages tradeRequest(String resourceType, int amount, int price, String message) {
        Material material;
        if ((material = Material.findMaterialByName(resourceType)) == null)
            return TradeMenuMessages.INVALID_RESOURCE;
        if (amount < 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        if (price < 0)
            return TradeMenuMessages.INVALID_PRICE;
        if (price > GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getMaterialAmount(Material.GOLD,GameMenuController.getCurrentGame().getCurrentPlayer()))
            return TradeMenuMessages.NOT_ENOUGH_MONEY;

        allTrades.add(new Trade(GameMenuController.getCurrentGame().getCurrentPlayer(),material,amount,price,message));
        return TradeMenuMessages.REQUEST_IS_MADE;
    }

    public static TradeMenuMessages acceptTrade(int id, String message) {
        Trade trade;
        if ((trade = findTradeById(id)) == null)
            return TradeMenuMessages.ID_DOESNT_EXIST;
        if (GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getMaterialAmount(trade.getResourceType(),GameMenuController.getCurrentGame().getCurrentPlayer()) < trade.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_MATERIAL;
        if (trade.isAccepted())
            return TradeMenuMessages.ALREADY_ACCEPTED;

        trade.setAcceptor(GameMenuController.getCurrentGame().getCurrentPlayer());
        trade.setAcceptorMessage(message);
        tradeDone(trade);
        return TradeMenuMessages.ACCEPTED;
    }

    static void tradeDone(Trade trade) {
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getRequester()).increaseMaterial(trade.getResourceType(), trade.getAmount(),trade.getRequester());
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getRequester()).reduceMaterial(Material.GOLD, trade.getPrice(),trade.getRequester());
        trade.getRequester().getUsersNewTrades().add(trade);
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getAcceptor()).reduceMaterial(trade.getResourceType(),trade.getAmount(),trade.getAcceptor());
        GameMenuController.getCurrentGame().getPlayersGovernment(trade.getAcceptor()).increaseMaterial(Material.GOLD, trade.getPrice(),trade.getAcceptor());
        trade.getAcceptor().getUsersNewTrades().add(trade);
    }

    public static String showTradeHistory() {
        User player;
        StringBuilder answer = new StringBuilder();
        if ((player = GameMenuController.getCurrentGame().getCurrentPlayer()).getUsersNewTrades().size() == 0)
            return "You don't have any new trades!";

        for (Trade trade : player.getUsersNewTrades()) {
            answer.append(trade.toString()).append("\n");
        }
        player.getUsersNewTrades().clear();
        return answer.toString().trim();
    }
}
