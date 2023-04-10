package models.buildings;

import models.Building;
import models.BuildingType;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class Stockpile extends Building {
    public static final BuildingType type = BuildingType.INDUSTRY;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>();

    public static final String name = "Stockpile";
    public static final int capacity = 3; //TODO
}
