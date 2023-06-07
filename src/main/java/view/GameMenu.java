package view;

import controllers.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import models.*;
import utils.Parser;
import utils.Utils;
import view.enums.GameMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMenu {
    private boolean isGameOver = false;

    private ArrayList<Rectangle> selectedTiles = new ArrayList<>();
    private Point2D selectionStart;

    private final int TILE_SIZE = 50;
    private int scrollX = 0, scrollY = 0;

    private void renderMap(GridPane gridPane, Map map, int fromRow, int toRow, int fromCol, int toCol, int offsetX,
            int offsetY) {
        gridPane.getChildren().clear();

        for (int row = fromRow; row <= toRow && row < map.getHeight(); row++) {
            for (int col = fromCol; col <= toCol && col < map.getWidth(); col++) {
                Cell cell = map.findCellWithXAndY(col, row);

                Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                rect.setFill(cell.getTexture().getPaint());
                rect.setStrokeType(StrokeType.INSIDE);
                rect.setStroke(Color.TRANSPARENT);

                Tooltip tooltip = new Tooltip();
                tooltip.setShowDelay(Duration.ZERO);
                tooltip.setText("x: " + col + ", y: " + row);
                Tooltip.install(rect, tooltip);

                GridPane.setColumnIndex(rect, col - fromCol);
                GridPane.setRowIndex(rect, row - fromRow);

                rect.setTranslateX(-offsetX);
                rect.setTranslateY(-offsetY);

                gridPane.getChildren().add(rect);
            }
        }
    }

    private void renderMapFromScrollPosition(Map map, GridPane gridPane, Pane rootPane) {
        int offsetX = scrollX % TILE_SIZE;
        int offsetY = scrollY % TILE_SIZE;

        int windowWidth = (int) rootPane.getScene().getWindow().getWidth();
        int windowHeight = (int) rootPane.getScene().getWindow().getHeight();

        int fromRow = (int) (scrollY / gridPane.getHeight() * map.getHeight());
        int toRow = fromRow + (int) (windowHeight / TILE_SIZE);
        int fromCol = (int) (scrollX / gridPane.getWidth() * map.getWidth());
        int toCol = fromCol + (int) (windowWidth / TILE_SIZE);

        renderMap(gridPane, map, fromRow, toRow, fromCol, toCol, offsetX, offsetY);
    }

    public Pane getPane() {
        Game game = GameMenuController.getCurrentGame();
        if (game == null) {
            // TODO: remove this
            game = new Game(new ArrayList<>(), 0, new Map(50, 50));
        }
        Map map = game.getMap();

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(TILE_SIZE * map.getWidth(), TILE_SIZE * map.getHeight());

        Pane rootPane = new Pane(gridPane);
        gridPane.setLayoutX(0);
        gridPane.setLayoutY(0);

        gridPane.setOnMousePressed(event -> {
            handleMousePressed(event, gridPane, rootPane);
        });
        gridPane.setOnMouseDragged(event -> {
            handleMouseDragged(event, gridPane, rootPane);
        });

        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();

        rootPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                scrollX = Math.min(scrollX + TILE_SIZE / 2, map.getWidth() * TILE_SIZE - (int) rootPane.getWidth());
                renderMapFromScrollPosition(map, gridPane, rootPane);
            } else if (event.getCode() == KeyCode.LEFT) {
                scrollX = Math.max(scrollX - TILE_SIZE / 2, 0);
                renderMapFromScrollPosition(map, gridPane, rootPane);
            } else if (event.getCode() == KeyCode.UP) {
                scrollY = Math.max(scrollY - TILE_SIZE / 2, 0);
                renderMapFromScrollPosition(map, gridPane, rootPane);
            } else if (event.getCode() == KeyCode.DOWN) {
                scrollY = Math.min(scrollY + TILE_SIZE / 2, map.getHeight() * TILE_SIZE - (int) rootPane.getHeight());
                renderMapFromScrollPosition(map, gridPane, rootPane);
            }
            // TODO: implement shortcuts
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
                                renderMapFromScrollPosition(map, gridPane, rootPane);
                            }
                        });
                        newWindow.heightProperty().addListener((observable1, oldValue1, newValue1) -> {
                            renderMapFromScrollPosition(map, gridPane, rootPane);
                        });
                    }
                });
            }
        });

        return rootPane;
    }

    private void handleMousePressed(MouseEvent event, GridPane gridPane, Pane rootPane) {
        if (!event.isPrimaryButtonDown())
            return;
        for (Rectangle tile : selectedTiles) {
            tile.setStroke(Color.TRANSPARENT);
        }
        selectedTiles.clear();
        selectionStart = new Point2D(event.getX(), event.getY());
    }

    private void handleMouseDragged(MouseEvent event, GridPane gridPane, Pane scrollPane) {
        if (!event.isPrimaryButtonDown())
            return;

        Point2D currentPoint = new Point2D(event.getX(), event.getY());

        double minX = Math.min(selectionStart.getX(), currentPoint.getX());
        double minY = Math.min(selectionStart.getY(), currentPoint.getY());
        double maxX = Math.max(selectionStart.getX(), currentPoint.getX());
        double maxY = Math.max(selectionStart.getY(), currentPoint.getY());

        for (Node node : gridPane.getChildren()) {
            if (!(node instanceof Rectangle))
                continue;
            Rectangle tile = (Rectangle) node;
            if (tile.getBoundsInParent().intersects(minX, minY, maxX - minX, maxY - minY)) {
                tile.setStroke(Color.WHITE);
                selectedTiles.add(tile);
            } else {
                tile.setStroke(Color.TRANSPARENT);
                selectedTiles.remove(tile);
            }
        }
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
            } else if (parser.beginsWith("enter trade menu")) {
                new TradeMenu().run(scanner);
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
            dropBuildingAndUnit(player, scanner);
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

    private void dropBuildingAndUnit(User player, Scanner scanner) {
        System.out.println("Now you may drop buildings and units with no cost. type next to continue.");
        while (true) {
            Parser parser = new Parser(scanner.nextLine());
            if (parser.beginsWith("drop building")) {
                dropBuilding(player, parser, false);
            } else if (parser.beginsWith("drop unit")) {
                dropUnit(player, parser);
            } else if (parser.beginsWith("next")) {
                break;
            } else {
                System.out.println("invalid command!");
            }
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
        GameMenuMessages message = GameMenuController.dropBuilding(x, y, type, useMaterials);
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

    void dropUnit(User player, Parser parser) {
        if (!parser.getFlag("x") || !parser.getFlag("y") || !parser.getFlag("t") || !parser.getFlag("c")) {
            System.out.println("wrong format. some flags are missing");
            return;
        }
        if (!Utils.isInteger(parser.get("x")) || !Utils.isInteger(parser.get("y")) || !Utils.isInteger(parser.get("c"))) {
            System.out.println("coordinates and count should be integers");
            return;
        }
        GameMenuMessages outputMessage = GameMenuController.dropUnit(Integer.parseInt(parser.get("x")), Integer.parseInt(parser.get("y")),
            parser.get("t"), Integer.parseInt(parser.get("c")), player, false);
        System.out.println(outputMessage.getMessage());
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
        printNowPlaying();
    }

    private void printNowPlaying() {
        System.out.println("Now playing: @" + GameMenuController.getCurrentGame().getCurrentPlayer().getUsername());
    }

    public void endGame(User winner) {
        System.out.println("Game over!\nUser " + winner.getUsername() + " is the winner of the game!");
        isGameOver = true;
    }
}
