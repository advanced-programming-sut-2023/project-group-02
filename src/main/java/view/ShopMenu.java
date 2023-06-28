package view;

import controllers.GameMenuController;
import controllers.ItemsController;
import controllers.ShopMenuController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Material;
import utils.Graphics;
import view.enums.ShopMenuMessages;

import java.util.Objects;

public class ShopMenu {
    HBox operateHBox = new HBox();
    VBox coinsLeft = new VBox();
    Text itemsLeft = new Text();
    Text coinsLeftText = new Text();

    public Pane getPane() {
        Pane shopPane = new Pane();
        initPane(shopPane);
        return shopPane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/shop-menu.jpg"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.getChildren().addAll(makeMainVBox(), makeBackButton(), (coinsLeft = makeCoinsLeftBox()), makeEnterTradeMenuButton());
        coinsLeftText.textProperty().addListener((observable, oldValue, newValue) -> coinsLeftText.setText(coinsLeftText.getText()));
    }

    private VBox makeCoinsLeftBox() {
        ImageView coinImage = new ImageView(new Image(getClass().getResource("/images/others/coin.png").toExternalForm()));
        coinImage.setFitHeight(70);
        coinImage.setFitWidth(70);
        coinsLeftText.setText(" " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(Material.GOLD));
        coinsLeftText.setTranslateX(50);
        coinsLeftText.getStyleClass().add("title1");
        VBox goldLeft = new VBox(coinImage, coinsLeftText);
        goldLeft.setSpacing(10);
        goldLeft.setTranslateX(0.66 * Main.getStage().getWidth());
        goldLeft.setTranslateY(0.77 * Main.getStage().getHeight());
        return goldLeft;
    }

    private VBox makeMainVBox() {
        HBox shelf1 = makeShelfHBox("material");
        shelf1.setTranslateX(150);
        VBox mainVBox = new VBox(shelf1, makeShelfHBox("food"), makeShelfHBox("martialEquipment"), operateHBox);
        operateHBox.idProperty().addListener((observable, oldValue, newValue) -> {
            updateOperateHBox(ItemsController.findItemWithName(newValue));
        });
        itemsLeft.textProperty().addListener((observable, oldValue, newValue) -> itemsLeft.setText(itemsLeft.getText()));
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
//        itemsLeft = new Text("10");
        itemsLeft = new Text(GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(item) + " ");
        itemsLeft.getStyleClass().add("title1");
        VBox imageAndAmountLeft = new VBox(image, itemsLeft);
        imageAndAmountLeft.setSpacing(10);
        VBox sellAndBuy = makeSellAndBuyBox(item);
        operateHBox.getChildren().addAll(imageAndAmountLeft, sellAndBuy);
    }

    private VBox makeSellAndBuyBox(Object item) {
        VBox sellAndBuyBox = new VBox();
        sellAndBuyBox.setSpacing(20);

        Button sellButton = new Button("Sell " + ItemsController.getItemSellPrice(item));
        sellButton.getStyleClass().add("button1");
        sellButton.setOnAction(event -> {
//            itemsLeft.setText("20");
//            coinsLeftText.setText("13");
            itemsLeft.setText(GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(item) + " ");
            coinsLeftText.setText(" " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(Material.GOLD));
            ShopMenuMessages message = ShopMenuController.sellItem(item, 1);
            Graphics.showMessagePopup(message.getMessage());
            updateOperateHBox(item);
        });

        Button buyButton = new Button("Buy " + ItemsController.getItemBuyPrice(item));
        buyButton.getStyleClass().add("button1");
        buyButton.setOnAction(event -> {
//            itemsLeft.setText("15");
//            coinsLeftText.setText("5");
            itemsLeft.setText(GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(item) + " ");
            coinsLeftText.setText(" " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(Material.GOLD));
            ShopMenuMessages message = ShopMenuController.buyItem(item, 1);
            Graphics.showMessagePopup(message.getMessage());
            updateOperateHBox(item);
        });
        sellAndBuyBox.getChildren().addAll(buyButton, sellButton);

        return sellAndBuyBox;
    }

    private HBox makeShelfHBox(String itemType) {
        HBox shelf = new HBox();
        for (Object item : ItemsController.getImagedItemsWithType(itemType)) {
            ImageView itemImage = ItemsController.getItemsImage(item);
            itemImage.setOnMouseClicked(event -> operateHBox.idProperty().set(ItemsController.getItemName(item)));
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
            Main.getStage().setScene(new Scene(new GameMenu().getPane(false, null)));
            Main.getStage().setFullScreen(true);
        });
        return backButton;
    }

    private Button makeEnterTradeMenuButton() {
        Button enterTradeMenuButton = new Button("Enter Trade Menu");
        enterTradeMenuButton.getStyleClass().add("button1");
        enterTradeMenuButton.setTranslateX(20);
        enterTradeMenuButton.setTranslateY(0.95 * Main.getStage().getHeight());
        enterTradeMenuButton.setOnAction(event -> {
            Main.getStage().setScene(new Scene(new TradeMenu().getPane()));
            Main.getStage().setFullScreen(true);
        });
        return enterTradeMenuButton;
    }
}
