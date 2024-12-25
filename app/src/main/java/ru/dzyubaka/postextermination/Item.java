package ru.dzyubaka.postextermination;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Item implements Cloneable {

    static final Map<String, Item> prototypes = new HashMap<>();

    String name;
    String description;
    int drawable;

    static {
        prototypes.put("canned beans", new Item("Canned beans", "Tin can of beans.", R.drawable.canned_beans));
        prototypes.put("beans", new Food("Beans", "Can of tasty beans.", R.drawable.beans, -30, -10, 0));
        prototypes.put("chocolate", new Food("Chocolate", "Milk chocolate plate.", R.drawable.chocolate, -10, 0, 0));
        prototypes.put("water", new Food("Water", "Bottle of water.", R.drawable.water, 0, -30, -5));
        prototypes.put("multitool", new Tool("Multitool", R.drawable.multitool, 10));
    }

    protected Item(String name, String description, int drawable) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
    }

    public static Item create(String key) {
        return prototypes.get(key).clone();
    }

    @NonNull
    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
