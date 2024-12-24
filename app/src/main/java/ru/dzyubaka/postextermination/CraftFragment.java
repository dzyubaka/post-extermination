package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CraftFragment extends Fragment {

    ArrayList<Item> inventory;

    public CraftFragment(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView list = (ListView) inflater.inflate(R.layout.fragment_craft, container, false);
        if (inventory.contains(Item.CANNED_BEANS) && inventory.contains(Item.MULTITOOL)) {
            list.setAdapter(new ArrayAdapter<>(container.getContext(), R.layout.item_craft, new String[1]) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_craft, parent, false);
                    view.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                            .setTitle("Open canned beans")
                            .setMessage(Item.BEANS.description)
                            .setPositiveButton("Craft", (dialog, which) -> {
                                inventory.remove(Item.CANNED_BEANS);
                                ((Tool) inventory.get(inventory.indexOf(Item.MULTITOOL))).use();
                                inventory.add(Item.BEANS);
                                list.setAdapter(null);
                            })
                            .setNegativeButton("Close", null)
                            .show());
                    return view;
                }
            });
        }
        return list;
    }
}