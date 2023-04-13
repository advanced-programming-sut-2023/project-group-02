package models;

import java.util.ArrayList;

public abstract class Building {
  private String name;
  private int hitpoint;

  String getName() {
    return name;
  }

  public int getHitpoint() {
    return hitpoint;
  }

  public static BuildingType type;

  public static ArrayList<MaterialInstance> cost = new ArrayList<>();
}
