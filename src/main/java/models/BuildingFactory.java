package models;

import java.lang.reflect.Method;

import models.buildings.DamagingBuilding;
import models.buildings.DefensiveBuilding;
import models.buildings.InventoryBuilding;
import models.buildings.LimitedProductionBuilding;
import models.buildings.PlainBuilding;
import models.buildings.ProductionBuilding;
import models.buildings.RatedBuilding;
import utils.Utils;

public class BuildingFactory {
    public static PlainBuilding smallStoneGate() {
        return new PlainBuilding("Small Stone Gate", BuildingType.CASTLE_BUILDINGS, new MaterialInstance[0], 300, 0, 0,
                8);
    }

    public static PlainBuilding largeStoneGate() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 20)};
        return new PlainBuilding("Large Stone Gate", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 500, 0, 0, 10);
    }

    public static PlainBuilding drawbridge() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new PlainBuilding("Drawbridge", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 120, 0, 0, 0);
    }

    public static DefensiveBuilding lookoutTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 10)};
        return new DefensiveBuilding("Lookout Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 400, 4, 6);
    }

    public static DefensiveBuilding perimeterTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 10)};
        return new DefensiveBuilding("Perimeter Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 600, 4, 5);
    }

    public static DefensiveBuilding defenseTurret() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 15)};
        return new DefensiveBuilding("Defense Turret", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 650, 4, 5);
    }

    public static DefensiveBuilding squareTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 35)};
        return new DefensiveBuilding("Square Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 700, 3, 5);
    }

    public static DefensiveBuilding circleTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 40)};
        return new DefensiveBuilding("Circle Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 750, 3, 6);
    }

    public static InventoryBuilding armoury() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new InventoryBuilding("Armoury", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 250, 0, 0,
            MartialEquipment.class, 40);
    }

    public static DamagingBuilding killingPit() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 6)};
        return new DamagingBuilding("Killing Pit", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 80, 0, 0, 0);
    }

    public static PlainBuilding barrack() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 15)};
        return new PlainBuilding("Barrack", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 200, 0, 0, 0);
    }

    public static ProductionBuilding mill() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new ProductionBuilding("Mill", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 150, 3, 0, 15,
                new Food[] { Food.FLOUR }, Food.WHEAT);
    }

    public static ProductionBuilding inn() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding("Inn", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 300, 1, 10, 3,
            null,
            Food.BEER);
    }

    public static ProductionBuilding ironMine() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new ProductionBuilding("Iron Mine", BuildingType.INDUSTRY, buildingMaterials, 170, 2, 0, 10,
            new Material[]{Material.IRON});
    }

    public static PlainBuilding shop() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new PlainBuilding("Shop", BuildingType.INDUSTRY, buildingMaterials, 70, 1, 0, 0);
    }

    public static RatedBuilding oxTether() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new RatedBuilding("Ox Tether", BuildingType.INDUSTRY, buildingMaterials, 50, 1, 0, 1);
    }

    public static ProductionBuilding pitchRig() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new ProductionBuilding("Pitch Rig", BuildingType.INDUSTRY, buildingMaterials, 100, 1, 0, 3,
            new Material[]{Material.TAR});
    }

    public static LimitedProductionBuilding stoneMine() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new LimitedProductionBuilding("Stone Mine", BuildingType.INDUSTRY, buildingMaterials, 40, 3, 0, 10,
                new Material[] { Material.STONE }, 10);
    }

    public static InventoryBuilding stockpile() {
        return new InventoryBuilding("Stockpile", BuildingType.INDUSTRY, new MaterialInstance[0], 25, 0, 0,
            Material.class, 50);
    }

    public static ProductionBuilding woodcutter() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 3)};
        return new ProductionBuilding("Woodcutter", BuildingType.INDUSTRY, buildingMaterials, 40, 3, 0, 20,
            new Material[]{Material.WOOD});
    }

    public static PlainBuilding house() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 6)};
        return new PlainBuilding("House", BuildingType.TOWN_BUILDINGS, buildingMaterials, 120, 0, 0, 8);
    }

    public static ProductionBuilding armourer() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding("Armourer", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 70, 1, 0, 3,
            new MartialEquipment[]{MartialEquipment.METAL_ARMOUR}, Material.IRON);
    }

    public static ProductionBuilding blacksmith() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding("Blacksmith", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 70, 1, 0, 3,
            new MartialEquipment[]{MartialEquipment.SWORD, MartialEquipment.MACE}, Material.IRON);
    }

    public static ProductionBuilding fletcher() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding("Fletcher", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 70, 1, 0, 3,
            new MartialEquipment[]{MartialEquipment.BOW, MartialEquipment.CROSSBOW}, Material.WOOD);
    }

    public static ProductionBuilding poleturner() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding("Poleturner", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 70, 1, 0, 3,
            new MartialEquipment[]{MartialEquipment.PIKE}, Material.IRON);
    }

    public static ProductionBuilding oilSmelter() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.IRON, 10),
            new MaterialInstance(Material.GOLD, 100)};
        // TODO: enforce the worker to be an engineer
        // TODO: how are we going to specify a pot of oil?
        return new ProductionBuilding("Oil Smelter", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 30, 1, 0, 0,
            null);
    }

    public static PlainBuilding pitchDitch() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.TAR, 4)}; //TODO i changed 0.4 to 4 hossein!
        return new PlainBuilding("Pitch Ditch", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 30, 1, 0, 0);
    }

    public static PlainBuilding cagedWarDogs() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10),
            new MaterialInstance(Material.GOLD, 100)};
        return new PlainBuilding("Caged War Dogs", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 50, 1, 0, 0);
    }

    public static PlainBuilding siegeTent() {
        // TODO: enforce the worker to be an engineer
        return new PlainBuilding("Siege Tent", BuildingType.CASTLE_BUILDINGS, new MaterialInstance[0], 30, 1, 0, 0);
    }

    public static ProductionBuilding stable() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new ProductionBuilding("Stable", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 100, 1, 0, 1,
            new MartialEquipment[]{MartialEquipment.HORSE});
    }

    public static ProductionBuilding appleGarden() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new ProductionBuilding("Apple Garden", BuildingType.FARM_BUILDINGS, buildingMaterials, 60, 1, 0, 20,
            new Food[]{Food.APPLES});
    }

    public static ProductionBuilding dairy() {
        // TODO: leather
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new ProductionBuilding("Dairy", BuildingType.FARM_BUILDINGS, buildingMaterials, 60, 1, 0, 15,
            new Food[]{Food.CHEESE});
    }

    public static ProductionBuilding barleyField() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 15)};
        return new ProductionBuilding("Barley Field", BuildingType.FARM_BUILDINGS, buildingMaterials, 60, 1, 0, 15,
            new Food[]{Food.BARLEY});
    }

    public static ProductionBuilding huntingPost() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new ProductionBuilding("Hunting Post", BuildingType.FARM_BUILDINGS, buildingMaterials, 60, 1, 0, 5,
            new Food[]{Food.MEAT});
    }

    public static ProductionBuilding wheatFarm() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 15)};
        return new ProductionBuilding("Wheat Farm", BuildingType.FARM_BUILDINGS, buildingMaterials, 90, 1, 0, 20,
            new Food[]{Food.WHEAT});
    }

    public static ProductionBuilding bakery() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new ProductionBuilding("Bakery", BuildingType.FARM_BUILDINGS, buildingMaterials, 70, 1, 0, 20,
            new Food[]{Food.BREAD},
            Food.FLOUR);
    }

    public static ProductionBuilding brewery() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new ProductionBuilding("Brewery", BuildingType.FARM_BUILDINGS, buildingMaterials, 70, 1, 0, 10,
            new Food[]{Food.BARLEY},
            Food.BEER);
    }

    public static InventoryBuilding foodInventory() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new InventoryBuilding("Food Inventory", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 130,
                0, 0, Food.class, 100);
    }

    public static PlainBuilding church() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.GOLD, 250)};
        return new PlainBuilding("Church", BuildingType.TOWN_BUILDINGS, buildingMaterials, 400, 0, 5, 0);
    }

    public static PlainBuilding cathedral() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.GOLD, 1000)};
        return new PlainBuilding("Cathedral", BuildingType.TOWN_BUILDINGS, buildingMaterials, 800, 0, 10, 0);
    }

    public static DefensiveBuilding shortWall() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 5)};
        return new DefensiveBuilding("Short Wall", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 100, 0, 0);
    }

    public static DefensiveBuilding tallWall() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 10)};
        return new DefensiveBuilding("Tall Wall", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 140, 0, 0);
    }

    /**
     * @param name name of the building (case-insensitive)
     * @return a building with the given name, or `null` if not found
     */
    public static Building makeBuilding(String name) {
        name = Utils.toCamelCase(name);
        Method[] methods = BuildingFactory.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                try {
                    return (Building) method.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
