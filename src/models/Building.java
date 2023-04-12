package models;

import java.util.ArrayList;

public abstract class Building {
  private String name;
  private int hitpoint;
  private Unit unit;

  String getName() {
    return name;
  }

  int getHitpoint() {
    return hitpoint;
  }

  public static BuildingType type;

  public static ArrayList<MaterialInstance> cost = new ArrayList<>();
}
