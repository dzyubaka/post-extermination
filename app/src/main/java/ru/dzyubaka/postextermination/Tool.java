package ru.dzyubaka.postextermination;

public class Tool extends Item {

    private int durability;

    protected Tool(Type type, String name, int drawable, int durability) {
        super(type, name, "Durability: " + durability, drawable);
        this.durability = durability;
    }

    public void use() {
        durability--;
        description = "Durability: " + durability;
    }
}
