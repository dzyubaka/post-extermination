package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HealthFragment extends Fragment {

    private final Player player;

    public HealthFragment(Player player) {
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        view.findViewById(R.id.head_bleeding).setVisibility(player.headBleeding ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.body_bleeding).setVisibility(player.bodyBleeding ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.left_arm_bleeding).setVisibility(player.leftArmBleeding ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.left_arm_fracture).setVisibility(player.leftArmFracture ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.right_hand_bleeding).setVisibility(player.rightHandBleeding ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.right_hand_fracture).setVisibility(player.rightHandFracture ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.left_leg_bleeding).setVisibility(player.leftLegBleeding ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.left_leg_fracture).setVisibility(player.leftLegFracture ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.right_leg_bleeding).setVisibility(player.rightLegBleeding ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.right_leg_fracture).setVisibility(player.rightLegFracture ? View.VISIBLE : View.GONE);

        return view;
    }

}