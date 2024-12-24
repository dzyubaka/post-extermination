package ru.dzyubaka.postextermination;

public class Item {

    public static final Item BEANS = new Item("Beans", "Tin can of beans.", R.drawable.beans);
    public static final Item CHOCOLATE = new Food("Chocolate", "Milk chocolate plate.", R.drawable.chocolate, -10, 0);
    public static final Item WATER = new Food("Water", "Bottle of water.", R.drawable.water, 0, -30);

    String name;
    String description;
    int drawable;

    protected Item(String name, String description, int drawable) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
    }

}
