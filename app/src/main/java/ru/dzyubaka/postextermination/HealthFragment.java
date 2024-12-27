package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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

        View headBleeding = view.findViewById(R.id.head_bleeding);
        View bodyBleeding = view.findViewById(R.id.body_bleeding);
        View leftArmBleeding = view.findViewById(R.id.left_arm_bleeding);
        View leftArmFracture = view.findViewById(R.id.left_arm_fracture);
        View rightHandBleeding = view.findViewById(R.id.right_hand_bleeding);
        View rightHandFracture = view.findViewById(R.id.right_hand_fracture);
        View leftLegBleeding = view.findViewById(R.id.left_leg_bleeding);
        View leftLegFracture = view.findViewById(R.id.left_leg_fracture);
        View rightLegBleeding = view.findViewById(R.id.right_leg_bleeding);
        View rightLegFracture = view.findViewById(R.id.right_leg_fracture);

        headBleeding.setVisibility(player.headBleeding ? View.VISIBLE : View.GONE);
        bodyBleeding.setVisibility(player.bodyBleeding ? View.VISIBLE : View.GONE);
        leftArmBleeding.setVisibility(player.leftArmBleeding ? View.VISIBLE : View.GONE);
        leftArmFracture.setVisibility(player.leftArmFracture ? View.VISIBLE : View.GONE);
        rightHandBleeding.setVisibility(player.rightHandBleeding ? View.VISIBLE : View.GONE);
        rightHandFracture.setVisibility(player.rightHandFracture ? View.VISIBLE : View.GONE);
        leftLegBleeding.setVisibility(player.leftLegBleeding ? View.VISIBLE : View.GONE);
        leftLegFracture.setVisibility(player.leftLegFracture ? View.VISIBLE : View.GONE);
        rightLegBleeding.setVisibility(player.rightLegBleeding ? View.VISIBLE : View.GONE);
        rightLegFracture.setVisibility(player.rightLegFracture ? View.VISIBLE : View.GONE);

        headBleeding.setOnClickListener(v -> message("Head bleeding"));
        bodyBleeding.setOnClickListener(v -> message("Body bleeding"));
        leftArmBleeding.setOnClickListener(v -> message("Left arm bleeding"));
        leftArmFracture.setOnClickListener(v -> message("Left arm fracture"));
        rightHandBleeding.setOnClickListener(v -> message("Right hand bleeding"));
        rightHandFracture.setOnClickListener(v -> message("Right hand fracture"));
        leftLegBleeding.setOnClickListener(v -> message("Left leg bleeding"));
        leftLegFracture.setOnClickListener(v -> message("Left leg fracture"));
        rightLegBleeding.setOnClickListener(v -> message("Right leg bleeding"));
        rightLegFracture.setOnClickListener(v -> message("Right leg fracture"));

        return view;
    }

    public void message(String injury) {
        new AlertDialog.Builder(getContext())
                .setTitle(injury)
                .setNegativeButton("Close", null)
                .show();
    }
}