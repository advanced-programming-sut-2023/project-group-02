package models;

public abstract class Building {
  protected String name;
  protected BuildingType type;
  protected MaterialInstance[] buildingMaterials;
  protected int hitpoint;
  protected int workerCount = 0;
  protected int effectOnPopularity = 0;
  protected Unit unit;

  public Building(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity) {
    this.name = name;
    this.type = type;
    this.buildingMaterials = buildingMaterials;
    this.hitpoint = initialHitpoint;
    this.workerCount = workerCount;
    this.effectOnPopularity = effectOnPopularity;
  }

  public String getName() {
    return name;
  }

  public BuildingType getType() {
    return type;
  }

  public MaterialInstance[] getBuildingMaterials() {
    return buildingMaterials;
  }

  public int getHitpoint() {
    return hitpoint;
  }

  public int getWorkerCount() {
    return workerCount;
  }

  public int getEffectOnPopularity() {
    return effectOnPopularity;
  }

  public void decreaseHitpoint(int value) {
    hitpoint -= value;
  }
}
