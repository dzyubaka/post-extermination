package ru.dzyubaka.postextermination;

public class Food extends Item {

    int hunger, thirst;

    protected Food(String name, String description, int sprite, int hunger, int thirst) {
        super(name, description, sprite);
        this.hunger = hunger;
        this.thirst = thirst;
    }
}
