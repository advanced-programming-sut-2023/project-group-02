package view;

import controllers.ShopMenuController;
import models.Material;
import utils.Parser;
import view.enums.ShopMenuMessages;

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
        System.out.println(ShopMenuController.showPrice());
    }

    public static void buyItem(Parser parser) {
        ShopMenuMessages message = ShopMenuController.buyItem(parser.get("i"),Integer.parseInt(parser.get("a")));
        System.out.println(message.getMessage());
    }

    public static void sellItem(Parser parser) {
        ShopMenuMessages message = ShopMenuController.sellItem(parser.get("i"),Integer.parseInt(parser.get("a")));
        System.out.println(message.getMessage());
    }
}
