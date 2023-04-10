package models;

public class MaterialInstance {
  public final Material material;
  public final int amount;

  public MaterialInstance(Material material, int amount) {
    this.material = material;
    this.amount = amount;
  }
}