package view;

import utils.Parser;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            Matcher matcher;
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

    }

    public static void showTradeList() {

    }

    public static void acceptTrade(Parser parser) {

    }

    public static void showTradeHistory() {

    }
}
