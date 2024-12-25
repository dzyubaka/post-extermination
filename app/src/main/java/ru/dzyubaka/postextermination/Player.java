package ru.dzyubaka.postextermination;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int sanity = 100;
    private int hunger = 10;
    private int thirst = 10;
    private int energy = 100;
    private int toxins = 0;
    private int pain = 0;
    private int turns = 0;

    final int maxWeight = 10_000;

    final Point position = new Point(250, 250);
    final ArrayList<Item> inventory = new ArrayList<>(List.of(Item.create(Item.Type.CANNED_BEANS), Item.create(Item.Type.CHOCOLATE), Item.create(Item.Type.MULTITOOL)));

    public int getSanity() {
        return sanity;
    }

    public int getHunger() {
        return hunger;
    }

    public void addHunger(int hunger) {
        this.hunger = Math.min(Math.max(this.hunger + hunger, 0), 100);
    }

    public int getThirst() {
        return thirst;
    }

    public void addThirst(int thirst) {
        this.thirst = Math.min(Math.max(this.thirst + thirst, 0), 100);
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int energy) {
        this.energy = Math.min(Math.max(this.energy + energy, 0), 100);
    }

    public int getToxins() {
        return toxins;
    }

    public void addToxins(int toxins) {
        this.toxins = Math.min(Math.max(this.toxins + toxins, 0), 100);
    }

    public int getPain() {
        return pain;
    }

    public int getTurns() {
        return turns;
    }

    public void action() {
        hunger++;
        thirst += 2;
        energy--;

        if (hunger <= 20 && thirst <= 20 && energy >= 80 && toxins <= 20 && pain <= 20 && sanity < 100) {
            sanity++;
        }

        if (hunger >= 100) sanity--;
        if (thirst >= 100) sanity--;
        if (energy <= 0) sanity--;
        if (toxins >= 50) sanity--;
        if (toxins >= 100) sanity--;
        if (pain >= 50) sanity--;
        if (pain >= 100) sanity--;

        sanity = Math.min(Math.max(sanity, 0), 100);
        hunger = Math.min(Math.max(hunger, 0), 100);
        thirst = Math.min(Math.max(thirst, 0), 100);
        energy = Math.min(Math.max(energy, 0), 100);
        toxins = Math.min(Math.max(toxins, 0), 100);
        pain = Math.min(Math.max(pain, 0), 100);

        turns++;
    }

    public int getWeight() {
        int weight = 0;

        for (Item item : inventory) {
            weight += item.weight;
        }

        return weight;
    }

    public boolean canWalk() {
        return getWeight() <= maxWeight;
    }

}
