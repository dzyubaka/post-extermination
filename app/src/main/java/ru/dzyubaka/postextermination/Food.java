package ru.dzyubaka.postextermination;

public class Food extends Item {

    int hunger, thirst, toxins;

    protected Food(Type type, String name, String description, int sprite, int hunger, int thirst, int toxins) {
        super(type, name, description, sprite);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
    }

}
