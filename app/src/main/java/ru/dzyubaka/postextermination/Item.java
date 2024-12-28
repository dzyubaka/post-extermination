package ru.dzyubaka.postextermination;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class Item implements Cloneable {

    private static final HashMap<ItemType, Item> prototypes = new HashMap<>();

    final ItemType type;
    final String name;
    private final String description;
    final int drawable;
    final int weight;

    static {
        prototypes.put(ItemType.CANNED_BEANS, new Item(ItemType.CANNED_BEANS, "Canned beans", "Tin can of beans.", R.drawable.canned_beans, 400));
        prototypes.put(ItemType.BEANS, new Food(ItemType.BEANS, "Beans", "Can of tasty beans.", R.drawable.beans, 400, -30, -10, 0));
        prototypes.put(ItemType.CHOCOLATE, new Food(ItemType.CHOCOLATE, "Chocolate", "Milk chocolate plate.", R.drawable.chocolate, 100, -10, 0, 0));
        prototypes.put(ItemType.WATER, new Food(ItemType.WATER, "Water", "Bottle of water.", R.drawable.water, 500, 0, -30, -10));
        prototypes.put(ItemType.MULTITOOL, new Tool(ItemType.MULTITOOL, "Multitool", "Useful knife.", R.drawable.multitool, 250, 10));
        prototypes.put(ItemType.APPLE, new Food(ItemType.APPLE, "Apple", "Fresh apple.", R.drawable.apple, 150, -15, -5, -5));
        prototypes.put(ItemType.ROTTEN_APPLE, new Food(ItemType.ROTTEN_APPLE, "Rotten apple.", "Rotten apple.", R.drawable.rotten_apple, 150, -15, -5, 20));
        prototypes.put(ItemType.ENERGY_DRINK, new Food(ItemType.ENERGY_DRINK, "Energy drink.", "Energy drink.", R.drawable.energy_drink, 500, -5, -30, 0, 20));
        prototypes.put(ItemType.CHIPS, new Food(ItemType.CHIPS, "Chips", "Potato chips.", R.drawable.chips, 150, -20, 5, 0));
        prototypes.put(ItemType.SHOVEL, new Tool(ItemType.SHOVEL, "Shovel", "Shovel", R.drawable.shovel, 1500, 20));
        prototypes.put(ItemType.BANDAGE, new Item(ItemType.BANDAGE, "Bandage", "Bandage", R.drawable.bandage, 50));
    }

    public static Item create(ItemType type) {
        return prototypes.get(type).clone();
    }

    public static String getDescription(ItemType type) {
        return prototypes.get(type).description;
    }

    protected Item(ItemType type, String name, String description, int drawable, int weight) {
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

}
