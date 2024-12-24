package ru.dzyubaka.postextermination;

public class Item {

    public static final Item CANNED_BEANS = new Item("Canned beans", "Tin can of beans.", R.drawable.canned_beans);
    public static final Item BEANS = new Food("Beans", "Can of tasty beans.", R.drawable.beans, -30, -10, 0);
    public static final Item CHOCOLATE = new Food("Chocolate", "Milk chocolate plate.", R.drawable.chocolate, -10, 0, 0);
    public static final Item WATER = new Food("Water", "Bottle of water.", R.drawable.water, 0, -30, -5);
    public static final Item MULTITOOL = new Tool("Multitool", R.drawable.multitool, 10);

    String name;
    String description;
    int drawable;

    protected Item(String name, String description, int drawable) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
    }

}
