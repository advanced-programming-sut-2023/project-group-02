package models.buildings;

import models.BuildingType;
import models.MaterialInstance;

public class ProductionBuilding extends RatedBuilding {
    private Object[] products;
    private Object material;

    public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity, int rate, Object[] products) {
        super(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate);
        this.rate = rate;
        this.products = products;
    }

    public ProductionBuilding(String name, BuildingType type, MaterialInstance[] buildingMaterials, int initialHitpoint,
            int workerCount, int effectOnPopularity, int rate, Object[] products, Object material) {
        this(name, type, buildingMaterials, initialHitpoint, workerCount, effectOnPopularity, rate, products);
        this.material = material;
    }

    public Object[] getProducts() {
        return products;
    }

    public Object getMaterial() {
        return material;
    }

    public boolean needsMaterial() {
        return material != null;
    }
}
