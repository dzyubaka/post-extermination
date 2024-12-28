package ru.dzyubaka.postextermination.fragment;

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

import ru.dzyubaka.postextermination.Item;
import ru.dzyubaka.postextermination.ItemAdapter;
import ru.dzyubaka.postextermination.ItemType;
import ru.dzyubaka.postextermination.MainActivity;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.Tile;
import ru.dzyubaka.postextermination.Tool;

public class AreaFragment extends Fragment {

    private final Player player;
    private final Tile tile;
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
                if (tile.name.equals("Ruins") && MainActivity.chance(20)) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Blockage")
                            .setMessage("You've found a blockage. How do you want to dig it up?")
                            .setPositiveButton("Shovel", (dialog1, which) -> {
                                Tool shovel = (Tool) player.get(ItemType.SHOVEL);
                                if (shovel != null) {
                                    search(true);
                                    if (shovel.use()) {
                                        player.inventory.remove(shovel);
                                    }
                                }
                            })
                            .setNegativeButton("Hands", (dialog2, which) -> {
                                search(true);
                                boolean injury = false;

                                if (MainActivity.chance(10)) {
                                    player.bleeding.put(R.id.left_arm_bleeding, true);
                                    injury = true;
                                }

                                if (MainActivity.chance(10)) {
                                    player.bleeding.put(R.id.right_arm_bleeding, true);
                                    injury = true;
                                }

                                if (injury) {
                                    Toast.makeText(getContext(), "You have suffered a new injury.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNeutralButton("Ignore", null)
                            .setCancelable(false)
                            .show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(player.get(ItemType.SHOVEL) != null);
                } else {
                    player.action(getContext());
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
            if (MainActivity.chance(entry.getValue())) {
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