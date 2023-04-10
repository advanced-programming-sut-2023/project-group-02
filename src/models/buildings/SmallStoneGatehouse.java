package models.buildings;

import java.util.ArrayList;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class SmallStoneGatehouse extends Building {
  public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
  public static final ArrayList<MaterialInstance> cost = new ArrayList<>();

  public final String name = "Small stone gatehouse";
  public final int hitpoint = 1; // TODO ???
}
