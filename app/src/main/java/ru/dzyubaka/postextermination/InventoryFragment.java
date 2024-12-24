package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private ArrayList<Item> items;
    private final Tile tile;
    private final Player player;

    public InventoryFragment(ArrayList<Item> items, Tile tile, Player player) {
        this.items = items;
        this.tile = tile;
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        RecyclerView recycler = root.findViewById(R.id.recycler);
        recycler.setAdapter(new ItemAdapter(items, false, tile, player));
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 6));
        return root;
    }

}