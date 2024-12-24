package ru.dzyubaka.postextermination;

public class Tile {

    String name;
    String description;
    int drawable;
    int searchesLeft;
    Item[] items;

    public Tile(String name, String description, int drawable) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
    }

}
