package ru.dzyubaka.postextermination;

import java.util.ArrayList;

public class Tool extends Item {

    private int durability;

    protected Tool(ItemType type, String description, int drawable, int weight, int durability) {
        super(type, description, drawable, weight);
        this.durability = durability;
    }

    @Override
    public String getDescription() {
        return (super.getDescription() + "\nDurability: " + durability).replace("null\n", "");
    }

    public void use(ArrayList<Item> inventory) {
        durability--;
        if (durability == 0) {
            inventory.remove(this);
        }
    }
}
