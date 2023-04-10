package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class MercenaryPost extends Building {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>(
            List.of(new MaterialInstance(Material.WOOD,10)));

    public static final String name = "Mercenary Post";
    public static final int troopCost = 1; //TODO
}
