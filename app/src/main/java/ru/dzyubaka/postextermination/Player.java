package ru.dzyubaka.postextermination;

import android.content.Context;
import android.graphics.Point;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dzyubaka.postextermination.model.Equipment;
import ru.dzyubaka.postextermination.model.Item;
import ru.dzyubaka.postextermination.model.ItemType;

public class Player {

    public final Point position = new Point(250, 250);
    public final ArrayList<Item> inventory = new ArrayList<>();

    public HashMap<Integer, Boolean> bleeding = new HashMap<>(Map.of(
            R.id.head_bleeding, false,
            R.id.body_bleeding, false,
            R.id.left_arm_bleeding, false,
            R.id.right_arm_bleeding, false,
            R.id.left_leg_bleeding, false,
            R.id.right_leg_bleeding, false
    ));

    public HashMap<Integer, Boolean> fractures = new HashMap<>(Map.of(
            R.id.left_arm_fracture, false,
            R.id.right_arm_fracture, false,
            R.id.left_leg_fracture, false,
            R.id.right_leg_fracture, false
    ));

    int thirstPerAction = 2;
    int maxWeight = 8000;

    private int sanity = 100;
    private int hunger = 10;
    private int thirst = 10;
    private int energy = 100;
    private int toxins = 0;
    private int pain = 0;
    private int turns = 0;

    public int getSanity() {
        return sanity;
    }

    public void addSanity(int sanity) {
        this.sanity = Math.min(Math.max(this.sanity + sanity, 0), 100);
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

    public void addPain(int pain) {
        this.pain = Math.min(Math.max(this.pain + pain, 0), 100);
    }

    public int getTurns() {
        return turns;
    }

    public void action(Context context) {
        hunger++;
        thirst += thirstPerAction;
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

        for (Map.Entry<Integer, Boolean> bleed : bleeding.entrySet()) {
            if (bleed.getValue()) {
                pain++;
                sanity--;
                if (Utils.chance(10)) {
                    bleeding.put(bleed.getKey(), false);
                    Toast.makeText(context, "Bleeding has stopped.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        for (Map.Entry<Integer, Boolean> fracture : fractures.entrySet()) {
            if (fracture.getValue()) {
                pain++;
                if (Utils.chance(1)) {
                    fractures.put(fracture.getKey(), false);
                    Toast.makeText(context, "Fracture has healed.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        sanity = Math.min(Math.max(sanity, 0), 100);
        hunger = Math.min(Math.max(hunger, 0), 100);
        thirst = Math.min(Math.max(thirst, 0), 100);
        energy = Math.min(Math.max(energy, 0), 100);
        toxins = Math.min(Math.max(toxins, 0), 100);
        pain = Math.min(Math.max(pain, 0), 100);

        turns++;
    }

    public int getMaxWeight() {
        int weight = 0;

        for (Item item : inventory) {
            if (item instanceof Equipment equipment && equipment.equipped) {
                weight += equipment.capacity;
            }
        }

        return maxWeight + weight;
    }

    public int getWeight() {
        int weight = 0;

        for (Item item : inventory) {
            weight += item.weight;
        }

        return weight;
    }

    public boolean canWalk() {
        return getWeight() <= getMaxWeight();
    }

    public boolean has(ItemType type) {
        return get(type) != null;
    }

    public Item get(ItemType type) {
        for (Item item : inventory) {
            if (item.type == type) {
                return item;
            }
        }
        return null;
    }

    public boolean has(ItemType type, int count) {
        int c = 0;
        for (Item item : inventory) {
            if (item.type == type) {
                c++;
            }
        }
        return c >= count;
    }
}
