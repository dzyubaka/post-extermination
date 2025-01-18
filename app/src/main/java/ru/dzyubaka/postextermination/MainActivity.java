package ru.dzyubaka.postextermination;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

import ru.dzyubaka.postextermination.fragment.PlaceFragment;
import ru.dzyubaka.postextermination.fragment.CraftFragment;
import ru.dzyubaka.postextermination.fragment.HealthFragment;
import ru.dzyubaka.postextermination.fragment.InventoryFragment;
import ru.dzyubaka.postextermination.fragment.MapFragment;
import ru.dzyubaka.postextermination.model.Event;
import ru.dzyubaka.postextermination.model.Item;
import ru.dzyubaka.postextermination.model.ItemType;
import ru.dzyubaka.postextermination.model.Tile;
import ru.dzyubaka.postextermination.model.TileType;
import ru.dzyubaka.postextermination.model.Tool;

public class MainActivity extends AppCompatActivity {

    private final int SIZE = 501;

    private final Player player = new Player();
    private final Tile[][] tiles = new Tile[SIZE][SIZE];

    private ProgressBar sanityBar;
    private ProgressBar hungerBar;
    private ProgressBar thirstBar;
    private ProgressBar energyBar;
    private ProgressBar toxinsBar;
    private ProgressBar painBar;

    private float injuryMultiplier = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyTraits();

        sanityBar = findViewById(R.id.bar_sanity);
        hungerBar = findViewById(R.id.bar_hunger);
        thirstBar = findViewById(R.id.bar_thirst);
        energyBar = findViewById(R.id.bar_energy);
        toxinsBar = findViewById(R.id.bar_toxins);
        painBar = findViewById(R.id.bar_pain);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (Utils.chance() < 5) {
                    Map<TileType, Integer> chances = Map.of(
                            TileType.SHOP, 15,
                            TileType.RUINS, 50,
                            TileType.PHARMACY, 13,
                            TileType.TENT, 1,
                            TileType.MILITARY_BASE, 1,
                            TileType.HOUSE, 20
                    );
                    int sum = 0;
                    int chance = Utils.chance();
                    for (Map.Entry<TileType, Integer> entry : chances.entrySet()) {
                        sum += entry.getValue();
                        if (sum >= chance) {
                            if (entry.getKey() == TileType.SHOP) {
                                tiles[i][j] = new Tile(TileType.SHOP, "It may contain some food.", R.drawable.shop, 5, Map.of(
                                        ItemType.CANNED_BEANS, 10,
                                        ItemType.WATER, 30,
                                        ItemType.CHOCOLATE, 5,
                                        ItemType.APPLE, 5,
                                        ItemType.ROTTEN_APPLE, 5,
                                        ItemType.ENERGY_DRINK, 5,
                                        ItemType.CHIPS, 5,
                                        ItemType.BREAD, 5
                                ));
                            } else if (entry.getKey() == TileType.RUINS) {
                                tiles[i][j] = new Tile(TileType.RUINS, "A house destroyed after an explosion.", R.drawable.ruins, 3, Map.of(
                                        ItemType.ROTTEN_APPLE, 10,
                                        ItemType.SHOVEL, 1,
                                        ItemType.MULTITOOL, 1,
                                        ItemType.BANDAGE, 2,
                                        ItemType.PAINKILLERS, 2
                                ), new Event(
                                        20,
                                        "Blockage",
                                        "You've found a blockage. How do you want to dig it up?",
                                        "Shovel",
                                        (player, tile, searchesLeft, search) -> {
                                            Tool shovel = (Tool) player.get(ItemType.SHOVEL);
                                            if (shovel != null) {
                                                search.accept(true);
                                                shovel.use(player.inventory);
                                            }
                                        },
                                        "Hands",
                                        (player, tile, searchesLeft, search) -> {
                                            search.accept(true);
                                            boolean injury = false;

                                            if (Utils.chance((int) (10 * injuryMultiplier))) {
                                                player.bleeding.put(R.id.left_arm_bleeding, true);
                                                injury = true;
                                            }

                                            if (Utils.chance((int) (10 * injuryMultiplier))) {
                                                player.bleeding.put(R.id.right_arm_bleeding, true);
                                                injury = true;
                                            }

                                            if (injury) {
                                                Toast.makeText(searchesLeft.getContext(), "You have suffered a new injury.", Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        false,
                                        ItemType.SHOVEL
                                ));
                            } else if (entry.getKey() == TileType.PHARMACY) {
                                tiles[i][j] = new Tile(TileType.PHARMACY, "It can contain some medication", R.drawable.pharmacy, 3, Map.of(
                                        ItemType.BANDAGE, 20,
                                        ItemType.PAINKILLERS, 20
                                ));
                            } else if (entry.getKey() == TileType.TENT) {
                                tiles[i][j] = new Tile(TileType.TENT, "May contain some useful items for survival", R.drawable.tent, 1, Map.of(
                                        ItemType.TRAVEL_BACKPACK, 100,
                                        ItemType.CANNED_BEANS, 50,
                                        ItemType.WATER, 50,
                                        ItemType.CHOCOLATE, 50,
                                        ItemType.ENERGY_DRINK, 50
                                ));
                            } else if (entry.getKey() == TileType.MILITARY_BASE) {
                                tiles[i][j] = new Tile(TileType.MILITARY_BASE, null, R.drawable.military_base, 10, Map.of(
                                        ItemType.GUN, 10,
                                        ItemType.AMMO, 20,
                                        ItemType.WATER, 30,
                                        ItemType.RICE, 20,
                                        ItemType.FLOUR, 20
                                ));
                            } else if (entry.getKey() == TileType.HOUSE) {
                                tiles[i][j] = new Tile(TileType.HOUSE, "The house that survived the explosion.", R.drawable.house, 3, Map.ofEntries(
                                        Map.entry(ItemType.CANNED_BEANS, 10),
                                        Map.entry(ItemType.WATER, 20),
                                        Map.entry(ItemType.CHOCOLATE, 5),
                                        Map.entry(ItemType.APPLE, 5),
                                        Map.entry(ItemType.ROTTEN_APPLE, 5),
                                        Map.entry(ItemType.ENERGY_DRINK, 3),
                                        Map.entry(ItemType.CHIPS, 3),
                                        Map.entry(ItemType.SHOVEL, 1),
                                        Map.entry(ItemType.MULTITOOL, 1),
                                        Map.entry(ItemType.BANDAGE, 2),
                                        Map.entry(ItemType.PAINKILLERS, 2),
                                        Map.entry(ItemType.BREAD, 5),
                                        Map.entry(ItemType.SCHOOL_BACKPACK, 1)
                                ), new Event(
                                        10,
                                        "Locked door",
                                        "You've found a locked door. How do you want to open it?",
                                        "Multitool",
                                        (player, tile, searchesLeft, search) -> {
                                            tile.searchesLeft += 2;
                                            searchesLeft.setText(tile.searchesLeft + " searches left");
                                            Tool multitool = (Tool) player.get(ItemType.MULTITOOL);
                                            if (multitool != null) {
                                                multitool.use(player.inventory);
                                            }
                                        },
                                        "Break down",
                                        (player, tile, searchesLeft, search) -> {
                                            tile.searchesLeft += 2;
                                            searchesLeft.setText(tile.searchesLeft + " searches left");

                                            if (Utils.chance((int) (30 * injuryMultiplier))) {
                                                player.fractures.put(R.id.right_arm_fracture, true);
                                                Toast.makeText(searchesLeft.getContext(), "You have suffered a new injury.", Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        true,
                                        ItemType.MULTITOOL
                                ));
                            } else throw new RuntimeException();
                            break;
                        }
                    }
                } else {
                    tiles[i][j] = new Tile(TileType.WASTELAND, "There was vegetation here once.", View.NO_ID, 10, Map.of(
                            ItemType.BRANCH, 50,
                            ItemType.STONE, 10
                    ));
                }
            }
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, new InventoryFragment(player, tiles[player.position.y][player.position.x]))
                .commit();

        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.inventory) {
                fragment = new InventoryFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.map) {
                fragment = new MapFragment(player, tiles);
            } else if (id == R.id.craft) {
                fragment = new CraftFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.place) {
                fragment = new PlaceFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.health) {
                fragment = new HealthFragment(player);
            }

            transaction.replace(R.id.frame_layout, fragment);
            transaction.commit();
            return true;
        });

        updateIndicators();
    }

