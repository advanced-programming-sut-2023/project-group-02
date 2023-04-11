package models;

public class Map {
  public final int width;
  public final int height;
  private final Cell[][] map;

  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    this.map = new Cell[width][height];
  }

  public Cell findCellWithXAndY(int x, int y){
    return map[x][y];
  }
}
