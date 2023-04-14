package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class ProductionBuilding<T> extends Building {
  private int rate;
  private MaterialInstance neededMaterial;
  private T product;

  public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T product) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
    this.rate = rate;
    this.product = product;
  }

  public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T product, MaterialInstance neededMaterial) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
    this.neededMaterial = neededMaterial;
  }

  public int getRate() {
    return rate;
  }

  public T getProduct() {
    return product;
  }

  public boolean needsMaterial() {
    return neededMaterial != null;
  }
}
