package models;

import models.buildings.PlainBuilding;

public class People {
    private String workingPlace;
    boolean hasJob = false;

    public void recruit(String workingPlace) {
        hasJob = true;
        this.workingPlace = workingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public boolean hasJob() {
        return hasJob;
    }
}
