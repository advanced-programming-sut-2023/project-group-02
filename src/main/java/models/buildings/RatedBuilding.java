package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class RatedBuilding extends Building {
  protected int rate;

  public RatedBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
    this.rate = rate;
  }

  public int getRate() {
    return rate;
  }
}
