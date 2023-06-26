package view;

import controllers.ItemsController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.User;
import utils.Graphics;

import java.util.Objects;

public class TradeMenu {
    HBox tradeMenuPane = new HBox();
    VBox itemsVBox;
    Object selectedItem = null;
    ImageView selectedItemImage = new ImageView();
    Text selectedItemName = new Text();
    User selectedUser = null;

    public HBox getPane() {
        initializePane();
        tradeMenuPane.setSpacing(60);
        return tradeMenuPane;
    }

    private void initializePane() {
        tradeMenuPane.getChildren().clear();
        tradeMenuPane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.jpg"))));
        tradeMenuPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());

        Button newTradeButton = new Button("Request New Trade");
        newTradeButton.getStyleClass().add("button1");
        newTradeButton.setOnAction(event -> getNewTradePane());

        Button inboxButton = new Button("Inbox");
        inboxButton.getStyleClass().add("button1");
        inboxButton.setOnAction(event -> getInboxPane());

        Button sentButton = new Button("Sent");
        sentButton.getStyleClass().add("button1");
        sentButton.setOnAction(event -> getSentPane());

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button1");
        backButton.setOnAction(event -> {
            Main.getStage().setScene(new Scene(new ShopMenu().getPane()));
            Main.getStage().setFullScreen(true);
        });

        VBox buttons = new VBox(newTradeButton,inboxButton,sentButton,backButton);
        buttons.setSpacing(10);
        buttons.setTranslateX(10);
        buttons.setTranslateY(10);
        tradeMenuPane.getChildren().add(buttons);
    }


    public void getNewTradePane() {
        initializePane();
        initializeNewTradePane();
    }

    private void initializeNewTradePane() {
        itemsVBox = makeItemVBox();
        itemsVBox.setTranslateY(30);
        selectedItemName.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedItemName.setText(selectedItemName.getText());
            selectedItemImage.setImage(ItemsController.getItemsImage(selectedItem).getImage());
        });
        tradeMenuPane.setOnKeyPressed(event -> {
            if (selectedItem != null && event.getCode() == KeyCode.PLUS) {
                System.out.println("hello plus is here");
            } else if (selectedItem != null && event.getCode() == KeyCode.MINUS) {
                System.out.println("hello minus is here");
            }
        });
        tradeMenuPane.getChildren().add(itemsVBox);
    }


    private void getInboxPane() {
        initializePane();
        initializeInboxPane();
    }

    private void initializeInboxPane() {
        tradeMenuPane.getChildren().add(new Button("impolite person"));
    }

    private void getSentPane() {
        initializePane();
        initializeSentPane();
    }

    private void initializeSentPane() {

    }


    private VBox makeItemVBox() {
        selectedItemImage.setFitHeight(70);
        selectedItemImage.setFitWidth(70);
        selectedItemName.getStyleClass().add("title");
        VBox itemVBox = new VBox(makeItemsRow("material"),makeItemsRow("food"),
            makeItemsRow("martialEquipment"),selectedItemImage,selectedItemName);
        itemVBox.setSpacing(10);
        return itemVBox;
    }

    private HBox makeItemsRow(String itemType) {
        HBox itemsRow = new HBox();
        for (Object item : ItemsController.getImagedItemsWithType(itemType)) {
            ImageView itemImage = ItemsController.getItemsImage(item);
            itemImage.setOnMouseClicked(event -> {
                selectedItem = item;
                selectedItemName.setText(ItemsController.getItemName(item));
            });
            itemsRow.getChildren().add(itemImage);
        }
        itemsRow.setSpacing(10);
        return itemsRow;
    }
}
