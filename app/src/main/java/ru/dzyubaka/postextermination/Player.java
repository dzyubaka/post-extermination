package ru.dzyubaka.postextermination;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class Player {

    int sanity = 100;
    int hunger = 10;
    int thirst = 10;
    int energy = 100;
    int toxins = 0;
    int pain = 0;

    final Point position = new Point(24, 24);
    final ArrayList<Item> items = new ArrayList<>(List.of(Item.BEANS, Item.CHOCOLATE));

    public void action() {
        hunger++;
        thirst++;
        energy--;

        if (hunger <= 20 && thirst <= 20 && energy >= 80 && toxins <= 20 && pain <= 20 && sanity < 100) {
            sanity++;
        }

        if (hunger >= 100) sanity--;
        if (thirst >= 100) sanity--;
        if (energy <= 0) sanity--;
        if (toxins >= 50) sanity--;
        if (toxins >= 100) sanity--;
        if (pain >= 50) sanity--;
        if (pain >= 100) sanity--;

        sanity = Math.min(Math.max(sanity, 0), 100);
        hunger = Math.min(Math.max(hunger, 0), 100);
        thirst = Math.min(Math.max(thirst, 0), 100);
        energy = Math.min(Math.max(energy, 0), 100);
        toxins = Math.min(Math.max(toxins, 0), 100);
        pain = Math.min(Math.max(pain, 0), 100);
    }
}
