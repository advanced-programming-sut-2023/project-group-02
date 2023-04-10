package models.buildings;

import java.util.ArrayList;
import java.util.List;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;
import models.Material;

public class Armoury extends Building {
  public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
  public static final ArrayList<MaterialInstance> cost = new ArrayList<>(
      List.of(new MaterialInstance(Material.WOOD, 5)));

  public static final String name = "Armoury";
  public static final int capacity = 1; // TODO
}
