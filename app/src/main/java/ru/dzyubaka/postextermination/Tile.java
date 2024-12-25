package ru.dzyubaka.postextermination;

import java.util.ArrayList;
import java.util.Map;

public class Tile {

    final Map<String, Integer> loot;

    String name;
    String description;
    int drawable;
    int searchesLeft;
    ArrayList<Item> items;

    public Tile(String name, String description, int drawable, int searchesLeft, Map<String, Integer> loot) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
        this.searchesLeft = searchesLeft;
        items = new ArrayList<>();
        this.loot = loot;
    }

}
