package ru.dzyubaka.postextermination;

public class Food extends Item {

    int hunger, thirst, toxins;

    protected Food(String name, String description, int sprite, int hunger, int thirst, int toxins) {
        super(name, description, sprite);
        this.hunger = hunger;
        this.thirst = thirst;
        this.toxins = toxins;
    }

}
