package ru.dzyubaka.postextermination;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class Item implements Cloneable {

    private static final HashMap<Type, Item> prototypes = new HashMap<>();

    final Type type;
    final String name;
    private final String description;
    final int drawable;
    final int weight;

    static {
        prototypes.put(Type.CANNED_BEANS, new Item(Type.CANNED_BEANS, "Canned beans", "Tin can of beans.", R.drawable.canned_beans, 400));
        prototypes.put(Type.BEANS, new Food(Type.BEANS, "Beans", "Can of tasty beans.", R.drawable.beans, 400, -30, -10, 0));
        prototypes.put(Type.CHOCOLATE, new Food(Type.CHOCOLATE, "Chocolate", "Milk chocolate plate.", R.drawable.chocolate, 100, -10, 0, 0));
        prototypes.put(Type.WATER, new Food(Type.WATER, "Water", "Bottle of water.", R.drawable.water, 500, 0, -30, -10));
        prototypes.put(Type.MULTITOOL, new Tool(Type.MULTITOOL, "Multitool", "Useful knife.", R.drawable.multitool, 250, 10));
        prototypes.put(Type.APPLE, new Food(Type.APPLE, "Apple", "Fresh apple.", R.drawable.apple, 150, -15, -5, -5));
        prototypes.put(Type.ROTTEN_APPLE, new Food(Type.ROTTEN_APPLE, "Rotten apple", "Rotten apple.", R.drawable.rotten_apple, 150, -15, -5, 20));
        prototypes.put(Type.ENERGY_DRINK, new Food(Type.ENERGY_DRINK, "Energy drink", "Energy drink.", R.drawable.energy_drink, 500, -5, -30, 0, 20));
    }

    public static Item create(Type type) {
        return prototypes.get(type).clone();
    }

    public static String getDescription(Type type) {
        return prototypes.get(type).description;
    }

    protected Item(Type type, String name, String description, int drawable, int weight) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.drawable = drawable;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
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

    public enum Type {
        CANNED_BEANS,
        BEANS,
        CHOCOLATE,
        WATER,
        MULTITOOL,
        APPLE,
        ROTTEN_APPLE,
        ENERGY_DRINK,
    }

}
