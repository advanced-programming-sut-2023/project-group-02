package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class ProductionBuilding<T, R> extends Building {
  private int rate;
  private T product;
  private R material;

  public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T product) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
    this.rate = rate;
    this.product = product;
  }

  public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T product, R material) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
    this.material = material;
  }

  public int getRate() {
    return rate;
  }

  public T getProduct() {
    return product;
  }

  public boolean needsMaterial() {
    return material != null;
  }
}
