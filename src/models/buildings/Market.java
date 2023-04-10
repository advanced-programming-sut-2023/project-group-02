package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class Market extends Building {
    public static final BuildingType type = BuildingType.INDUSTRY;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.WOOD,5)));

    public static final String name = "Market";
    public static final int numberOfWorkers = 1;
}
