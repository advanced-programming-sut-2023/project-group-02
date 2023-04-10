package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class PitchRig extends Building {
    public static final BuildingType type = BuildingType.INDUSTRY;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>
            (List.of(new MaterialInstance(Material.WOOD,20)));

    public static final String name = "Pitch Rig";
    public static final int rate = 1; //TODO
    public static final int numberOfWorkers = 1;
}
