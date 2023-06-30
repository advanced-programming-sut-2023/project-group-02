package models;

import controllers.GameMenuController;
import controllers.UnitMenuController;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import models.units.MakeUnitInstances;
import models.units.Unit;
import utils.Graphics;
import utils.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.view.enums.GameMenuMessages;

public class CellWrapper extends StackPane {
    private static final int squareSize = 70;
    private final Cell cell;
    private Rectangle rectangle;
    private boolean isSelected = false;
    private Rectangle sicknessOverlay;

    public CellWrapper(Cell cell) {
        super();
        setPrefWidth(squareSize);
        setPrefHeight(squareSize);

        this.cell = cell;

        rectangle = new Rectangle(squareSize, squareSize);
        rectangle.setFill(cell.getTexture().getPaint());
        rectangle.setStrokeType(StrokeType.INSIDE);
        rectangle.setStroke(Color.TRANSPARENT);
        getChildren().add(rectangle);

        sicknessOverlay = new Rectangle(squareSize, squareSize);
        sicknessOverlay.setFill(Color.rgb(50, 150, 20, 0.4));
        sicknessOverlay.setVisible(cell.hasSickness());

        if (cell.getObject() != null) {
            ImageView imageView = cell.getObject().getImage();
            if (imageView != null) {
                imageView.setFitWidth(squareSize);
                imageView.setFitHeight(squareSize);
                getChildren().add(imageView);
            }
        }
        addUnitsImages();

        Tooltip tooltip = new Tooltip();
        tooltip.setOnShowing(event -> {
            tooltip.setText(getTooltipText());
        });
        Tooltip.install(this, tooltip);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            rectangle.setStroke(Color.WHITE);
        } else {
            rectangle.setStroke(Color.TRANSPARENT);
        }
    }

    public static int getSquareSize() {
        return squareSize;
    }

    public Cell getCell() {
        return cell;
    }

    public int getSquareX() {
        return cell.getX();
    }

    public int getSquareY() {
        return cell.getY();
    }

    public MapObject getObject() {
        return cell.getObject();
    }

//    public void setObject(MapObject mapObject) {
//        cell.setObject(mapObject);
//        if (mapObject != null) {
//            ImageView imageView = mapObject.getImage();
//            if (imageView != null) {
//                imageView.setFitWidth(squareSize);
//                imageView.setFitHeight(squareSize);
//                getChildren().add(imageView);
//            }
//            cell.setObject(mapObject);
//        } else {
//            getChildren().removeIf(node -> node instanceof ImageView);
//            cell.setObject(null);
//        }
//    }

    public void dropObject(String objectName, Image objectImage, boolean isPreGame, User owner) {
        Building building = null;
        Unit unit = null;
        if (Directions.getDirectionsLowerCase().contains(objectName)) {
            GameMenuMessages message = GameMenuController.dropRock(cell.getX(), cell.getY(), objectName);
            if (message != GameMenuMessages.DONE_SUCCESSFULLY)
                Graphics.showMessagePopup(message.getMessage());
        } else if (TreeType.getTreeNamesList().contains(objectName)) {
            GameMenuMessages message = GameMenuController.dropTree(cell.getX(), cell.getY(), objectName);
            if (message != GameMenuMessages.DONE_SUCCESSFULLY)
                Graphics.showMessagePopup(message.getMessage());
        } else if ((building = BuildingFactory.makeBuilding(objectName)) != null) {
            GameMenuMessages message;
            if (owner != null) {
                message = GameMenuController.dropBuilding(cell.getX(), cell.getY(), owner, objectName, !isPreGame);
            } else
                message = GameMenuController.dropBuilding(cell.getX(), cell.getY(), building, !isPreGame);
            if (message != GameMenuMessages.DONE_SUCCESSFULLY)
                Graphics.showMessagePopup(message.getMessage());
        } else if ((unit = MakeUnitInstances.createUnitInstance(objectName)) != null) {
            int amount = UnitMenuController.getDroppingUnitsCount().get(unit.getName());
            GameMenuMessages message = GameMenuController.dropUnit(cell.getX(), cell.getY(), unit, amount, owner);
            if (!message.equals(GameMenuMessages.DONE_SUCCESSFULLY)) Graphics.showMessagePopup(message.getMessage());
        } else
            return;


        addUnitsImages();
        if (cell.getObject() != null) {
            getChildren().add(cell.getObject().getImage());
        }
    }

    public boolean dropSmallStone(User player) {
        Building building = BuildingFactory.makeBuilding("small stone gate");
        GameMenuMessages message = GameMenuController.dropSmallStoneGate(player, new Parser("-x " + cell.getX() + " -y " + cell.getY()));
        if (message != GameMenuMessages.DONE_SUCCESSFULLY) {
            Graphics.showMessagePopup(message.getMessage());
            return false;
        }
        ArrayList<CellWrapper> cellWrappers = new ArrayList<>();
        cellWrappers.add(this);
        cellWrappers.add(findCellWrapperWithXAndY(GameMenuController.getCurrentGameCellWrappers(), cell.getX() + 1, cell.getY()));
        GameMenuController.updateCellWrappers(cellWrappers);
        return true;
    }

    public static CellWrapper findCellWrapperWithXAndY(ArrayList<CellWrapper> cellWrappers, int x, int y) {
        for (CellWrapper cellWrapper : cellWrappers) {
            if (cellWrapper.getSquareX() == x && cellWrapper.getSquareY() == y)
                return cellWrapper;
        }
        return null;
    }

    private String getTooltipText() {
        StringBuilder tooltipText = new StringBuilder();
        if (getObject() != null) {
            MapObject object = getObject();
            if (object instanceof Building building) {
                tooltipText.append("Building: ");
                tooltipText.append(object.getName()).append("\n");
                tooltipText.append("Owner: " + building.getOwner().getUsername()).append("\n");
            } else {
                tooltipText.append("Object: ");
                tooltipText.append(object.getName()).append("\n");
            }
        }
        tooltipText.append("Texture: ").append(getCell().getTexture().getName()).append("\n");
        // TODO: show unit info
        return tooltipText.toString();
    }

    public void addUnitsImages() {
        if (cell.getUnits() != null && !cell.getUnits().isEmpty()) {
            for (Unit unit : cell.getUnits()) {
                if (this.getChildren().contains(unit.getImage()))
                    this.getChildren().remove(unit.getImage());
                this.getChildren().add(unit.getImage());
            }
        }
    }
}
