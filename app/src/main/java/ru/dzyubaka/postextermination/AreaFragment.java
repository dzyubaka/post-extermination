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

public class AreaFragment extends Fragment {

    private final Tile tile;
    private final Player player;

    public AreaFragment(Tile tile, Player player) {
        this.tile = tile;
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        ((TextView) view.findViewById(R.id.name)).setText(tile.name);
        TextView searchesLeft = view.findViewById(R.id.searchesLeft);
        searchesLeft.setText(tile.searchesLeft + " searches left");
        ItemAdapter adapter = new ItemAdapter(tile.items, true, tile, player);
        view.findViewById(R.id.search).setOnClickListener(v -> {
            if (tile.searchesLeft > 0) {
                tile.searchesLeft--;
                searchesLeft.setText(tile.searchesLeft + " searches left");
                tile.items.add(Item.BEANS);
                adapter.notifyItemInserted(tile.items.size() - 1);
            } else {
                Toast.makeText(v.getContext(), "There are no any items left in this place.", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(container.getContext(), 6));
        recycler.setAdapter(adapter);
        return view;
    }

}