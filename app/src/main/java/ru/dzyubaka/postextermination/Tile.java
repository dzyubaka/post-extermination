package ru.dzyubaka.postextermination;

import java.util.ArrayList;
import java.util.Map;

public class Tile {

    public final Map<ItemType, Integer> loot;

    public TileType type;
    public String description;
    public int drawable;
    public int searchesLeft;
    public ArrayList<Item> items = new ArrayList<>();
    public Event event;

    public Tile(TileType type, String description, int drawable, int searchesLeft, Map<ItemType, Integer> loot) {
        this.type = type;
        this.description = description;
        this.drawable = drawable;
        this.searchesLeft = searchesLeft;
        this.loot = loot;
    }

    public Tile(TileType type, String description, int drawable, int searchesLeft, Map<ItemType, Integer> loot, Event event) {
        this.type = type;
        this.description = description;
        this.drawable = drawable;
        this.searchesLeft = searchesLeft;
        this.loot = loot;
        this.event = event;
    }

    public String getName() {
        return Utils.title(type.name());
    }
}
