package ru.dzyubaka.postextermination.model;

import androidx.annotation.NonNull;

import java.util.HashMap;

import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.Utils;

public class Item implements Cloneable {

    private static final HashMap<ItemType, Item> prototypes = new HashMap<>();

    public final ItemType type;
    private final String description;
    public final int drawable;
    public int weight;

    static {
        prototypes.put(ItemType.CANNED_BEANS, new Item(ItemType.CANNED_BEANS, "Tin can of beans.", R.drawable.canned_beans, 400));
        prototypes.put(ItemType.BEANS, new Food(ItemType.BEANS, "Opened can of tasty beans.", R.drawable.beans, 400, -30, -10, 0));
        prototypes.put(ItemType.CHOCOLATE, new Food(ItemType.CHOCOLATE, "Milk chocolate plate.", R.drawable.chocolate, 100, -10, 0, 0));
        prototypes.put(ItemType.WATER, new Food(ItemType.WATER, "Bottle of water.", R.drawable.water, 500, 0, -30, -10));
        prototypes.put(ItemType.MULTITOOL, new Tool(ItemType.MULTITOOL, "Useful knife.", R.drawable.multitool, 250, 10));
        prototypes.put(ItemType.APPLE, new Food(ItemType.APPLE, "Fresh apple.", R.drawable.apple, 150, -15, -5, -5));
        prototypes.put(ItemType.ROTTEN_APPLE, new Food(ItemType.ROTTEN_APPLE, "Rotten apple.", R.drawable.rotten_apple, 150, -15, -5, 20));
        prototypes.put(ItemType.ENERGY_DRINK, new Food(ItemType.ENERGY_DRINK, "Energy drink.", R.drawable.energy_drink, 500, -5, -30, 0, 20));
        prototypes.put(ItemType.CHIPS, new Food(ItemType.CHIPS, "Potato chips.", R.drawable.chips, 150, -20, 5, 0));
        prototypes.put(ItemType.SHOVEL, new Tool(ItemType.SHOVEL, "Old, but of high quality.", R.drawable.shovel, 1500, 20));
        prototypes.put(ItemType.BANDAGE, new Item(ItemType.BANDAGE, "To stop the bleeding.", R.drawable.bandage, 50));
        prototypes.put(ItemType.PAINKILLERS, new Food(ItemType.PAINKILLERS, "They are able to completely remove the pain.", R.drawable.painkillers, 50, -100));
        prototypes.put(ItemType.BRANCH, new Item(ItemType.BRANCH, null, R.drawable.branch, 150));
        prototypes.put(ItemType.STONE, new Item(ItemType.STONE, null, R.drawable.stone, 500));
        prototypes.put(ItemType.MATCHES, new Tool(ItemType.MATCHES, null, R.drawable.matches, 20, 5));
        prototypes.put(ItemType.CAMPFIRE, new Item(ItemType.CAMPFIRE, null, R.drawable.campfire, 0));
        prototypes.put(ItemType.BREAD, new Food(ItemType.BREAD, null, R.drawable.bread, 300, -30, -5, 0));
        prototypes.put(ItemType.FLOUR, new Tool(ItemType.FLOUR, null, R.drawable.flour, 1000, 3));
        prototypes.put(ItemType.KNIFE, new Tool(ItemType.KNIFE, null, R.drawable.knife, 600,10));
    }

    public static Item create(ItemType type) {
        return prototypes.get(type).clone();
    }

    public static String getDescription(ItemType type) {
        return prototypes.get(type).description;
    }

    public static int getDrawable(ItemType type) {
        return prototypes.get(type).drawable;
    }

    protected Item(ItemType type, String description, int drawable, int weight) {
        this.type = type;
        this.description = description;
        this.drawable = drawable;
        this.weight = weight;
    }

    public String getName() {
        return Utils.title(type.name());
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
