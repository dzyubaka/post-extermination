package ru.dzyubaka.postextermination;

public class Craft {

    public String description;
    public ItemType leftItem;
    public ItemType rightItem;
    public ItemType result;

    public Craft(String description, ItemType leftItem, ItemType rightItem, ItemType result) {
        this.description = description;
        this.leftItem = leftItem;
        this.rightItem = rightItem;
        this.result = result;
    }
}
