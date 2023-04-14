package models.buildings;

import models.BuildingType;
import models.MaterialInstance;

public class LimitedProductionBuilding<T, R> extends ProductionBuilding<T, R> {
  private int capacity;

  public LimitedProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials,
      int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T[] products, int capacity) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate, products);
    this.capacity = capacity;
  }

  public LimitedProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials,
      int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T[] products, R material, int capacity) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate, products, material);
    this.capacity = capacity;
  }

  public int getCapacity() {
    return capacity;
  }
}
