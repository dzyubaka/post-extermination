package ru.dzyubaka.postextermination.model;

public class Equipment extends Item {

    public boolean equipped;
    public EquipmentType equipmentType;
    public int capacity;
    public int armor;

    protected Equipment(ItemType type, String description, int drawable, int weight, EquipmentType equipmentType) {
        super(type, description, drawable, weight);
        this.equipmentType = equipmentType;
    }

    protected Equipment(ItemType type, String description, int drawable, int weight, EquipmentType equipmentType, int capacity) {
        super(type, description, drawable, weight);
        this.equipmentType = equipmentType;
        this.capacity = capacity;
    }

    protected Equipment(ItemType type, String description, int drawable, int weight, EquipmentType equipmentType, int capacity, int armor) {
        super(type, description, drawable, weight);
        this.equipmentType = equipmentType;
        this.capacity = capacity;
        this.armor = armor;
    }
}
