package models;

public class WaterZone extends MapObject {
    private final WaterZoneType type;
    public WaterZone(int x, int y, WaterZoneType type) {
        super(x,y,null);
        this.type = type;
    }

    public WaterZoneType getType() {
        return type;
    }
}
