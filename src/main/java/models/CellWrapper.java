package models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class CellWrapper extends StackPane {
    private static final int squareSize = 70;
    private final Cell cell;
    private Rectangle rectangle;

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

    public void setStroke(Paint paint) {
        rectangle.setStroke(paint);
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
        } else {
            getChildren().remove(1);
        }
    }
}
