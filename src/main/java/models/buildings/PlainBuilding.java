package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;
import models.People;

import java.util.ArrayList;

public class PlainBuilding extends Building {
    int maxPeople;
    private final ArrayList<People> people; // people living in this building

    public PlainBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
                         int workerCount, int effectOnPopularity, int maxPeople) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity);
        this.maxPeople = maxPeople;
        this.people = new ArrayList<>(maxPeople);
    }

    public int getPopulation() {
        return people.size();
    }

    public int numberOfUnemployedPeople() {
        int result = 0;
        for (People person : this.people) {
            if (!person.hasJob())
                result++;
        }
        return result;
    }

    public int recruit(Building workingPlace, int number) {
        for (People person : this.people) {
            if (!person.hasJob()) {
                person.recruit(workingPlace);
                number--;
            }
            if (number == 0)
                return 0;
        }
        return number;
    }
}
