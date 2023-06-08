package models;


import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {
    private static final int squareSize = 70;
    private final Cell cell;

    public Square(Cell cell) {
        super(squareSize,squareSize);
        this.cell = cell;
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

    //TODO later we add buildings and ...
}
