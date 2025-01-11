package ru.dzyubaka.postextermination;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.dzyubaka.postextermination.fragment.InventoryFragment;
import ru.dzyubaka.postextermination.model.Equipment;
import ru.dzyubaka.postextermination.model.Food;
import ru.dzyubaka.postextermination.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    /**
     * Main rendering list. It becomes player items from inventory and tile items from place.
     */
    private final ArrayList<Item> items;
    /**
     * Not null only if main list is player items.
     */
    private final ArrayList<Item> tileItems;
    private final Player player;
    private final Fragment fragment;

    public ItemAdapter(ArrayList<Item> items, ArrayList<Item> tileItems, Player player, Fragment fragment) {
        this.items = items;
        this.tileItems = tileItems;
        this.player = player;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = 200;
        imageView.setLayoutParams(params);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        ImageView imageView = (ImageView) holder.itemView;
        imageView.setImageResource(item.drawable);

        if (item instanceof Equipment equipment && equipment.equipped) {
            imageView.setBackgroundColor(Color.GREEN);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                    .setTitle(item.getName())
                    .setMessage(item.getDescription())
                    .setNeutralButton("Close", null);

            if (tileItems != null) {
                builder.setNegativeButton("Drop", (dialog, which) -> {
                    int index = holder.getAdapterPosition();
                    items.remove(index);
                    notifyItemRemoved(index);
                    ((InventoryFragment) fragment).updateWeight();
                    tileItems.add(item);
                });

                if (item instanceof Food food) {
                    builder.setPositiveButton("Eat", (dialog, which) -> {
                        int index = holder.getAdapterPosition();
                        items.remove(index);
                        notifyItemRemoved(index);
                        ((InventoryFragment) fragment).updateWeight();
                        player.addHunger(food.hunger);
                        player.addThirst(food.thirst);
                        player.addToxins(food.toxins * player.toxinsMultiplier);
                        player.addEnergy(food.energy);
                        player.addPain(food.pain);
                        ((MainActivity) view.getContext()).updateIndicators();
                    });
                } else if (item instanceof Equipment selectedEquipment) {
                    if (selectedEquipment.equipped) {
                        builder.setPositiveButton("Unequip", (dialog, which) -> {
                            selectedEquipment.equipped = false;
                            notifyItemChanged(holder.getAdapterPosition());
                            ((InventoryFragment) fragment).updateWeight();
                        });
                    } else {
                        builder.setPositiveButton("Equip", (dialog, which) -> {
                            for (int i = 0; i < player.inventory.size(); i++) {
                                if (player.inventory.get(i) instanceof Equipment equipment &&
                                        equipment.equipmentType == selectedEquipment.equipmentType && equipment.equipped) {
                                    equipment.equipped = false;
                                    notifyItemChanged(i);
                                    break;
                                }
                            }
                            selectedEquipment.equipped = true;
                            notifyItemChanged(holder.getAdapterPosition());
                            ((InventoryFragment) fragment).updateWeight();
                        });
                    }
                }
            } else {
                if (item.weight > 0) {
                    builder.setPositiveButton("Take", (dialog, which) -> {
                        int index = holder.getAdapterPosition();
                        items.remove(index);
                        notifyItemRemoved(index);
                        player.inventory.add(item);
                    });
                }
            }
            AlertDialog dialog = builder.show();
            if (item instanceof Equipment equipment && equipment.equipped) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
