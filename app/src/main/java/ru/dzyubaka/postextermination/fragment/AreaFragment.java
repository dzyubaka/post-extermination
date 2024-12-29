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

import ru.dzyubaka.postextermination.model.Event;
import ru.dzyubaka.postextermination.model.Item;
import ru.dzyubaka.postextermination.ItemAdapter;
import ru.dzyubaka.postextermination.model.ItemType;
import ru.dzyubaka.postextermination.MainActivity;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.model.Tile;
import ru.dzyubaka.postextermination.Utils;

public class AreaFragment extends Fragment {

    private final Player player;
    private final Tile tile;
    private ItemAdapter adapter;
    private TextView searchesLeft;

    public AreaFragment(Player player, Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        ((TextView) view.findViewById(R.id.name)).setText(tile.getName());
        searchesLeft = view.findViewById(R.id.searchesLeft);
        searchesLeft.setText(tile.searchesLeft + " searches left");
        adapter = new ItemAdapter(tile.items, null, player, this);
        view.findViewById(R.id.search).setOnClickListener(v -> {
            if (tile.searchesLeft > 0) {
                boolean occurred = false;
                if (tile.event != null) {
                    occurred = handle(tile.event);
                }

                if (!occurred) {
                    player.action(requireContext());
                    ((MainActivity) requireContext()).updateIndicators();
                    search();
                }

                tile.searchesLeft--;
                searchesLeft.setText(tile.searchesLeft + " searches left");
            } else {
                Toast.makeText(v.getContext(), "There are no items left in this place.", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new

                GridLayoutManager(container.getContext(), 6));
        recycler.setAdapter(adapter);
        return view;
    }

    private void search() {
        for (Map.Entry<ItemType, Integer> entry : tile.loot.entrySet()) {
            if (Utils.chance(entry.getValue())) {
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

    private boolean handle(Event event) {
        if (Utils.chance(event.chance)) {
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setTitle(event.title)
                    .setMessage(event.description)
                    .setPositiveButton(event.positiveText, (dialog1, which) ->
                            event.positiveAction.execute(player, tile, searchesLeft, this::search))
                    .setNegativeButton(event.negativeText, (dialog2, which) ->
                            event.negativeAction.execute(player, tile, searchesLeft, this::search))
                    .setNeutralButton("Ignore", null)
                    .setCancelable(event.cancelable)
                    .show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(player.get(event.requirement) != null);
            return true;
        }
        return false;
    }

}