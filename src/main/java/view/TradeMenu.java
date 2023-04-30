package view;

import controllers.TradeMenuController;
import utils.Parser;
import view.enums.TradeMenuMessages;

import java.util.Scanner;

public class TradeMenu {
    public static void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("trade list")) {
                showTradeList();
            } else if (parser.beginsWith("trade accept")) {
                acceptTrade(parser);
            } else if (parser.beginsWith("trade history")) {
                showTradeHistory();
            } else if (parser.beginsWith("trade")) {
                tradeRequest(parser);
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at TradeMenu");
            } else if (parser.beginsWith("exit")) {
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static void tradeRequest(Parser parser) {
        TradeMenuMessages message = TradeMenuController.tradeRequest(parser.get("t"), Integer.parseInt(parser.get("a")),
            Integer.parseInt(parser.get("p")), parser.get("m"));
        System.out.println(message.getMessage());
    }

    public static void showTradeList() {
        System.out.println(TradeMenuController.showAllTrades());
    }

    public static void acceptTrade(Parser parser) {
        TradeMenuMessages message = TradeMenuController.acceptTrade(Integer.parseInt(parser.get("i")), parser.get("m"));
        System.out.println(message.getMessage());
    }

    public static void showTradeHistory() {
        System.out.println(TradeMenuController.showTradeHistory());
    }
}
