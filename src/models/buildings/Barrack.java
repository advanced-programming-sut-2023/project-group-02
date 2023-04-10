package models.buildings;

import java.util.ArrayList;
import java.util.List;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;
import models.Material;

public class Barrack extends Building {
  public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
  public static final ArrayList<MaterialInstance> cost = new ArrayList<>(
      List.of(new MaterialInstance(Material.STONE, 15)));

  public static final String name = "Barrack";
  public static final int troopCost = 1; // TODO
}
