package models.buildings;

import models.Building;
import models.BuildingType;
import models.Material;
import models.MaterialInstance;

import java.util.ArrayList;
import java.util.List;

public class EngineerGuild extends Building {
    public static final BuildingType type = BuildingType.CASTLE_BUILDINGS;
    public static final ArrayList<MaterialInstance> cost = new ArrayList<>(
            List.of(new MaterialInstance(Material.WOOD,10),new MaterialInstance(Material.GOLD,100)));

    public static final String name = "Engineer Guild";
    public static final int ladderManCost = 2 ;
    public static final int engineerCost = 2; //TODO
}
