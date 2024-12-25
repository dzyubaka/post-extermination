package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MapFragment extends Fragment {

    private final int halfViewport = 3;
    private GridLayout gridLayout;
    private final Player player;
    private final Tile[][] tiles;

    public MapFragment(Player player, Tile[][] tiles) {
        this.tiles = tiles;
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        gridLayout = view.findViewById(R.id.gridLayout);

        for (int y = player.position.y - halfViewport; y <= player.position.y + halfViewport; y++) {
            for (int x = player.position.x - halfViewport; x <= player.position.x + halfViewport; x++) {
                ImageView imageView = new ImageView(container.getContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.rowSpec = params.columnSpec;
                imageView.setLayoutParams(params);
                gridLayout.addView(imageView);
            }
        }

        updateMap();

        view.findViewById(R.id.left).setOnClickListener(v -> movePlayer(-1, 0));
        view.findViewById(R.id.right).setOnClickListener(v -> movePlayer(1, 0));
        view.findViewById(R.id.up).setOnClickListener(v -> movePlayer(0, -1));
        view.findViewById(R.id.down).setOnClickListener(v -> movePlayer(0, 1));

        return view;
    }

    private void movePlayer(int x, int y) {
        if (player.canWalk()) {
            player.position.x += x;
            player.position.y += y;
            player.action();
            ((MainActivity) getContext()).updateIndicators();
            updateMap();
        } else {
            Toast.makeText(getContext(), "Maximum weight exceeded", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMap() {
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                Tile tile = tiles[y + player.position.y - halfViewport][x + player.position.x - halfViewport];
                ImageView imageView = (ImageView) gridLayout.getChildAt(x + y * 7);
                imageView.setOnClickListener(view -> new AlertDialog.Builder(view.getContext())
                        .setTitle(tile.name)
                        .setMessage(tile.description)
                        .setNegativeButton("Close", null)
                        .show());
                imageView.setImageResource(tile.drawable);
            }
        }
    }
}