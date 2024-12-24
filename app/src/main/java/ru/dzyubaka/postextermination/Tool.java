package ru.dzyubaka.postextermination;

public class Tool extends Item {

    private int durability;

    protected Tool(String name, int drawable, int durability) {
        super(name, "Durability: " + durability, drawable);
        this.durability = durability;
    }

    public void use() {
        durability--;
        description = "Durability: " + durability;
    }
}
