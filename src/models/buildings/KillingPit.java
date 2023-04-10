package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class KillingPit extends Building {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>(
            List.of(new MaterialInstance(Material.WOOD,6)));

    public static final String name = "Killing Pit";
    public static final int damage = 3; //TODO
}
