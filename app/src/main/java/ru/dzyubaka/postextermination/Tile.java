package ru.dzyubaka.postextermination;

import java.util.ArrayList;

public class Tile {

    String name;
    String description;
    int drawable;
    int searchesLeft;
    ArrayList<Item> items;

    public Tile(String name, String description, int drawable, int searchesLeft) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
        this.searchesLeft = searchesLeft;
        items = new ArrayList<>();
    }

}
