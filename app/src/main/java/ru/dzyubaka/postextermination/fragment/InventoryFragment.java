package ru.dzyubaka.postextermination.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dzyubaka.postextermination.ItemAdapter;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.model.Tile;

public class InventoryFragment extends Fragment {

    private final Tile tile;
    private final Player player;

    private TextView weightTextView;

    public InventoryFragment(Player player, Tile tile) {
        this.tile = tile;
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        weightTextView = root.findViewById(R.id.weight);
        updateWeight();
        RecyclerView recycler = root.findViewById(R.id.recycler);
        recycler.setAdapter(new ItemAdapter(player.inventory, false, tile, player, this));
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 6));
        return root;
    }

    public void updateWeight() {
        weightTextView.setText("Weight: " + player.getWeight() / 1000f + " / " + player.maxWeight / 1000f + " kg");
    }
}