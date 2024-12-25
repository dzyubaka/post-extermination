package ru.dzyubaka.postextermination;

public class Food extends Item {

    int hunger, thirst, toxins;

    protected Food(Type type, String name, String description, int drawable, int weight, int hunger, int thirst, int toxins) {
        super(type, name, description, drawable, weight);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
    }

}
