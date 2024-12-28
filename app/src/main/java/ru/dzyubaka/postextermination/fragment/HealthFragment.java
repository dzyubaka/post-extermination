package ru.dzyubaka.postextermination.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;

public class HealthFragment extends Fragment {

    private final Player player;

    public HealthFragment(Player player) {
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_health, container, false);

        for (Map.Entry<Integer, Boolean> entry : player.injuries.entrySet()) {
            View view = root.findViewById(entry.getKey());
            view.setVisibility(entry.getValue() ? View.VISIBLE : View.GONE);
            view.setOnClickListener(v -> {
                String injury = getResources().getResourceEntryName(entry.getKey()).replace('_', ' ');
                new AlertDialog.Builder(getContext())
                        .setTitle(Character.toUpperCase(injury.charAt(0)) + injury.substring(1))
                        .setNegativeButton("Close", null)
                        .show();
            });
        }

        return root;
    }

}