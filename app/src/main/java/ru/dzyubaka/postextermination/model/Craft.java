package ru.dzyubaka.postextermination.model;

public class Craft {

    public String description;
    public ItemType leftItem;
    public int count;
    public ItemType rightItem;
    public ItemType result;
    public ItemType requirement;

    public Craft(String description, ItemType leftItem, int count, ItemType rightItem, ItemType result) {
        this.description = description;
        this.leftItem = leftItem;
        this.count = count;
        this.rightItem = rightItem;
        this.result = result;
    }

    public Craft(String description, ItemType leftItem, int count, ItemType rightItem, ItemType result, ItemType requirement) {
        this.description = description;
        this.leftItem = leftItem;
        this.count = count;
        this.rightItem = rightItem;
        this.result = result;
        this.requirement = requirement;
    }
}
