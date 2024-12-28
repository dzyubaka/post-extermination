package ru.dzyubaka.postextermination;

public class Food extends Item {

    int hunger;
    int thirst;
    int toxins;
    int energy;
    int pain;

    protected Food(ItemType type, String name, String description, int drawable, int weight, int hunger, int thirst, int toxins) {
        super(type, name, description, drawable, weight);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
    }

    protected Food(ItemType type, String name, String description, int drawable, int weight, int hunger, int thirst, int toxins, int energy) {
        super(type, name, description, drawable, weight);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
        this.energy = energy;
    }

    public Food(ItemType type, String name, String description, int drawable, int weight, int pain) {
        super(type, name, description, drawable, weight);
        this.pain = pain;
    }
}
