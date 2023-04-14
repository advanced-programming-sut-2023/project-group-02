package models;

import models.buildings.PlainBuilding;

public class People {
    private PlainBuilding livingPlace;
    private String workingPlace;
    boolean hasJob = false;

    public People(PlainBuilding livingPlace) {
        this.livingPlace = livingPlace;
    }

    public void recruit(String workingPlace) {
        hasJob = true;
        this.workingPlace = workingPlace;
    }

    public PlainBuilding getLivingPlace() {
        return livingPlace;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public boolean isHasJob() {
        return hasJob;
    }
}
