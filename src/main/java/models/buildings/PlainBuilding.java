package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;
import models.People;

import java.util.ArrayList;

public class PlainBuilding extends Building {
    int maxPeople;
    private final People[] people; // people living in this building

    public PlainBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
                         int workerCount, int effectOnPopularity, int maxPeople) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
        this.maxPeople = maxPeople;
        this.people = new People[maxPeople];
    }

    public int getPopulation() {
        return people.length;
    }
}
