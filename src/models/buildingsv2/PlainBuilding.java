package models.buildingsv2;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class PlainBuilding extends Building {
  public PlainBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
  }
}
