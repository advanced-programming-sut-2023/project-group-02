package view;

import controllers.ItemsController;
import controllers.ShopMenuController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.Graphics;
import utils.Parser;
import view.enums.ShopMenuMessages;

import java.util.Objects;
import java.util.Scanner;

public class ShopMenu {
    HBox operateHBox = new HBox();

    public Pane getPane() {
        Pane shopPane = new Pane();
        initPane(shopPane);
        return shopPane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/shop-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.getChildren().addAll(makeMainVBox(),makeBackButton());
    }

    private VBox makeMainVBox() {
        HBox shelf1 = makeShelfHBox(0,4);
        shelf1.setTranslateX(150);
        VBox mainVBox = new VBox(shelf1,makeShelfHBox(4,12),makeShelfHBox(12,20),operateHBox);
        operateHBox.idProperty().addListener((observable,oldValue,newValue) -> {
            updateOperateHBox(ItemsController.findItemWithName(newValue));
        });
        mainVBox.setSpacing(120);
        mainVBox.setTranslateX(300);
        mainVBox.setTranslateY(50);
        return mainVBox;
    }

    private void updateOperateHBox(Object item) {
        operateHBox.getChildren().clear();
        operateHBox.setSpacing(20);
        operateHBox.setTranslateX(120);
        operateHBox.setId(ItemsController.getItemName(item));
        ImageView image = ItemsController.getItemsImage(item);
        VBox sellAndBuy = makeSellAndBuyBox(item);
        operateHBox.getChildren().addAll(image,sellAndBuy);
    }

    private VBox makeSellAndBuyBox(Object item) {
        VBox sellAndBuyBox = new VBox();
        sellAndBuyBox.setSpacing(20);
        Button sellButton = new Button("Sell " + ItemsController.getItemSellPrice(item));
        sellButton.getStyleClass().add("button1");
        Button buyButton = new Button("Buy " + ItemsController.getItemBuyPrice(item));
        buyButton.getStyleClass().add("button1");
        sellAndBuyBox.getChildren().addAll(buyButton,sellButton);
        return sellAndBuyBox;
    }

    private HBox makeShelfHBox(int start, int end) {
        HBox shelf = new HBox();
        for (int i = start; i < end; i++) {
            Object item = ItemsController.getImagedItems().get(i);
            ImageView itemImage = ItemsController.getItemsImage(item);
            itemImage.setOnMouseClicked(event -> {
                operateHBox.idProperty().set(ItemsController.getItemName(item));
            });
            shelf.getChildren().add(itemImage);
        }
        shelf.setSpacing(20);
        return shelf;
    }

    private Button makeBackButton() {
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button1");
        backButton.setTranslateX(20);
        backButton.setTranslateY(20);
        backButton.setOnAction(event -> {
            Main.getStage().setScene(new Scene(new GameMenu().getPane()));
            Main.getStage().setFullScreen(true);
        });
        return backButton;
    }

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
