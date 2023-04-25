package view;

import utils.Parser;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    public static void run(Scanner scanner) {
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("show price list")) {
                showPriceList();
            } else if (parser.beginsWith("buy")) {
                buyItem(parser);
            } else if (parser.beginsWith("sell")) {
                sellItem(parser);
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at ShopMenu");
            } else if (parser.beginsWith("exit")) {
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static void showPriceList() {

    }

    public static void buyItem(Parser parser) {

    }

    public static void sellItem(Parser parser) {

    }
}
