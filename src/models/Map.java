package models;

public class Map {
  public final int width;
  public final int height;
  private final Cell[][] map;

  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    this.map = new Cell[height][width];
  }

  public Cell findCellWithXAndY(int x, int y){
    return map[x][y]; //TODO thinking about changing x and y
  }

  public Cell[][] getMiniMap(int x, int y) {
      //TODO a method to return a mini map (20 x 20)
    return null;
    }

    public void printMiniMap() {

    }

    public Cell[][] movedUp() {
      return null;
    }

    public Cell[][] movedRight() {
      return null;
    }

    public Cell[][] movedLeft() {
      return null;
    }

    public Cell[][] movedDown() {
      return null;
    }
}
