package ru.dzyubaka.postextermination;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class Player {

    int sanity = 50;
    int hunger = 50;
    int thirst = 50;
    int sleep = 50;
    int toxins = 50;
    int pain = 50;

    final Point position = new Point(24, 24);
    final ArrayList<Item> items = new ArrayList<>(List.of(Item.BEANS, Item.CHOCOLATE));

}
