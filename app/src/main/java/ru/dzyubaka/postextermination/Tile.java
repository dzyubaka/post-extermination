package ru.dzyubaka.postextermination;

import java.util.ArrayList;
import java.util.Map;

public class Tile {

    final Map<ItemType, Integer> loot;

    String name;
    String description;
    int drawable;
    int searchesLeft;
    ArrayList<Item> items;

    public Tile(String name, String description, int drawable, int searchesLeft, Map<ItemType, Integer> loot) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
        this.searchesLeft = searchesLeft;
        items = new ArrayList<>();
        this.loot = loot;
    }

}
