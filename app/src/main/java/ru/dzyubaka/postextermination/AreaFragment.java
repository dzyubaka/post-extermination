package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
    private final Random random = new Random();
    private ItemAdapter adapter;

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
        adapter = new ItemAdapter(tile.items, true, tile, player, this);
        view.findViewById(R.id.search).setOnClickListener(v -> {
            if (tile.searchesLeft > 0) {
                if (tile.name.equals("Ruins") && random.nextInt(100) < 20) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Blockage")
                            .setMessage("You've found a blockage. How do you want to dig it up?")
                            .setPositiveButton("Shovel", (dialog1, which) -> {
                                for (Item item : player.inventory) {
                                    if (item instanceof Tool tool && tool.type == ItemType.SHOVEL) {
                                        search(true);
                                        if (tool.use()) {
                                            player.inventory.remove(tool);
                                        }
                                        break;
                                    }
                                }
                            })
                            .setNegativeButton("Hands", (dialog2, which) -> search(true))
                            .setNeutralButton("Ignore", null)
                            .show();

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                    for (Item item : player.inventory) {
                        if (item.type == ItemType.SHOVEL) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                            break;
                        }
                    }
                } else {
                    player.action();
                    ((MainActivity) getContext()).updateIndicators();
                    search();
                }

                tile.searchesLeft--;
                searchesLeft.setText(tile.searchesLeft + " searches left");
            } else {
                Toast.makeText(v.getContext(), "There are no items left in this place.", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(container.getContext(), 6));
        recycler.setAdapter(adapter);
        return view;
    }

    private void search() {
        for (Map.Entry<ItemType, Integer> entry : tile.loot.entrySet()) {
            if (random.nextInt(100) < entry.getValue()) {
                tile.items.add(Item.create(entry.getKey()));
                adapter.notifyItemInserted(tile.items.size() - 1);
            }
        }
    }

    private void search(boolean multiple) {
        for (int i = 0; i < 5; i++) {
            search();
        }
    }

}