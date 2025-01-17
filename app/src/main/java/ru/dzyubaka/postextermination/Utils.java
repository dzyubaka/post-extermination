package ru.dzyubaka.postextermination;

import java.util.Random;

public class Utils {

    private final static Random RANDOM = new Random();

    public static boolean chance(int percents) {
        return chance() < percents;
    }

    public static int chance() {
        return random(100);
    }

    public static int random(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static String title(String text) {
        return (Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase()).replace('_', ' ');
    }
}
