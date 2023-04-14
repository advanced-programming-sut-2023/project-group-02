package models;

import models.buildings.DamagingBuilding;
import models.buildings.DefensiveBuilding;
import models.buildings.InventoryBuilding;
import models.buildings.PlainBuilding;
import models.buildings.ProductionBuilding;

public class BuildingFactory {
  public static PlainBuilding smallStoneGate() {
    return new PlainBuilding("Small Stone Gate", BuildingType.CASTLE_BUILDINGS, new MaterialInstance[0], 0, 0, 0);
  }

  public static PlainBuilding largeStoneGate() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.STONE, 20) };
    return new PlainBuilding("Large Stone Gate", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static PlainBuilding drawbridge() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 10) };
    return new PlainBuilding("Drawbridge", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static DefensiveBuilding lookoutTower() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.STONE, 10) };
    return new DefensiveBuilding("Lookout Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static DefensiveBuilding perimeterTower() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.STONE, 10) };
    return new DefensiveBuilding("Perimeter Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static DefensiveBuilding defenseTurret() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.STONE, 15) };
    return new DefensiveBuilding("Defense Turret", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static DefensiveBuilding squareTower() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.STONE, 35) };
    return new DefensiveBuilding("Square Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static DefensiveBuilding circleTower() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.STONE, 40) };
    return new DefensiveBuilding("Square Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  // TODO: use a better type than Object
  public static InventoryBuilding<Object> armoury() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 5) };
    return new InventoryBuilding<>("Armoury", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0, 0);
  }

  public static DamagingBuilding killingPit() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 6) };
    return new DamagingBuilding("Killing Pit", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0, 0);
  }

  public static ProductionBuilding<Object> mill() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20) };
    return new ProductionBuilding<>("Mill", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 0, 3, 0, 0,
        /* flour */ null, /* wheat */ null);
  }

  public static ProductionBuilding<Object> ironMine() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20) };
    return new ProductionBuilding<>("Iron Mine", BuildingType.INDUSTRY, buildingMaterials, 0, 2,
        0, 0,
        null, /* iron */ null);
  }

  public static PlainBuilding shop() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 5) };
    return new PlainBuilding("Shop", BuildingType.INDUSTRY, buildingMaterials, 0, 1, 0);
  }

  public static ProductionBuilding<Object> pitchRig() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20) };
    return new ProductionBuilding<>("Pitch Rig", BuildingType.INDUSTRY, buildingMaterials, 0, 1, 0, 0,
        /* pitch */ null);
  }

  public static ProductionBuilding<Object> stoneMine() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20) };
    // TODO: implement capacity
    return new ProductionBuilding<>("Stone Mine", BuildingType.INDUSTRY, buildingMaterials, 0, 3, 0, 0,
        /* stone */ null);
  }

  public static InventoryBuilding<Object> stockpile() {
    return new InventoryBuilding<>("Stockpile", BuildingType.INDUSTRY, new MaterialInstance[0], 0, 0, 0, 0);
  }

  public static ProductionBuilding<Object> woodcutter() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 3) };
    return new ProductionBuilding<>("Woodcutter", BuildingType.INDUSTRY, buildingMaterials, 0, 3, 0, 0,
        /* wood */ null);
  }

  public static PlainBuilding house() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 6) };
    return new PlainBuilding("House", BuildingType.TOWN_BUILDINGS, buildingMaterials, 0, 0, 0);
  }

  public static ProductionBuilding<Object> armourer() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20),
        new MaterialInstance(Material.GOLD, 100) };
    return new ProductionBuilding<>("Armourer", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* armour */ null, /* iron */ null);
  }

  public static ProductionBuilding<Object> blacksmith() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20),
        new MaterialInstance(Material.GOLD, 100) };
    return new ProductionBuilding<>("Blacksmith", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* armour */ null, /* iron */ null);
  }

  public static ProductionBuilding<Object> fletcher() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20),
        new MaterialInstance(Material.GOLD, 100) };
    return new ProductionBuilding<>("Fletcher", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* armour */ null, /* iron */ null);
  }

  public static ProductionBuilding<Object> poleturner() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 20),
        new MaterialInstance(Material.GOLD, 100) };
    return new ProductionBuilding<>("Poleturner", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* armour */ null, /* iron */ null);
  }

  public static ProductionBuilding<Object> oilSmelter() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.IRON, 10),
        new MaterialInstance(Material.GOLD, 100) };
    // TODO: enforce the worker to be an engineer
    return new ProductionBuilding<>("Oil Smelter", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* armour */ null, /* iron */ null);
  }

  public static ProductionBuilding<Object> appleGarden() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 5) };
    return new ProductionBuilding<>("Apple Garden", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* apples */ null);
  }

  public static ProductionBuilding<Object> dairy() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 10) };
    return new ProductionBuilding<>("Dairy", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* cheese, letter */ null);
  }

  public static ProductionBuilding<Object> barleyField() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 15) };
    return new ProductionBuilding<>("Barley Field", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* barley */ null);
  }

  public static ProductionBuilding<Object> huntingPost() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 5) };
    return new ProductionBuilding<>("Hunting Post", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* meat */ null);
  }

  public static ProductionBuilding<Object> wheatFarm() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 15) };
    return new ProductionBuilding<>("Wheat Farm", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* wheat */ null);
  }

  public static ProductionBuilding<Object> bakery() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 10) };
    return new ProductionBuilding<>("Bakery", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* bread */ null, /* flour */ null);
  }

  public static ProductionBuilding<Object> brewery() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 10) };
    return new ProductionBuilding<>("Brewery", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
        /* barley */ null, /* beer */ null);
  }

  public static InventoryBuilding<Object> foodInventory() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.WOOD, 5) };
    return new InventoryBuilding<>("Food Inventory", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 0, 0, 0,
        0);
  }

  public static PlainBuilding church() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.GOLD, 250) };
    return new PlainBuilding("Church", BuildingType.TOWN_BUILDINGS, buildingMaterials, 0, 0, 2);
  }

  public static PlainBuilding cathedral() {
    MaterialInstance[] buildingMaterials = { new MaterialInstance(Material.GOLD, 1000) };
    return new PlainBuilding("Cathedral", BuildingType.TOWN_BUILDINGS, buildingMaterials, 0, 0, 2);
  }
}
