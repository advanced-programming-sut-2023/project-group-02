package view;

import controllers.*;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import models.*;
import models.Cell;
import models.Map;
import models.units.MakeUnitInstances;
import models.units.Unit;
import utils.Graphics;
import utils.Parser;
import utils.Utils;
import view.enums.GameMenuMessages;

import java.util.*;

public class GameMenu {
    private boolean isGameOver = false;
    private boolean isPreGame = false;
    private ArrayList<User> players;

    private User currentPlayer = null;
    private final ArrayList<CellWrapper> selectedTiles = new ArrayList<>();
    private Point2D selectionStart;
    private ChoiceBox<String> textureChoiceBox;
    private Button clearButton;
    private Button nextButton;
    private final int TILE_SIZE = CellWrapper.getSquareSize();
    private int scrollX = 0, scrollY = 0;
    private Pane rootPane;
    private GridPane gridPane;
    private HBox bottomHBox;
    private VBox governmentVBox;
    private Text popularityText = new Text();
    private ScrollPane itemsScrollPane;
    private boolean showBuildingsBar = true;

    private void renderMap(Map map, int fromRow, int toRow, int fromCol, int toCol, int offsetX,
                           int offsetY) {
        gridPane.getChildren().clear();

        for (int row = fromRow; row <= toRow && row < map.getHeight(); row++) {
            for (int col = fromCol; col <= toCol && col < map.getWidth(); col++) {
                CellWrapper cellWrapper;
                if ((cellWrapper = CellWrapper.findCellWrapperWithXAndY(GameMenuController.getCurrentGameCellWrappers()
                    , col, row)) == null) {
                    cellWrapper = new CellWrapper(map.findCellWithXAndY(col, row));
                    GameMenuController.getCurrentGameCellWrappers().add(cellWrapper);
                }

                if (cellWrapper.getObject() != null) {
                    cellWrapper.getChildren().add(cellWrapper.getObject().getImage());
                }
                cellWrapper.addUnitsImages();

                cellWrapper.setOnDragOver(event -> {
                    if (event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                });
                CellWrapper finalCellWrapper = cellWrapper;
                cellWrapper.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    finalCellWrapper.dropObject(db.getString(), db.getImage(), isPreGame, currentPlayer);
                    event.setDropCompleted(true);
                    event.consume();
                });

                GridPane.setColumnIndex(cellWrapper, col - fromCol);
                GridPane.setRowIndex(cellWrapper, row - fromRow);

                cellWrapper.setTranslateX(-offsetX);
                cellWrapper.setTranslateY(-offsetY);
                gridPane.getChildren().add(cellWrapper);
                rootPane.requestFocus();
            }
        }
    }

    private void renderMapFromScrollPosition(Map map, Pane rootPane) {
        Window window = rootPane.getScene().getWindow();
        if (window == null)
            return;
        int windowWidth = (int) window.getWidth();
        int windowHeight = (int) window.getHeight();

        int offsetX = scrollX % TILE_SIZE;
        int offsetY = scrollY % TILE_SIZE;

        int fromRow = (int) (scrollY / gridPane.getHeight() * map.getHeight());
        int toRow = fromRow + (int) (windowHeight / TILE_SIZE);
        int fromCol = (int) (scrollX / gridPane.getWidth() * map.getWidth());
        int toCol = fromCol + (int) (windowWidth / TILE_SIZE);
        renderMap(map, fromRow, toRow, fromCol, toCol, offsetX, offsetY);
    }

    public Pane getPane(boolean isPreGame, ArrayList<User> players) {
        if (players != null)
            this.players = players;
        this.isPreGame = isPreGame;
        Game game = GameMenuController.getCurrentGame();
        if (game == null) {
            System.out.println("game is null");
            // TODO: remove this
            game = new Game(new ArrayList<>(), 0, new Map(50, 50));
            GameMenuController.setCurrentGame(game);
        }
        Map map = game.getMap();

        gridPane = new GridPane();
        gridPane.setPrefSize(TILE_SIZE * map.getWidth(), TILE_SIZE * map.getHeight());

        rootPane = new Pane(gridPane);
        gridPane.setLayoutX(0);
        gridPane.setLayoutY(0);
        rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());

        gridPane.setOnMousePressed(event -> {
            handleMousePressed(event, rootPane);
        });
        gridPane.setOnMouseDragged(event -> {
            handleMouseDragged(event, rootPane);
        });
        gridPane.setOnMouseClicked(event -> {
            handleMouseClicked(event, rootPane);
        });

        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();

        rootPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                scrollX = Math.min(scrollX + TILE_SIZE / 2, map.getWidth() * TILE_SIZE - (int) rootPane.getWidth());
                renderMapFromScrollPosition(map, rootPane);
            } else if (event.getCode() == KeyCode.LEFT) {
                scrollX = Math.max(scrollX - TILE_SIZE / 2, 0);
                renderMapFromScrollPosition(map, rootPane);
            } else if (event.getCode() == KeyCode.UP) {
                scrollY = Math.max(scrollY - TILE_SIZE / 2, 0);
                renderMapFromScrollPosition(map, rootPane);
            } else if (event.getCode() == KeyCode.DOWN) {
                scrollY = Math.min(scrollY + TILE_SIZE / 2, map.getHeight() * TILE_SIZE - (int) rootPane.getHeight());
                renderMapFromScrollPosition(map, rootPane);
            } else if (event.getCode() == KeyCode.S) {
                goToShopMenu();
            } else if (event.getCode() == KeyCode.C && event.isShortcutDown()) {
                copy();
            } else if (event.getCode() == KeyCode.V && event.isShortcutDown()) {
                paste();
            }
        });
        // TODO: maybe we should lock the focus on this pane, or set the listener on the
        // scene instead

        // render when the pane is added to the scene
        rootPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.windowProperty().addListener((observable2, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        newWindow.widthProperty().addListener((observable1, oldValue1, newValue1) -> {
                            int width = newValue1.intValue();
                            if (width > 0 && width < 10000) {
                                renderMapFromScrollPosition(map, rootPane);
                            }
                        });
                        newWindow.heightProperty().addListener((observable1, oldValue1, newValue1) -> {
                            renderMapFromScrollPosition(map, rootPane);
                        });
                    }
                });
            }
        });

        bottomHBox = makeDefaultBottomMenuHBox(isPreGame);
        if (!isPreGame) {
            makePopularityBox();
            makeEnterGovernmentButton();
        }
        popularityText.textProperty().addListener((observable, oldValue, newValue) -> popularityText.setText(popularityText.getText()));

        itemsScrollPane.contentProperty().addListener((observable, oldValue, newValue) -> itemsScrollPane.setContent(itemsScrollPane.getContent()));

        rootPane.getChildren().add(bottomHBox);
        doPreGameProcess(isPreGame);
        addNextTurnButton(isPreGame);
        return rootPane;
    }

    private void goToShopMenu() {
        Main.getStage().setScene(new Scene(new ShopMenu().getPane()));
        Main.getStage().setFullScreen(true);
    }

    private void doPreGameProcess(boolean isPreGame) {
        if (!isPreGame)
            return;
        nextButton = makeNextButton();
        nextButton.setOnMouseClicked(event -> {
            initPlayers();
        });
        clearButton = makeClearButton();
        textureChoiceBox = makeTextureChoiceBox();

        rootPane.getChildren().addAll(nextButton, clearButton, textureChoiceBox);
    }

    private void addNextTurnButton(boolean isPreGame) {
        if (isPreGame)
            return;
        Button nextTurn = makeNextButton();
        nextTurn.setText("Next Turn");
        nextTurn.setOnMouseClicked(mouseEvent -> {
            nextTurn();
            nextTurn.setFocusTraversable(false);
            mouseEvent.consume();
        });
        rootPane.getChildren().add(nextTurn);
    }

    private ChoiceBox<String> makeTextureChoiceBox() {
        ChoiceBox<String> textureChoiceBox = new ChoiceBox<>();
        textureChoiceBox.setLayoutX(100);
        textureChoiceBox.setLayoutY(50);
        textureChoiceBox.setPrefSize(100, 50);
        textureChoiceBox.setBackground(new Background(new BackgroundFill(Color.rgb(175, 85, 85, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
        for (Texture texture : Texture.values()) {
            textureChoiceBox.getItems().add(texture.getName());
        }
        textureChoiceBox.setValue("earth");
        textureChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedTiles.isEmpty())
                return;
            clearSelectedCells(false);
            setTextureForSelectedTiles(newValue);
        });
        return textureChoiceBox;
    }

    private synchronized void setTextureForSelectedTiles(String textureName) {
        ArrayList<CellWrapper> selectedTiles = this.selectedTiles;
        for (CellWrapper cellWrapper : selectedTiles) {
            GameMenuController.setTexture(cellWrapper.getSquareX(), cellWrapper.getSquareY(), textureName);
        }
        GameMenuController.updateCellWrappers(selectedTiles);
        renderMapFromScrollPosition(GameMenuController.getCurrentGame().getMap(), rootPane);
    }

    private Button makeClearButton() {
        Button clear = new Button("Clear");
        clear.setLayoutX(250);
        clear.setLayoutY(50);
        clear.setPrefSize(100, 50);
        clear.setBackground(new Background(new BackgroundFill(Color.rgb(175, 85, 85, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
        clear.setOnMouseClicked(event -> {
            clearSelectedCells(true);
        });
        return clear;
    }

    private synchronized void clearSelectedCells(boolean clearTexturesAndUpdate) {
        ArrayList<CellWrapper> selectedTiles = this.selectedTiles;
        for (CellWrapper cellWrapper : selectedTiles) {
            if (cellWrapper.getObject() != null) {
                GameMenuController.clearBlock(cellWrapper.getSquareX(), cellWrapper.getSquareY());
            }
        }
        if (clearTexturesAndUpdate) {
            setTextureForSelectedTiles("earth");
            GameMenuController.updateCellWrappers(selectedTiles);
        }
        renderMapFromScrollPosition(GameMenuController.getCurrentGame().getMap(), rootPane);
    }

    private Button makeNextButton() {
        Button next = new Button("Next");
        next.setLayoutX(1400);
        next.setLayoutY(50);
        next.setPrefSize(100, 50);
        next.setBackground(new Background(new BackgroundFill(Color.rgb(175, 85, 85, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
        return next;
    }

    private void initPlayers() {
        if (players == null)
            throw new RuntimeException("players is null");
        rootPane.getChildren().removeAll(nextButton, clearButton, textureChoiceBox);
        bottomHBox.getChildren().clear();
        bottomHBox.setVisible(false);
        ArrayList<Colors> availableColors = new ArrayList<>(EnumSet.allOf(Colors.class));
        initAPlayer(players.get(0), availableColors);
        // TODO start game
    }

    private synchronized void initAPlayer(User player, ArrayList<Colors> availableColors) {
        currentPlayer = player;
        Text text = new Text("Player " + (player.getUsername()) + " choose your color");
        text.getStyleClass().add("text-title");
        text.setLayoutX(200);
        text.setLayoutY(200);
        Text[] colors = new Text[availableColors.size()];
        for (int i = 0; i < availableColors.size(); i++) {
            colors[i] = new Text(availableColors.get(i).toString());
            colors[i].setFill(availableColors.get(i).getPaint());
            colors[i].setLayoutX(200);
            colors[i].setLayoutY(250 + i * 40);
            colors[i].setFont(Font.font("Verdana", FontWeight.BOLD, 30));
            colors[i].getStyleClass().add("colors");
            int tmp = i;
            colors[i].setOnMouseClicked(event -> {
                GameMenuController.addPlayerToGame(player, availableColors.get(tmp));
                availableColors.remove(availableColors.get(tmp));
                rootPane.getChildren().removeAll(colors);
                rootPane.getChildren().remove(text);
                initPlayersSmallStone(player, availableColors);
                event.consume();
            });
        }
        rootPane.getChildren().addAll(colors);
        rootPane.getChildren().add(text);
        text.requestFocus();
    }

    private void addInitialMaterial() {
        for (Government government : GameMenuController.getCurrentGame().getGovernments()) {
            government.increaseItem(Material.GOLD, 50);
            government.increaseItem(Material.STONE, 50);
            government.increaseItem(Material.WOOD, 50);
            government.increaseItem(Material.IRON, 50);
        }
    }

    private void initPlayersSmallStone(User player, ArrayList<Colors> availableColors) {
        Graphics.showMessagePopup("choose where you want to drop your small stone gate.");
        rootPane.setOnMouseClicked(event -> {
            Point2D currentPoint = new Point2D(event.getX(), event.getY());
            for (Node node : gridPane.getChildren()) {
                if (!(node instanceof CellWrapper tile))
                    continue;
                if (tile.getBoundsInParent().contains(currentPoint)) {
                    if (tile.dropSmallStone(player)) {
                        rootPane.setOnMouseClicked(event2 -> {
                        });
                        renderMapFromScrollPosition(GameMenuController.getCurrentGame().getMap(), rootPane);
                        initPlayersBuildings(player, availableColors);
                    }
                    break;
                }
            }
            event.consume();
        });
        rootPane.requestFocus();
    }

    private void initPlayersBuildings(User player, ArrayList<Colors> availableColors) {
        Graphics.showMessagePopup("Now you can drop building with no cost.");
        bottomHBox = makeDefaultBottomMenuHBox(false);
        rootPane.getChildren().add(bottomHBox);
        bottomHBox.setVisible(true);
        Button nextButton = makeNextButton();
        nextButton.setOnMouseClicked(mouseEvent -> {
            if (players.indexOf(player) + 1 < players.size())
                initAPlayer(players.get(players.indexOf(player) + 1), availableColors);
            else {
                GameMenuController.saveGame();
                addInitialMaterial();
                Main.setScene(new GameMenu().getPane(false, null));
                Main.getStage().setFullScreen(true);
            }
            rootPane.getChildren().remove(nextButton);
        });
        rootPane.getChildren().add(nextButton);
    }

    private void makeEnterGovernmentButton() {
        Circle enterGovernmentThings = new Circle(10);
        enterGovernmentThings.setFill(Color.WHITE);
        ImageView image = new ImageView(new Image(getClass().getResource("/images/buttons/government.png").toExternalForm()));

        enterGovernmentThings.setFill(new ImagePattern(image.getImage()));
        enterGovernmentThings.setTranslateX(430);
        enterGovernmentThings.setTranslateY(120);
        enterGovernmentThings.setOnMouseClicked(event -> {
            makeGovernmentVBox();
        });
        bottomHBox.getChildren().add(enterGovernmentThings);
    }

    private void makeGovernmentVBox() {
        Text foodRateText = new Text("Food Rate:");
        Slider foodRateSlider = new Slider(-2, 2, GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFoodRate());
        foodRateSlider.setStyle("-fx-background-color: linear-gradient(to right, red 0%, yellow 50%, green 100%);");

        Text taxRateText = new Text("Tax Rate:");
        Slider taxRateSlider = new Slider(-3, 8, GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getTaxRate());
        taxRateSlider.setStyle("-fx-background-color: linear-gradient(to right, green 0%, yellow 50%, red 100%);");

        Text fearRateText = new Text("Fear Rate:");
        Slider fearRateSlider = new Slider(-5, 5, GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFearRate());
        fearRateSlider.setStyle("-fx-background-color: linear-gradient(to right, green 0%, yellow 50%, red 100%);");

        fixTexts(foodRateText, taxRateText, fearRateText);
        fixSliders(foodRateSlider, taxRateSlider, fearRateSlider);

        ImageView done = new ImageView(new Image(getClass().getResource("/images/others/check.jpg").toExternalForm()));
        done.setFitWidth(20);
        done.setFitHeight(20);
        done.setOnMouseClicked(event -> {
            GameMenuController.setFoodRate((int) foodRateSlider.getValue());
            GameMenuController.setTaxRate((int) taxRateSlider.getValue());
            GameMenuController.setFearRate((int) fearRateSlider.getValue());
            rootPane.getChildren().remove(governmentVBox);
        });

        governmentVBox = new VBox(foodRateText, foodRateSlider, taxRateText, taxRateSlider, fearRateText, fearRateSlider, done);
        governmentVBox.setTranslateX(0);
        governmentVBox.setTranslateY(100);
        governmentVBox.setSpacing(20);
        governmentVBox.setPrefWidth(400);
        governmentVBox.setPadding(new Insets(10));
        governmentVBox.setAlignment(Pos.CENTER);
        governmentVBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        rootPane.getChildren().add(governmentVBox);
    }

    private void fixTexts(Text... texts) {
        for (Text text : texts) {
            text.setFont(new Font("Arial", 20));
        }
    }

    private void fixSliders(Slider... sliders) {
        for (Slider slider : sliders) {
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setBlockIncrement(1);
            slider.setMajorTickUnit(1);
            slider.setMinorTickCount(0);
            slider.setSnapToTicks(true);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
        }
    }

    private void makePopularityBox() {
        popularityText = new Text(GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getPopularity() + "");
        popularityText.setFont(new Font("Arial", 20));
        popularityText.setTranslateY(120);
        popularityText.setTranslateX(400);

        Circle circle = new Circle(20);
        ImageView image = new ImageView(new Image(getClass().getResource("/images/buttons/info.png").toExternalForm()));
        circle.setFill(new ImagePattern(image.getImage()));
        circle.setTranslateX(370);
        circle.setTranslateY(140);
        circle.setOnMouseClicked(event -> {
            popularityFactorsVBox();
        });

        bottomHBox.getChildren().addAll(popularityText, circle);
    }

    private void popularityFactorsVBox() {
        VBox mainVBox = new VBox(makePopularityHBox("Food Rate"), makePopularityHBox("Tax Rate"),
            makePopularityHBox("Fear Rate"));
        mainVBox.setSpacing(40);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));
        makePopularityPopup(mainVBox);
    }

    private void makePopularityPopup(VBox vBox) {
        Popup popup = new Popup();
        popup.getContent().add(vBox);
        popup.setAutoHide(true);
        vBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        vBox.setPrefWidth(300);
        popup.show(Main.getStage());
    }

    private HBox makePopularityHBox(String parameter) {
        ImageView happyFace = new ImageView(new Image(getClass().getResource("/images/others/happy.png").toExternalForm()));
        ImageView normalFace = new ImageView(new Image(getClass().getResource("/images/others/normal.png").toExternalForm()));
        ImageView sadFace = new ImageView(new Image(getClass().getResource("/images/others/sad.png").toExternalForm()));
        happyFace.setFitWidth(30);
        happyFace.setFitHeight(30);
        normalFace.setFitWidth(30);
        normalFace.setFitHeight(30);
        sadFace.setFitWidth(30);
        sadFace.setFitHeight(30);

        int rate = 0;
        ImageView imageView;
        int goodThing = 0;

        switch (parameter) {
            case "Food Rate" -> {
                rate = GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFoodRate();
                goodThing = 1;
            }
            case "Tax Rate" -> {
                rate = GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getTaxRate();
                goodThing = -1;
            }
            case "Fear Rate" -> {
                rate = GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFearRate();
                goodThing = -1;
            }
        }

        if (rate * goodThing > 0) imageView = happyFace;
        else if (rate * goodThing == 0) imageView = normalFace;
        else imageView = sadFace;

        Text text = new Text(rate + "");
        text.setFont(new Font("Arial", 20));
        text.setFill(Color.BROWN);

        Text parameterText = new Text(parameter);
        parameterText.setFont(new Font("Arial", 20));
        parameterText.setFill(Color.BROWN);

        HBox hBox = new HBox(text, imageView, parameterText);
        hBox.setSpacing(30);
        return hBox;
    }

    private void paste() {
        if (selectedTiles.size() != 1)
            return;

        CellWrapper cellWrapper = selectedTiles.get(0);
        if (cellWrapper.getObject() == null) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String content[] = clipboard.getString().split("/");
            if (content[0] != null) {
                cellWrapper.dropObject(content[0], clipboard.getImage(), isPreGame, UserController.findUserWithUsername(content[1]));
            }
        }
    }

    private void copy() {
        if (selectedTiles.size() != 1)
            return;

        CellWrapper cellWrapper = selectedTiles.get(0);
        if (cellWrapper.getObject() instanceof Building building) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(building.getName() + "/" + building.getOwner());
            clipboard.setContent(content);
        }
    }

    public HBox makeBaseOfBottomMenu() {
        HBox hBox = new HBox();
        hBox.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/game-images/bottomMenu.png"))));
        hBox.setTranslateY(670);
        hBox.setPrefWidth(1550);
        hBox.setPrefHeight(200);
        return hBox;
    }

    private HBox makeDefaultBottomMenuHBox(boolean addTreesAndRocks) {
        bottomHBox = makeBaseOfBottomMenu();
        HBox itemsHBox = new HBox();
        itemsHBox.setMaxHeight(120);
        itemsHBox.setTranslateY(22);
        itemsHBox.setSpacing(10);

        if (!addTreesAndRocks)
            addBuildingsToHBox(itemsHBox);
        else
            addTreesAndRocksToHBox(itemsHBox);

        itemsScrollPane = getItemsScrollPane(itemsHBox);
        bottomHBox.getChildren().add(itemsScrollPane);

        return bottomHBox;
    }

    private void addTreesAndRocksToHBox(HBox itemsHBox) {
        for (TreeType treeType : TreeType.values()) {
            ImageView treeImage = treeType.getImageView();
            Tooltip tooltip = new Tooltip(treeType.getTreeName());
            tooltip.setShowDelay(Duration.ZERO);
            Tooltip.install(treeImage, tooltip);

            itemsHBox.getChildren().add(treeImage);
            treeImage.setOnMouseClicked(event -> {
                rootPane.requestFocus();
            });
            treeImage.setOnMouseDragged(event -> {
                rootPane.requestFocus();
            });
            handleDropItems(treeType.getTreeName(), treeImage,TILE_SIZE);
        }
        for (Directions directions : Directions.values()) {
            ImageView rockImage = new Rock(directions).getImageView();
            Tooltip tooltip = new Tooltip("Rock - " + directions.name().toLowerCase());
            tooltip.setShowDelay(Duration.ZERO);
            Tooltip.install(rockImage, tooltip);

            itemsHBox.getChildren().add(rockImage);
            rockImage.setOnMouseClicked(event -> {
                rootPane.requestFocus();
            });
            rockImage.setOnMouseDragged(event -> {
                rootPane.requestFocus();
            });
            handleDropItems(directions.name().toLowerCase(), rockImage,TILE_SIZE);
        }
    }

    private void addBuildingsToHBox(HBox buildingsHBox) {
        ImageView buildingImage;
        for (Building building : BuildingFactory.getAllBuildings()) {
            if ((buildingImage = building.getBuildingImage()) != null) {
                Tooltip tooltip = new Tooltip(building.getName());
                tooltip.setShowDelay(Duration.ZERO);
                Tooltip.install(buildingImage, tooltip);

                buildingsHBox.getChildren().add(buildingImage);
                buildingImage.setOnMouseClicked(event -> {
                    rootPane.requestFocus();
                });
                buildingImage.setOnMouseDragged(event -> {
                    rootPane.requestFocus();
                });
                handleDropItems(building.getName(), buildingImage,TILE_SIZE);
            }
        }
    }

    private ScrollPane getItemsScrollPane(HBox itemsHBox) {
        ScrollPane itemsScrollPane = new ScrollPane(itemsHBox);
        itemsScrollPane.setPrefWidth(550);
        itemsScrollPane.setMaxHeight(130);
        itemsScrollPane.setTranslateX(340);
        itemsScrollPane.setTranslateY(80);
        itemsScrollPane.setOnMouseClicked(event -> {
            rootPane.requestFocus();
        });
        itemsScrollPane.setOnMouseDragged(event -> {
            rootPane.requestFocus();
        });
        rootPane.requestFocus();
        return itemsScrollPane;
    }

    private void handleDropItems(String itemName, ImageView itemImage, int size) {
        itemImage.setOnDragDetected(event -> {
            Dragboard db = itemImage.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemName);
            Image image = itemImage.getImage();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.GREEN);
            content.putImage(imageView.snapshot(params, null));
            db.setContent(content);
            event.consume();
        });

        itemImage.setOnDragDone(Event::consume);
    }

    private void handleMousePressed(MouseEvent event, Pane rootPane) {
        if (!event.isPrimaryButtonDown())
            return;

        if (selectedTiles.size() > 1) {
            clearSelection();
        }
        selectionStart = new Point2D(event.getX(), event.getY());
    }

    private void handleMouseDragged(MouseEvent event, Pane scrollPane) {
        if (!event.isPrimaryButtonDown())
            return;

        Point2D currentPoint = new Point2D(event.getX(), event.getY());

        double minX = Math.min(selectionStart.getX(), currentPoint.getX());
        double minY = Math.min(selectionStart.getY(), currentPoint.getY());
        double maxX = Math.max(selectionStart.getX(), currentPoint.getX());
        double maxY = Math.max(selectionStart.getY(), currentPoint.getY());

        for (Node node : gridPane.getChildren()) {
            if (!(node instanceof CellWrapper tile))
                continue;
            if (tile.getBoundsInParent().intersects(minX, minY, maxX - minX, maxY - minY)) {
                selectTile(tile);
            } else {
                unselectTile(tile);
            }
        }
    }

    private void handleMouseClicked(MouseEvent event, Pane scrollPane) {
        if (selectedTiles.size() > 1) {
            handleSelection();
            return;
        }

        if (selectedTiles.size() == 1) {
            clearSelection();
            return;
        }
        if (selectedTiles.size() > 0) {
            return;
        }

        Point2D currentPoint = new Point2D(event.getX(), event.getY());
        for (Node node : gridPane.getChildren()) {
            if (!(node instanceof CellWrapper tile))
                continue;
            if (tile.getBoundsInParent().contains(currentPoint)) {
                selectTile(tile);
                handleSelection();
                break;
            }
        }
    }

    private void selectTile(CellWrapper tile) {
        tile.setSelected(true);
        if (!selectedTiles.contains(tile))
            selectedTiles.add(tile);
    }

    private void unselectTile(CellWrapper tile) {
        tile.setSelected(false);
        selectedTiles.remove(tile);
    }

    private void clearSelection() {
        for (CellWrapper tile : selectedTiles) {
            tile.setSelected(false);
        }
        selectedTiles.clear();
    }

    private void handleSelection() {
        if (selectedTiles.size() == 1) {
            handleSingleSelection(selectedTiles.get(0));
        } else {
            // TODO
        }
    }

    private void handleSingleSelection(CellWrapper cellWrapper) {
        Cell cell = cellWrapper.getCell();
        MapObject mapObject = cell.getObject();
        if (!isPreGame && mapObject != null && mapObject instanceof Building building) {
            if (building.getName().equalsIgnoreCase("shop")) {
                goToShopMenu();
            } else if (building.getName().matches("Barrack|Mercenary Post|Engineer Guild")) {
                addUnitsHBox(building.getName());
                showBuildingsBar = false;
            } else if (!showBuildingsBar) {
                HBox hBox = new HBox();
                hBox.setTranslateY(22);
                hBox.setSpacing(10);
                addBuildingsToHBox(hBox);
                itemsScrollPane.setContent(hBox);
            }
        } else if (!showBuildingsBar) {
            HBox hBox = new HBox();
            hBox.setTranslateY(22);
            hBox.setSpacing(10);
            addBuildingsToHBox(hBox);
            itemsScrollPane.setContent(hBox);
        }
    }

    private void addUnitsHBox(String type) {
        ArrayList<Unit> units = MakeUnitInstances.getUnitsBasedOfType(type);
        HBox unitsHBox = new HBox();
        for (Unit unit : units) {
            unitsHBox.getChildren().add(getUnitVBox(unit));
        }
        unitsHBox.setSpacing(10);
        itemsScrollPane.setContent(unitsHBox);
    }

    private VBox getUnitVBox(Unit unit) {
        ImageView unitImage = unit.getImage();
        unitImage.setFitHeight(50);
        unitImage.setFitWidth(50);
        Tooltip tooltip = new Tooltip(unit.getName());
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(unitImage,tooltip);
        unitImage.setOnMouseClicked(event -> {
            GameMenuMessages message = GameMenuController.makeUnit(unit,1,
                GameMenuController.getCurrentGame().getCurrentPlayer());
            if (!message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) Graphics.showMessagePopup(message.getMessage());
            addUnitsHBox(unit.getType().getWhereCanBeTrained());
        });

        Slider unitsToUse = new Slider(0,UnitMenuController.getAmountOfUnitLeft(unit),0);
        fixSliders(unitsToUse);

        Text amountOfUnitsLeft = new Text( "All: " + UnitMenuController.getAmountOfUnitLeft(unit));

        VBox vBox = new VBox(unitImage,unitsToUse,amountOfUnitsLeft);
        vBox.setSpacing(3);
        vBox.setAlignment(Pos.CENTER);

        unitImage.setOnDragDetected(event -> {
            UnitMenuController.getDroppingUnitsCount().put(unit.getName(), (int) unitsToUse.getValue());
            Dragboard db = unitImage.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(unit.getName());
            Image image = unitImage.getImage();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.GREEN);
            content.putImage(imageView.snapshot(params, null));
            db.setContent(content);
            event.consume();
        });

        unitImage.setOnDragDone(event -> addUnitsHBox(unit.getType().getWhereCanBeTrained()));

        return vBox;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void run(Scanner scanner) {
        while (true) {
            System.out.print("Do you want to start a new game? (yes or no): ");
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("yes")) {
                runPreGameMenu(scanner);
                break;
            } else if (parser.beginsWith("no")) {
                loadGame(scanner);
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GameMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public void runPreGameMenu(Scanner scanner) {
        int numberOfTurns = setNumberOfTurns(scanner);
        int mapWidth = setMapWidth(scanner);
        int mapHeight = setMapHeight(scanner);
        GameMenuController.setCurrentGame(new Game(new ArrayList<>(), numberOfTurns, new Map(mapWidth, mapHeight)));
        System.out.println("Now you can initialize map. type next to continue.");
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("droprock")) {
                dropRock(parser);
            } else if (parser.beginsWith("droptree")) {
                dropTree(parser);
            } else if (parser.beginsWith("settexture")) {
                setTexture(parser);
            } else if (parser.beginsWith("clear")) {
                clearBlock(parser);
            } else if (parser.beginsWith("exit")) {
                System.out.println("You came back to the main menu!");
                break;
            } else if (parser.beginsWith("next")) {
                initGovernments(scanner);
                GameMenuController.saveGame();
                runGameMenu(scanner);
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GameMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public void runGameMenu(Scanner scanner) {
        printNowPlaying();
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("drop building")) {
                dropBuilding(parser, true);
            } else if (parser.beginsWith("select building")) {
                selectBuilding(parser, scanner);
            } else if (parser.beginsWith("show map")) {
                showMap(parser, scanner);
            } else if (parser.beginsWith("select unit")) {
                selectUnit(parser, scanner);
            } else if (parser.beginsWith("show popularity factors")) {
                showPopularityFactors();
            } else if (parser.beginsWith("show popularity")) {
                showPopularity();
            } else if (parser.beginsWith("show food list")) {
                showFoodList();
            } else if (parser.beginsWith("food rate show")) {
                showFoodRate();
            } else if (parser.beginsWith("food rate")) {
                setFoodRate(parser);
            } else if (parser.beginsWith("tax rate show")) {
                showTaxRate();
            } else if (parser.beginsWith("tax rate")) {
                setTaxRate(parser);
            } else if (parser.beginsWith("fear rate")) {
                setFearRate(parser);
            } else if (parser.beginsWith("save")) {
                GameMenuController.saveGame();
                System.out.println("Game saved successfully!");
            } else if (parser.beginsWith("next")) {
                nextTurn();
            } else if (parser.beginsWith("turns")) {
                System.out.println("Turns passed: " + GameMenuController.getCurrentGame().getTurnCounter());
            } else if (parser.beginsWith("exit") || isGameOver) {
                System.out.println("You came back to the main menu!");
                break;
            } else if (parser.beginsWith("show current menu")) {
                System.out.println("You are at GameMenu");
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void initGovernments(Scanner scanner) {
        System.out.print("Enter the number of governments: ");
        int numberOfGovernments;
        while (true) {
            numberOfGovernments = Utils.getValidInt(scanner);
            if (numberOfGovernments <= 8)
                break;
            System.out.println("The number of governments should be at most 8!");
        }

        int[] colors = new int[8];
        for (int i = 0; i < numberOfGovernments; i++) {
            User player;
            if (i == 0) {
                player = UserController.getCurrentUser();
            } else {
                while (true) {
                    System.out.print("Enter the username of player you want to add: ");
                    String username = scanner.nextLine().trim();
                    if (!UserController.userWithUsernameExists(username))
                        System.out.println("user with this username doesn't exist!");
                    else if (GameMenuController.getCurrentGame().getPlayerByUsername(username) != null)
                        System.out.println("player is already in game!");
                    else {
                        player = UserController.findUserWithUsername(username);
                        break;
                    }
                }
            }
            Colors color = pickColor(colors, scanner);
            GameMenuController.addPlayerToGame(player, color);
            dropSmallStoneGate(player, scanner);
        }
    }

    private void dropSmallStoneGate(User player, Scanner scanner) {
        System.out.println("select coordinates for this user's small stone gate\nthe format should be \"-x <x> -y <y>\"");
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            GameMenuMessages message = GameMenuController.dropSmallStoneGate(player, parser);
            System.out.println(message.getMessage());
            if (message == GameMenuMessages.DONE_SUCCESSFULLY)
                break;
        }
    }

    private Colors pickColor(int[] colors, Scanner scanner) {
        System.out.println("Pick a color for this player: ");
        System.out.println("Blue: 1 | Red: 2 | Yellow: 3 | Green: 4 | Black: 5 | White: 6 | Purple: 7 | Pink: 8");
        while (true) {
            int input = Utils.getValidInt(scanner);
            if (input > 8)
                System.out.println("Invalid input! number should be at most 8!");
            else if (colors[input - 1] != 0)
                System.out.println("This color is already picked!");
            else {
                colors[input - 1] = 1;
                return Colors.values()[input - 1];
            }
        }
    }

    private void loadGame(Scanner scanner) {
        if (GameMenuController.loadGame()) {
            System.out.println("game loaded. you can play now.");
            runGameMenu(scanner);
        } else {
            System.out.println("no game is saved! a new game is started.");
            runPreGameMenu(scanner);
        }
    }

    private int setMapHeight(Scanner scanner) {
        System.out.print("Enter the height of the map: ");
        return Utils.getValidInt(scanner);
    }

    private int setMapWidth(Scanner scanner) {
        System.out.print("Enter the width of the map: ");
        return Utils.getValidInt(scanner);
    }

    private int setNumberOfTurns(Scanner scanner) {
        System.out.print("Enter the number of turns: ");
        return Utils.getValidInt(scanner);
    }

    void showMap(Parser parser, Scanner scanner) {
        if (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y"))) {
            System.out.println("Please import numbers!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));

        GameMenuMessages message = GameMenuController.showMap(x, y);
        if (message.equals(GameMenuMessages.INVALID_PLACE))
            System.out.println("The numbers are invalid!");
        if (message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) {
            System.out.println(GameMenuController.getCurrentGame().getMap().printMiniMap(x, y));
            new MapMenu(x, y).run(scanner);
        }
    }

    void showPopularityFactors() {
        Government gov = GameMenuController.getCurrentGame().getCurrentPlayersGovernment();
        showFoodRate();
        System.out.println("Food type count: " + gov.getFoodStock().size());
        System.out.println(
            "Fear rate: " + gov.getFearRate());
        showTaxRate();
    }

    void showPopularity() {
        System.out.println(
            "Popularity: " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getPopularity());
    }

    void showFoodList() {
        System.out.println("Foods:");
        for (Food food : GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFoodStock()) {
            System.out.println(food.name()); // TODO: use a more human-friendly name
        }
    }

    void setFoodRate(Parser parser) {
        String r = parser.get("r");
        if (!Utils.isInteger(r)) {
            System.out.println("Invalid number!");
            return;
        }
        GameMenuMessages message = GameMenuController.setFoodRate(Integer.parseInt(r));
        System.out.println(message.getMessage());
    }

    void showFoodRate() {
        System.out.println(
            "Food rate: " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getFoodRate());
    }

    void setTaxRate(Parser parser) {
        String r = parser.get("r");
        if (!Utils.isInteger(r)) {
            System.out.println("Invalid number!");
            return;
        }
        GameMenuMessages message = GameMenuController.setTaxRate(Integer.parseInt(r));
        System.out.println(message.getMessage());
    }

    void showTaxRate() {
        System.out
            .println("Tax rate: " + GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getTaxRate());
    }

    void setFearRate(Parser parser) {
        String r = parser.get("r");
        if (!Utils.isInteger(r)) {
            System.out.println("Invalid number!");
            return;
        }
        GameMenuMessages message = GameMenuController.setFearRate(Integer.parseInt(r));
        System.out.println(message.getMessage());
    }

    void dropBuilding(User player, Parser parser, boolean useMaterials) {
        if (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y"))) {
            System.out.println("Invalid x or y");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        String type = parser.get("type");
        GameMenuMessages message = GameMenuController.dropBuilding(x, y, player, type, useMaterials);
        System.out.println(message.getMessage());
    }

    void dropBuilding(Parser parser, boolean useMaterials) {
        dropBuilding(GameMenuController.getCurrentGame().getCurrentPlayer(), parser, useMaterials);
    }

    void selectBuilding(Parser parser, Scanner scanner) {
        String[] strings = {parser.get("x"), parser.get("y")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import numbers in x and y field!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.selectBuilding(x, y);
        if (message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) {
            System.out.println("You entered this Building: \"" + BuildingMenuController.getSelectedBuilding().getName() + "\" menu!");
            new BuildingMenu(BuildingMenuController.getSelectedBuilding()).run(scanner);
        } else System.out.println(message.getMessage());
    }

    void selectUnit(Parser parser, Scanner scanner) {
        String[] strings = {parser.get("y"), parser.get("x")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import numbers!");
            return;
        }
        String type = parser.get("t");
        if (type == null || type.equals("")) {
            System.out.println("Unit type is required");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.selectUnit(x, y, type);
        if (message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) {
            System.out.println("You entered unit menu!");
            new UnitMenu().run(scanner);
        } else System.out.println(message.getMessage());
    }

    void setTexture(Parser parser) {
        GameMenuMessages message;
        if (parser.get("x1") != null) {
            String[] strings = {parser.get("x1"), parser.get("x2"), parser.get("y1"), parser.get("y2")};
            if (!Utils.areIntegers(strings)) {
                System.out.println("Please import the numbers!");
                return;
            }
            message = GameMenuController.setTexture
                (Integer.parseInt(parser.get("x1")), Integer.parseInt(parser.get("y1"))
                    , Integer.parseInt(parser.get("x2")), Integer.parseInt(parser.get("y2"))
                    , parser.get("t"));
        } else {
            String[] strings = {parser.get("x"), parser.get("y")};
            if (!Utils.areIntegers(strings)) {
                System.out.println("Please import the numbers!");
                return;
            }
            message = GameMenuController.setTexture
                (Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")), parser.get("t"));
        }
        System.out.println(message.getMessage());
    }

    void clearBlock(Parser parser) {
        GameMenuMessages messages = GameMenuController.clearBlock(Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")));
        System.out.println(messages.getMessage());
    }

    void dropRock(Parser parser) {
        String[] strings = {parser.get("x"), parser.get("y")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import numbers!");
            return;
        }
        int y = Integer.parseInt(parser.get("y"));
        int x = Integer.parseInt(parser.get("x"));
        GameMenuMessages message = GameMenuController.dropRock(x, y, parser.get("d"));
        System.out.println(message.getMessage());
    }

    void dropTree(Parser parser) {
        String[] strings = {parser.get("x"), parser.get("y")};
        if (!Utils.areIntegers(strings)) {
            System.out.println("Please import numbers!");
            return;
        }
        int x = Integer.parseInt(parser.get("x"));
        int y = Integer.parseInt(parser.get("y"));
        GameMenuMessages message = GameMenuController.dropTree(x, y, parser.get("t"));
        System.out.println(message.getMessage());
    }

    void nextTurn() {
        GameMenuController.nextTurn(this);
    }

    public void printNowPlaying() {
        Graphics.showMessagePopup("Now playing: @" + GameMenuController.getCurrentGame().getCurrentPlayer().getUsername());
    }

    public void endGame(User winner) {
        isGameOver = true;
        Main.setScene(new MainMenu().getPane());
        Graphics.showMessagePopup("Game over!\nUser " + winner.getUsername() + " is the winner of the game!");
    }
}
