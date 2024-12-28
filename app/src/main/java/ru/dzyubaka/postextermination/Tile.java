package ru.dzyubaka.postextermination;

import java.util.ArrayList;
import java.util.Map;

public class Tile {

    public final Map<ItemType, Integer> loot;

    public String name;
    public String description;
    public int drawable;
    public int searchesLeft;
    public ArrayList<Item> items;

    public Tile(String name, String description, int drawable, int searchesLeft, Map<ItemType, Integer> loot) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
        this.searchesLeft = searchesLeft;
        items = new ArrayList<>();
        this.loot = loot;
    }

}
