package models.buildingsv2;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class DefensiveBuilding extends Building {
  private int fireRange = 0;
  private int defendRange = 0;

  public DefensiveBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int fireRange, int defendRange) {
    super(name, type, buildingMaterials, initialHitpoint, 0, 0);
    this.fireRange = fireRange;
    this.defendRange = defendRange;
  }

  public int getFireRange() {
    return fireRange;
  }

  public int getDefendRange() {
    return defendRange;
  }
}
