package view;

import controllers.GameMenuController;
import controllers.ItemsController;
import controllers.TradeMenuController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.Colors;
import models.User;
import utils.Graphics;
import view.enums.TradeMenuMessages;

import java.util.ArrayList;
import java.util.Objects;

public class TradeMenu {
    enum TradeType {
        REQUEST,
        DONATE,
        UNKNOWN,
    }
    HBox tradeMenuPane = new HBox();
    VBox itemsVBox;
    Object selectedItem = null;
    ImageView selectedItemImage = new ImageView();
    Text selectedItemName = new Text();
    User selectedUser = null;
    Text selectedItemAmount = new Text();
    int selectedAmount = 0;
    TextField priceTextField = new TextField();

    TextArea messageField = new TextArea();
    TradeType tradeType = TradeType.UNKNOWN;

    public HBox getPane() {
        initializePane();
        tradeMenuPane.setSpacing(60);
        return tradeMenuPane;
    }

    private void initializePane() {
        tradeMenuPane.getChildren().clear();
        selectedItem = null;
        tradeMenuPane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/trade-menu.jpg"))));
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
            selectedItemAmount.setText(selectedItemAmount.getText());
        });

        selectedItemAmount.textProperty().addListener((observable, oldValue, newValue) -> selectedItemAmount.setText(selectedItemAmount.getText()));

        tradeMenuPane.setOnKeyPressed(event -> {
            if (selectedItem != null && (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.EQUALS)) {
                selectedAmount ++;
                selectedItemAmount.setText(selectedAmount + " ");
            } else if (selectedItem != null && event.getCode() == KeyCode.MINUS) {
                selectedAmount --;
                selectedItemAmount.setText(selectedAmount + " ");
            }
        });

        VBox selectedVBox = makeSelectedVBox();
        selectedVBox.setTranslateX(300);
        VBox pricingVBox = makePricingVBox();
        pricingVBox.setTranslateX(260);
        VBox messageAndConfirmVBox = makeMessageAndConfirmVBox();
        messageAndConfirmVBox.setTranslateX(150);
        VBox mainVBox = new VBox(itemsVBox,selectedVBox,pricingVBox,messageAndConfirmVBox);
        VBox vBoxOfPlayers = makePlayersVBox();
        mainVBox.setSpacing(70);
        tradeMenuPane.getChildren().addAll(mainVBox,vBoxOfPlayers);
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
        HBox materialRow = makeItemsRow("material");
        materialRow.setTranslateX(200);
        VBox selectedVBox = makeSelectedVBox();
        selectedVBox.setTranslateX(300);

        VBox itemVBox = new VBox(materialRow,makeItemsRow("food"),
            makeItemsRow("martialEquipment"));
        itemVBox.setSpacing(10);
        return itemVBox;
    }

    private VBox makeSelectedVBox() {
        selectedItemImage.setFitHeight(70);
        selectedItemImage.setFitWidth(70);

        selectedItemName.getStyleClass().add("title1");
        selectedItemName.setTranslateX(20);

        selectedItemAmount.setTranslateX(20);
        selectedItemAmount.getStyleClass().add("title1");
        VBox selectedVBox = new VBox(selectedItemImage,selectedItemName,selectedItemAmount);
        selectedVBox.setSpacing(10);
        return selectedVBox;
    }

    private HBox makeItemsRow(String itemType) {
        HBox itemsRow = new HBox();
        for (Object item : ItemsController.getImagedItemsWithType(itemType)) {
            ImageView itemImage = ItemsController.getItemsImage(item);
            itemImage.setOnMouseClicked(event -> {
                selectedItem = item;
                selectedItemName.setText(ItemsController.getItemName(item));
                selectedAmount = 0;
                selectedItemAmount.setText(selectedAmount + " ");
            });
            itemsRow.getChildren().add(itemImage);
        }
        itemsRow.setSpacing(10);
        return itemsRow;
    }

    private VBox makePricingVBox() {
        Button donateButton = new Button("Donate");
        Button requestButton = new Button("Request");
        priceTextField = new TextField();
        priceTextField.setVisible(false);
        priceTextField.setMaxWidth(300);
        priceTextField.setPromptText("Enter the price you want to pay:");

        donateButton.getStyleClass().add("button1");
        donateButton.setOnAction(event -> {
            donateButton.setStyle("-fx-stroke: #00FF00; -fx-stroke-width: 2px;");
            requestButton.setStyle("-fx-background-color: #333333;");
            priceTextField.setVisible(false);
            tradeType = TradeType.DONATE;
        });

        requestButton.getStyleClass().add("button1");
        requestButton.setOnAction(event -> {
            requestButton.setStyle("-fx-stroke: #00FF00; -fx-stroke-width: 2px;");
            donateButton.setStyle("-fx-background-color: #333333;");
            priceTextField.setVisible(true);
            tradeType = TradeType.REQUEST;
        });

        HBox buttonsBox = new HBox(10, donateButton, requestButton);
        VBox mainBox = new VBox(10, buttonsBox, priceTextField);
        mainBox.setPadding(new Insets(10));
        return mainBox;
    }

    private VBox makeMessageAndConfirmVBox() {
        messageField = new TextArea();
        messageField.setPromptText("Enter your message here");
        messageField.setPrefColumnCount(10);
        messageField.setPrefRowCount(3);
        messageField.setTranslateY(-20);
        messageField.setMaxWidth(500);
        Button confirmButton = new Button("Confirm");
        confirmButton.getStyleClass().add("button1");
        confirmButton.setMaxWidth(20);
        confirmButton.setTranslateX(50);
        confirmButton.setOnAction(event -> makeTheTrade());

        VBox vBox = new VBox(messageField,confirmButton);
        vBox.setSpacing(5);
        return vBox;
    }

    private VBox makePlayersVBox() {
        VBox playersVBox = new VBox();
        for (User player : GameMenuController.getCurrentGame().getPlayers()) {
            if (!player.equals(GameMenuController.getCurrentGame().getCurrentPlayer()))
                playersVBox.getChildren().add(makeOnePlayerHBox(player,playersVBox));
        }
        playersVBox.setSpacing(10);
        playersVBox.setTranslateY(10);
        return playersVBox;
    }

    private HBox makeOnePlayerHBox(User player, VBox playersVBox) {
        ImageView avatar = player.getAvatar();
        avatar.setFitWidth(50);
        avatar.setFitHeight(50);

        Text username = new Text(player.getUsername());
        username.getStyleClass().add("title1");

        HBox onePlayer = new HBox(avatar,username);
        onePlayer.setOnMouseClicked(event -> {
            selectedUser = player;
            for (Text text : getAllUsernamesText(playersVBox)) {
                text.setStroke(Color.WHITE);
            }
            username.setStroke(Color.RED);
        });
        onePlayer.setSpacing(10);
        return onePlayer;
    }

    private ArrayList<Text> getAllUsernamesText(VBox vBox) {
        ArrayList<Text> allUsernameText = new ArrayList<>();
        for (Node child : vBox.getChildren()) {
            if (child instanceof HBox) {
                for (Node node : ((HBox) child).getChildren()) {
                    if (node instanceof Text)
                        allUsernameText.add((Text) node);
                }
            }
        }
        return allUsernameText;
    }

    private void makeTheTrade() {
        String finalMessage = "";
        if (tradeType.equals(TradeType.UNKNOWN)) {
            finalMessage = "Determine the type of trade!";
        } else if (tradeType.equals(TradeType.REQUEST) && priceTextField.getText().equals("")) {
            finalMessage = "Please fill all the fields!";
        } else {
            TradeMenuMessages message;
            if (tradeType.equals(TradeType.DONATE))
                message = TradeMenuController.tradeRequest(selectedUser, selectedItem, selectedAmount,
                    "0", messageField.getText());
            else message = TradeMenuController.tradeRequest(selectedUser, selectedItem, selectedAmount,
                priceTextField.getText(), messageField.getText());

            finalMessage = message.getMessage();
        }
        Graphics.showMessagePopup(finalMessage);
    }
}
