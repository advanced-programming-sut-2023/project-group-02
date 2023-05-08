package models;

import utils.Utils;

public enum Food {
    APPLES("apple",4),
    WHEAT("wheat",4),
    FLOUR("flour",3),
    BREAD("bread",3),
    MEAT("meat",8),
    BARLEY("barley",4),
    BEER("beer",0),
    // TODO: does this need to be here?
    CHEESE("cheese",5);

    private final String name;
    private final int price;

    Food(String name, int price) {
        this.name = name;
        this.price = price;
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
}
