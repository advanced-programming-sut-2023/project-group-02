package models;

public enum MartialEquipment {
    // made in Poleturner using wood:
    SPEAR("spear",5),
    PIKE("pike",8),
    // made in Fletcher using wood:
    BOW("bow",10),
    CROSSBOW("crossbow",10),
    // made in Armourer:
    METAL_ARMOUR("metal armour",8),
    // made in Diary Farm:
    LEATHER_ARMOUR("leather armour",7),
    // made in Black Smith using Iron:
    MACE("mace",13),
    SWORD("sword",15),
    // made in Stable:
    HORSE("horse",25);

    private final String name;
    private final int price;
    private final String imagePath;

    MartialEquipment(String name, int price) {
        this.name = name;
        this.price = price;
        imagePath = "/images/items/" + name + ".png";
    }

    public String getName() {
        return name;
    }

    public int getBuyPrice() {
        return price;
    }

    public int getSellPrice() {
        return price/2;
    }

    public String getImagePath() {
        return imagePath;
    }
}
