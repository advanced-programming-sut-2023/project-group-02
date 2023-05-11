package view;

import controllers.ShopMenuController;
import utils.Parser;
import view.enums.ShopMenuMessages;

import java.util.Scanner;

public class ShopMenu {
    public void run(Scanner scanner) {
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

    void showPriceList() {
        System.out.println(ShopMenuController.showPrice());
    }

    void buyItem(Parser parser) {
        ShopMenuMessages message = ShopMenuController.buyItem(parser.get("i"),Integer.parseInt(parser.get("a")));
        System.out.println(message.getMessage());
    }

    void sellItem(Parser parser) {
        ShopMenuMessages message = ShopMenuController.sellItem(parser.get("i"),Integer.parseInt(parser.get("a")));
        System.out.println(message.getMessage());
    }
}
