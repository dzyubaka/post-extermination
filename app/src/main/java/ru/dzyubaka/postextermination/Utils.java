package ru.dzyubaka.postextermination;

import java.util.Random;

public class Utils {

    public final static Random random = new Random();

    public static boolean chance(int percents) {
        return chance() < percents;
    }

    public static int chance() {
        return random.nextInt(100);
    }
}
