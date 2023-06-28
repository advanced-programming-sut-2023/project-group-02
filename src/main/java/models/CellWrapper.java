package models;

import controllers.GameMenuController;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import utils.Graphics;
import view.enums.GameMenuMessages;

import java.util.ArrayList;

public class CellWrapper extends StackPane {
    private static final int squareSize = 70;
    private final Cell cell;
    private Rectangle rectangle;
    private boolean isSelected = false;

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

        if (cell.getObject() != null) {
            ImageView imageView = cell.getObject().getImage();
            if (imageView != null) {
                imageView.setFitWidth(squareSize);
                imageView.setFitHeight(squareSize);
                getChildren().add(imageView);
            }
        }


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

    public void dropObject(String objectName, Image objectImage, boolean isPreGame) {
        Building building = null;
        if (Directions.getDirectionsLowerCase().contains(objectName)) {
            GameMenuMessages message = GameMenuController.dropRock(cell.getX(), cell.getY(), objectName);
            if (message != GameMenuMessages.DONE_SUCCESSFULLY)
                Graphics.showMessagePopup(message.getMessage());
        } else if (TreeType.getTreeNamesList().contains(objectName)) {
            GameMenuMessages message = GameMenuController.dropTree(cell.getX(), cell.getY(), objectName);
            if (message != GameMenuMessages.DONE_SUCCESSFULLY)
                Graphics.showMessagePopup(message.getMessage());
        } else if ((building = BuildingFactory.makeBuilding(objectName)) != null)
            GameMenuController.dropBuilding(cell.getX(), cell.getY(), building, !isPreGame);
        else
            return;
        if (cell.getObject() != null) {
            ImageView imageView = cell.getObject().getImage();
            if (imageView != null) {
                imageView.setFitWidth(squareSize);
                imageView.setFitHeight(squareSize);
                getChildren().add(imageView);
            }
        }
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
        tooltipText.append("Texture: ").append(getCell().getTexture().getName()).append("\n");
        if (getObject() != null) {
            MapObject object = getObject();
            if (object instanceof Building building) {
                tooltipText.append("Building: ");
            } else {
                tooltipText.append("Object: ");
            }
            tooltipText.append(object.getName()).append("\n");
        }
        // TODO: show unit info
        return tooltipText.toString();
    }
}
