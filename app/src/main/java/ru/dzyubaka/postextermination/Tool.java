package ru.dzyubaka.postextermination;

public class Tool extends Item {

    private int durability;

    protected Tool(Type type, String name, String description, int drawable, int weight, int durability) {
        super(type, name, description, drawable, weight);
        this.durability = durability;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "\nDurability: " + durability;
    }

    public boolean use() {
        durability--;
        return durability == 0;
    }
}
