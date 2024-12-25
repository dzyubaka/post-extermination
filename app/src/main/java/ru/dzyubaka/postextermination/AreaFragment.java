package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Random;

public class AreaFragment extends Fragment {

    private final Player player;
    private final Tile tile;

    public AreaFragment(Player player, Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        ((TextView) view.findViewById(R.id.name)).setText(tile.name);
        TextView searchesLeft = view.findViewById(R.id.searchesLeft);
        searchesLeft.setText(tile.searchesLeft + " searches left");
        ItemAdapter adapter = new ItemAdapter(tile.items, true, tile, player);
        Random random = new Random();
        view.findViewById(R.id.search).setOnClickListener(v -> {
            if (tile.searchesLeft > 0) {
                player.action();
                ((MainActivity) getContext()).updateIndicators();
                tile.searchesLeft--;
                searchesLeft.setText(tile.searchesLeft + " searches left");

                for (Map.Entry<String, Integer> entry : tile.loot.entrySet()) {
                    if (random.nextInt(100) < entry.getValue()) {
                        tile.items.add(Item.create(entry.getKey()));
                        adapter.notifyItemInserted(tile.items.size() - 1);
                    }
                }

            } else {
                Toast.makeText(v.getContext(), "There are no items left in this place.", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(container.getContext(), 6));
        recycler.setAdapter(adapter);
        return view;
    }

}