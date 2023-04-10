package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class WoodCutter extends Building {
    public static final BuildingType type = BuildingType.INDUSTRY;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.WOOD,4)));

    public static final String name = "Woodcutter";
    public static final int rate = 2; //TODO
    public static final int numberOfWorkers = 1;
}
