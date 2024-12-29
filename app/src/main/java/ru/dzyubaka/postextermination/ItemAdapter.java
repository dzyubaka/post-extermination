package ru.dzyubaka.postextermination;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.dzyubaka.postextermination.fragment.InventoryFragment;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ArrayList<Item> items;
    private final boolean onFloor;
    private final Tile tile;
    private final Player player;
    private final Fragment fragment;

    public ItemAdapter(ArrayList<Item> items, boolean onFloor, Tile tile, Player player, Fragment fragment) {
        this.items = items;
        this.onFloor = onFloor;
        this.tile = tile;
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
        ((ImageView) holder.itemView).setImageResource(item.drawable);
        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                    .setTitle(item.getName())
                    .setMessage(item.getDescription())
                    .setNeutralButton("Close", null);

            if (onFloor) {
                if (item.weight > 0) {
                    builder.setPositiveButton("Take", (dialog, which) -> {
                        int index = holder.getAdapterPosition();
                        items.remove(index);
                        notifyItemRemoved(index);
                        player.inventory.add(item);
                    });
                }
            } else {
                builder.setNegativeButton("Drop", (dialog, which) -> {
                    int index = holder.getAdapterPosition();
                    items.remove(index);
                    notifyItemRemoved(index);
                    ((InventoryFragment) fragment).updateWeight();
                    tile.items.add(item);
                });

                if (item instanceof Food food) {
                    builder.setPositiveButton("Eat", (dialog, which) -> {
                        int index = holder.getAdapterPosition();
                        items.remove(index);
                        notifyItemRemoved(index);
                        ((InventoryFragment) fragment).updateWeight();
                        player.addHunger(food.hunger);
                        player.addThirst(food.thirst);
                        player.addToxins(food.toxins);
                        player.addEnergy(food.energy);
                        player.addPain(food.pain);
                        ((MainActivity) view.getContext()).updateIndicators();
                    });
                }
            }
            builder.show();
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
