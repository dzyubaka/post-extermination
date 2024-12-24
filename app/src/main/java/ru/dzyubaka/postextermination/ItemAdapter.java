package ru.dzyubaka.postextermination;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
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
                    .setTitle(item.name)
                    .setMessage(item.description)
                    .setNegativeButton("Drop", (dialog, which) -> {
                        int index = items.indexOf(item);
                        items.remove(index);
                        notifyItemRemoved(index);
                    })
                    .setNeutralButton("Close", null);
            if (item instanceof Food food) {
                builder.setPositiveButton("Eat", (dialog, which) -> {
                    int index = items.indexOf(item);
                    items.remove(index);
                    notifyItemRemoved(index);
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
