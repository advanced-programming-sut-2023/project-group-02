package controllers;

import models.Building;
import models.MaterialInstance;
import view.enums.BuildingMenuMessages;

public class BuildingMenuController {
    private static Building selectedBuilding;

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static void setSelectedBuilding(Building selectedBuilding) {
        BuildingMenuController.selectedBuilding = selectedBuilding;
    }

    public static BuildingMenuMessages createUnit(String typeOfUnit, int count) {
        //errors are page 21
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
        for (int i = 0; i < usedMaterials.length ; i++) {
            neededMaterials[i] = new MaterialInstance(usedMaterials[i].material , (int) ((initialHitpoint - hitpoint) / initialHitpoint) * usedMaterials[i].amount);
        }
        return neededMaterials;
    }


}
