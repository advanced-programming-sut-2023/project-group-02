package models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

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

    public void setObject(MapObject mapObject) {
        cell.setObject(mapObject);
        // TODO: every MapObject should have a method to get its image, not just
        // Building
        if (mapObject != null && mapObject instanceof Building) {
            ImageView imageView = ((Building) mapObject).getBuildingImage();
            imageView.setFitWidth(squareSize);
            imageView.setFitHeight(squareSize);
            getChildren().add(imageView);
            cell.setObject(mapObject);
        } else {
            getChildren().remove(1);
        }
    }

    public static CellWrapper findCellWrapperWithXAndY(ArrayList<CellWrapper> cellWrappers, int x, int y) {
        for (CellWrapper cellWrapper : cellWrappers) {
            if (cellWrapper.getSquareX() == x && cellWrapper.getSquareY() == y)
                return cellWrapper;
        }
        return null;
    }
}
