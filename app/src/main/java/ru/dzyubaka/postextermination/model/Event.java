package ru.dzyubaka.postextermination.model;

import android.widget.TextView;

import java.util.function.Consumer;

import ru.dzyubaka.postextermination.Player;

public class Event {

    public int chance;
    public String title;
    public String description;
    public String positiveText;
    public Action positiveAction;
    public String negativeText;
    public Action negativeAction;
    public boolean cancelable;
    public ItemType requirement;

    public Event(int chance,
                 String title,
                 String description,
                 String positiveText,
                 Action positiveAction,
                 String negativeText,
                 Action negativeAction,
                 boolean cancelable,
                 ItemType requirement) {
        this.chance = chance;
        this.title = title;
        this.description = description;
        this.positiveText = positiveText;
        this.positiveAction = positiveAction;
        this.negativeText = negativeText;
        this.negativeAction = negativeAction;
        this.cancelable = cancelable;
        this.requirement = requirement;
    }

    @FunctionalInterface
    public interface Action {
        void execute(Player player, Tile tile, TextView searchesLeft, Consumer<Boolean> search);
    }

}
