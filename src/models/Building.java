package models;

public abstract class Building {
  private String name;
  private BuildingType type;
  private MaterialInstance[] buildingMaterials;
  private int hitpoint;
  private int workerCount = 0;
  private int effectOnPopularity = 0;
  private Unit unit;

  public Building(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
      int workerCount, int effectOnPopularity) {
    this.name = name;
    this.type = type;
    this.buildingMaterials = buildingMaterials;
    this.hitpoint = initialHitpoint;
    this.workerCount = workerCount;
    this.effectOnPopularity = effectOnPopularity;
  }

  String getName() {
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
