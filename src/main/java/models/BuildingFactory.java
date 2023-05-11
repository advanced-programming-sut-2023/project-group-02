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
        return new PlainBuilding("Small Stone Gate", BuildingType.CASTLE_BUILDINGS, new MaterialInstance[0], 0, 0, 0, 8);
    }

    public static PlainBuilding largeStoneGate() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 20)};
        return new PlainBuilding("Large Stone Gate", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0, 10);
    }

    public static PlainBuilding drawbridge() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new PlainBuilding("Drawbridge", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0, 0);
    }

    public static DefensiveBuilding lookoutTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 10)};
        return new DefensiveBuilding("Lookout Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
    }

    public static DefensiveBuilding perimeterTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 10)};
        return new DefensiveBuilding("Perimeter Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
    }

    public static DefensiveBuilding defenseTurret() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 15)};
        return new DefensiveBuilding("Defense Turret", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
    }

    public static DefensiveBuilding squareTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 35)};
        return new DefensiveBuilding("Square Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
    }

    public static DefensiveBuilding circleTower() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE, 40)};
        return new DefensiveBuilding("Circle Tower", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0);
    }

    public static InventoryBuilding armoury() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new InventoryBuilding("Armoury", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0,
                MartialEquipment.class, 0);
    }

    public static DamagingBuilding killingPit() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 6)};
        return new DamagingBuilding("Killing Pit", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 0, 0, 0);
    }

    public static ProductionBuilding<Food, Food> mill() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new ProductionBuilding<>("Mill", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 0, 3, 0, 0,
            new Food[]{Food.FLOUR}, Food.WHEAT);
    }

    public static ProductionBuilding<Object, Food> inn() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding<>("Inn", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            null,
            Food.BEER);
    }

    public static ProductionBuilding<Material, Object> ironMine() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new ProductionBuilding<>("Iron Mine", BuildingType.INDUSTRY, buildingMaterials, 0, 2, 0, 0,
            new Material[]{Material.IRON});
    }

    public static PlainBuilding shop() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new PlainBuilding("Shop", BuildingType.INDUSTRY, buildingMaterials, 0, 1, 0, 0);
    }

    public static RatedBuilding oxTether() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new RatedBuilding("Ox Tether", BuildingType.INDUSTRY, buildingMaterials, 0, 1, 0, 0);
    }

    public static ProductionBuilding<Material, Object> pitchRig() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new ProductionBuilding<>("Pitch Rig", BuildingType.INDUSTRY, buildingMaterials, 0, 1, 0, 0,
            new Material[]{Material.TAR});
    }

    public static LimitedProductionBuilding<Material, Object> stoneMine() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20)};
        return new LimitedProductionBuilding<>("Stone Mine", BuildingType.INDUSTRY, buildingMaterials, 0, 3, 0, 0,
            new Material[]{Material.STONE}, 0);
    }

    public static InventoryBuilding stockpile() {
        return new InventoryBuilding("Stockpile", BuildingType.INDUSTRY, new MaterialInstance[0], 0, 0, 0,
                Object.class, 0);
    }

    public static ProductionBuilding<Material, Object> woodcutter() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 3)};
        return new ProductionBuilding<>("Woodcutter", BuildingType.INDUSTRY, buildingMaterials, 0, 3, 0, 0,
            new Material[]{Material.WOOD});
    }

    public static PlainBuilding house() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 6)};
        return new PlainBuilding("House", BuildingType.TOWN_BUILDINGS, buildingMaterials, 0, 0, 0, 8);
    }

    public static ProductionBuilding<MartialEquipment, Material> armourer() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding<>("Armourer", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new MartialEquipment[]{MartialEquipment.METAL_ARMOUR}, Material.IRON);
    }

    public static ProductionBuilding<MartialEquipment, Material> blacksmith() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding<>("Blacksmith", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new MartialEquipment[]{MartialEquipment.SWORD, MartialEquipment.MACE}, Material.IRON);
    }

    public static ProductionBuilding<MartialEquipment, Material> fletcher() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding<>("Fletcher", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new MartialEquipment[]{MartialEquipment.BOW, MartialEquipment.CROSSBOW}, Material.WOOD);
    }

    public static ProductionBuilding<MartialEquipment, Material> poleturner() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 20),
            new MaterialInstance(Material.GOLD, 100)};
        return new ProductionBuilding<>("Poleturner", BuildingType.WEAPON_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new MartialEquipment[]{MartialEquipment.PIKE}, Material.IRON);
    }

    public static ProductionBuilding<Object, Object> oilSmelter() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.IRON, 10),
            new MaterialInstance(Material.GOLD, 100)};
        // TODO: enforce the worker to be an engineer
        // TODO: how are we going to specify a pot of oil?
        return new ProductionBuilding<>("Oil Smelter", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            null);
    }

    public static PlainBuilding pitchDitch() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.TAR, 4)}; //TODO i changed 0.4 to 4 hossein!
        return new PlainBuilding("Pitch Ditch", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 1, 0, 0);
    }

    public static PlainBuilding cagedWarDogs() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10),
            new MaterialInstance(Material.GOLD, 100)};
        return new PlainBuilding("Caged War Dogs", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 1, 0, 0);
    }

    public static PlainBuilding siegeTent() {
        // TODO: enforce the worker to be an engineer
        return new PlainBuilding("Siege Tent", BuildingType.CASTLE_BUILDINGS, new MaterialInstance[0], 0, 1, 0, 0);
    }

    public static ProductionBuilding<Object, MartialEquipment> stable() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new ProductionBuilding<>("Stable", BuildingType.CASTLE_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new MartialEquipment[]{MartialEquipment.HORSE});
    }

    public static ProductionBuilding<Food, Object> appleGarden() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new ProductionBuilding<>("Apple Garden", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.APPLES});
    }

    public static ProductionBuilding<Food, Object> dairy() {
        // TODO: leather
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new ProductionBuilding<>("Dairy", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.CHEESE});
    }

    public static ProductionBuilding<Food, Object> barleyField() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 15)};
        return new ProductionBuilding<>("Barley Field", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.BARLEY});
    }

    public static ProductionBuilding<Food, Object> huntingPost() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new ProductionBuilding<>("Hunting Post", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.MEAT});
    }

    public static ProductionBuilding<Food, Object> wheatFarm() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 15)};
        return new ProductionBuilding<>("Wheat Farm", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.WHEAT});
    }

    public static ProductionBuilding<Food, Food> bakery() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new ProductionBuilding<>("Bakery", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.BREAD},
            Food.FLOUR);
    }

    public static ProductionBuilding<Food, Food> brewery() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 10)};
        return new ProductionBuilding<>("Brewery", BuildingType.FARM_BUILDINGS, buildingMaterials, 0, 1, 0, 0,
            new Food[]{Food.BARLEY},
            Food.BEER);
    }

    public static InventoryBuilding foodInventory() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.WOOD, 5)};
        return new InventoryBuilding("Food Inventory", BuildingType.FOOD_PROCESSING_BUILDINGS, buildingMaterials, 0,
                0, 0, Food.class, 0);
    }

    public static PlainBuilding church() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.GOLD, 250)};
        return new PlainBuilding("Church", BuildingType.TOWN_BUILDINGS, buildingMaterials, 0, 0, 5, 0);
    }

    public static PlainBuilding cathedral() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.GOLD, 1000)};
        return new PlainBuilding("Cathedral", BuildingType.TOWN_BUILDINGS, buildingMaterials, 0, 0, 10, 0);
    }

    public static DefensiveBuilding shortWall() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE,5)};
        return new DefensiveBuilding("short wall",BuildingType.CASTLE_BUILDINGS,buildingMaterials,0,0,0);
    }

    public static DefensiveBuilding tallWall() {
        MaterialInstance[] buildingMaterials = {new MaterialInstance(Material.STONE,10)};
        return new DefensiveBuilding("tall wall",BuildingType.CASTLE_BUILDINGS,buildingMaterials,0,0,0);
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
