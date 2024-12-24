package ru.dzyubaka.postextermination;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> items = new ArrayList<>(List.of(
            new Item("Pea", "Tin can of pea.", R.drawable.pea),
            new Food("Chocolate", "Dark 70% chocolate plate.", R.drawable.chocolate, -10, 0)
    ));

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setPadding(0, 32, 0, 0);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        ((ImageView) holder.itemView).setImageResource(item.sprite);
        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                    .setTitle(item.name)
                    .setMessage(item.description)
                    .setNegativeButton("Drop", (dialog, which) -> {
                        items.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNeutralButton("Close", null);
            if (item instanceof Food food) {
                builder.setPositiveButton("Eat", (dialog, which) -> {
                    items.remove(position);
                    notifyItemRemoved(position);
                    MainActivity.instance.hunger += food.hunger;
                    MainActivity.instance.thirst += food.thirst;
                    MainActivity.instance.updateIndicators();
                });
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
