package controllers;

import models.*;
import models.units.MakeUnitInstances;
import models.units.Unit;
import models.units.UnitType;
import view.enums.BuildingMenuMessages;

import java.util.EnumSet;

public class BuildingMenuController {
    private static Building selectedBuilding;
    private static int selectedBuildingX;
    private static int selectedBuildingY;

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static void setSelectedBuilding(Building selectedBuilding, int x, int y) {
        BuildingMenuController.selectedBuilding = selectedBuilding;
        selectedBuildingX = x;
        selectedBuildingY = y;
    }

    public static String createUnit(String typeOfUnit, int count) {
        for (UnitType unitType : EnumSet.allOf(UnitType.class)) {
            if (unitType.getName().equals(typeOfUnit)) {
                if (!unitType.getWhereCanBeTrained().equals(selectedBuilding.getName()))
                    return "This troop can't be trained at this building";
            }
        }
//        return GameMenuController.dropUnit(selectedBuildingX, selectedBuildingY, typeOfUnit, count, true).getMessage();
        return null;
    }

    public static BuildingMenuMessages repair(Building building) {
        if (building.getHitpoint() == building.getInitialHitpoint()) {
            return BuildingMenuMessages.HP_IS_FULL;
        }
        MaterialInstance[] neededMaterials = neededMaterial(building);
        for (MaterialInstance material : neededMaterials) {
            if (material.amount > GameMenuController.getCurrentGame().getCurrentPlayersGovernment().getItemAmount(material.material)) {
                return BuildingMenuMessages.NOT_ENOUGH_MATERIAL;
            }
        }
        for (MaterialInstance material : neededMaterials) {
            GameMenuController.getCurrentGame().getCurrentPlayersGovernment().reduceItem(material.material, material.amount);
        }
        building.setHitpoint(building.getInitialHitpoint());
        return BuildingMenuMessages.DONE_SUCCESSFULLY;
    }

    public static MaterialInstance[] neededMaterial(Building building) {
        int hitpoint = building.getHitpoint();
        int initialHitpoint = building.getInitialHitpoint();
        MaterialInstance[] usedMaterials = building.getBuildingMaterials();
        MaterialInstance[] neededMaterials = new MaterialInstance[usedMaterials.length];
        for (int i = 0; i < usedMaterials.length; i++) {
            neededMaterials[i] = new MaterialInstance(usedMaterials[i].material, (int) ((initialHitpoint - hitpoint) / initialHitpoint) * usedMaterials[i].amount);
        }
        return neededMaterials;
    }


}
