package ru.dzyubaka.postextermination.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;
import java.util.function.Consumer;

import ru.dzyubaka.postextermination.Item;
import ru.dzyubaka.postextermination.ItemType;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.Tool;

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
                String injury = HealthFragment.this.getResources().getResourceEntryName(entry.getKey()).replace('_', ' ');
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(Character.toUpperCase(injury.charAt(0)) + injury.substring(1))
                        .setPositiveButton("Bandage", (dialog1, which) -> {
                            for (Item item : player.inventory) {
                                if (item.type == ItemType.BANDAGE) {
                                    player.inventory.remove(item);
                                    player.bleeding.put(entry.getKey(), false);
                                    view.setVisibility(View.GONE);
                                    break;
                                }
                            }
                        })
                        .setNegativeButton("Close", null)
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                for (Item item : player.inventory) {
                    if (item.type == ItemType.BANDAGE) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        break;
                    }
                }
            });
        };

        player.bleeding.entrySet().forEach(consumer);
        player.fractures.entrySet().forEach(consumer);

        return root;
    }

}