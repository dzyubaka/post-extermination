package ru.dzyubaka.postextermination.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;
import java.util.function.Consumer;

import ru.dzyubaka.postextermination.model.Item;
import ru.dzyubaka.postextermination.model.ItemType;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.Utils;

public class HealthFragment extends Fragment {

    private final Player player;

    public HealthFragment(Player player) {
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_health, container, false);

        Consumer<Map.Entry<Integer, Boolean>> consumer = entry -> {
            View view = root.findViewById(entry.getKey());
            view.setVisibility(entry.getValue() ? View.VISIBLE : View.GONE);
            view.setOnClickListener(v -> {
                String injury = getResources().getResourceEntryName(entry.getKey());
                AlertDialog dialog = new AlertDialog.Builder(requireContext())
                        .setTitle(Utils.title(injury))
                        .setPositiveButton("Bandage", (dialog1, which) -> {
                            Item bandage = player.get(ItemType.BANDAGE);
                            if (bandage != null) {
                                player.inventory.remove(bandage);
                                player.bleeding.put(entry.getKey(), false);
                                view.setVisibility(View.GONE);
                            }
                        })
                        .setNegativeButton("Close", null)
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(player.get(ItemType.BANDAGE) != null);
            });
        };

        player.bleeding.entrySet().forEach(consumer);
        player.fractures.entrySet().forEach(consumer);

        return root;
    }

}