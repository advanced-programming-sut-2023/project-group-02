package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

public class ProductionBuilding<T, R> extends Building {
  private int rate;
  private T[] products;
  private R material;

  public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T[] products) {
    super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
    this.rate = rate;
    this.products = products;
  }

  public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity, int rate, T[] products, R material) {
    this(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate, products);
    this.material = material;
  }

  public int getRate() {
    return rate;
  }

  public T[] getProducts() {
    return products;
  }

  public boolean needsMaterial() {
    return material != null;
  }
}
