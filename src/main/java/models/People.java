package models;

import models.buildings.PlainBuilding;

public class People {
    private Building workingPlace;
    boolean hasJob = false;

    public void recruit(Building workingPlace) {
        hasJob = true;
        this.workingPlace = workingPlace;
    }

    public Building getWorkingPlace() {
        return workingPlace;
    }

    public boolean hasJob() {
        return hasJob;
    }
}