    private void applyTraits() {
        Bundle extras = getIntent().getExtras();

        if (extras.getBoolean("Survival Kit")) {
            player.inventory.add(Item.create(ItemType.MULTITOOL));
            player.inventory.add(Item.create(ItemType.MATCHES));
        }

        if (extras.getBoolean("Injury")) {
            Integer[] keys = player.fractures.keySet().toArray(new Integer[0]);
            player.fractures.put(keys[Utils.random(keys.length)], true);
        }

        if (extras.getBoolean("Reduced Thirst")) {
            player.thirstPerAction--;
        }

        if (extras.getBoolean("Strong Back")) {
            player.maxWeight += 2000;
        }

        if (extras.getBoolean("Fragile Health")) {
            injuryMultiplier *= 2;
        }

        if (extras.getBoolean("Strong Health")) {
            injuryMultiplier /= 2;
        }

        if (extras.getBoolean("Weak Stomach")) {
            player.toxinsMultiplier = 2;
        }
    }

    public void updateIndicators() {
        sanityBar.setProgress(player.getSanity());
        hungerBar.setProgress(player.getHunger());
        thirstBar.setProgress(player.getThirst());
        energyBar.setProgress(player.getEnergy());
        toxinsBar.setProgress(player.getToxins());
        painBar.setProgress(player.getPain());

        if (player.getSanity() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("You're dead!")
                    .setMessage("You survived for " + player.getTurns() + " turns.")
                    .setCancelable(false)
                    .show();
        }
    }

}