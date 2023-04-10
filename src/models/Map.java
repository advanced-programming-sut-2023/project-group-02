package models;

import java.util.ArrayList;

public class Map {
  public final int width;
  public final int height;
  private final ArrayList<ArrayList<Cell>> map;

  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    this.map = new ArrayList<ArrayList<Cell>>();
  }
}
