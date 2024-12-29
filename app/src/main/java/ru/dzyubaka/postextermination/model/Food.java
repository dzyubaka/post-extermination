package ru.dzyubaka.postextermination.model;

public class Food extends Item {

    public int hunger;
    public int thirst;
    public int toxins;
    public int energy;
    public int pain;

    protected Food(ItemType type, String description, int drawable, int weight, int hunger, int thirst, int toxins) {
        super(type, description, drawable, weight);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
    }

    protected Food(ItemType type, String description, int drawable, int weight, int hunger, int thirst, int toxins, int energy) {
        super(type, description, drawable, weight);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
        this.energy = energy;
    }

    public Food(ItemType type, String description, int drawable, int weight, int pain) {
        super(type, description, drawable, weight);
        this.pain = pain;
    }
}
