package ru.dzyubaka.postextermination;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Map;

import ru.dzyubaka.postextermination.fragment.AreaFragment;
import ru.dzyubaka.postextermination.fragment.CraftFragment;
import ru.dzyubaka.postextermination.fragment.HealthFragment;
import ru.dzyubaka.postextermination.fragment.InventoryFragment;
import ru.dzyubaka.postextermination.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {

    private final int SIZE = 501;

    private final Player player = new Player();
    private final Tile[][] tiles = new Tile[SIZE][SIZE];

    private LinearProgressIndicator sanityIndicator;
    private LinearProgressIndicator hungerIndicator;
    private LinearProgressIndicator thirstIndicator;
    private LinearProgressIndicator energyIndicator;
    private LinearProgressIndicator toxinsIndicator;
    private LinearProgressIndicator painIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sanityIndicator = findViewById(R.id.sanity);
        hungerIndicator = findViewById(R.id.hunger);
        thirstIndicator = findViewById(R.id.thirst);
        energyIndicator = findViewById(R.id.energy);
        toxinsIndicator = findViewById(R.id.toxins);
        painIndicator = findViewById(R.id.pain);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int chance = Utils.chance();

                if (chance < 1) {
                    tiles[i][j] = new Tile(TileType.MARKET, "Market", "It may contain some food.", R.drawable.shop, 5, Map.of(
                            ItemType.CANNED_BEANS, 10,
                            ItemType.WATER, 30,
                            ItemType.CHOCOLATE, 5,
                            ItemType.APPLE, 5,
                            ItemType.ROTTEN_APPLE, 5,
                            ItemType.ENERGY_DRINK, 5,
                            ItemType.CHIPS, 5,
                            ItemType.BREAD, 5
                    ));
                } else if (chance < 3) {
                    tiles[i][j] = new Tile(TileType.RUINS, "Ruins", "A house destroyed after an explosion.", R.drawable.ruins, 3, Map.of(
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
                                    if (shovel.use()) {
                                        player.inventory.remove(shovel);
                                    }
                                }
                            },
                            "Hands",
                            (player, tile, searchesLeft, search) -> {
                                search.accept(true);
                                boolean injury = false;

                                if (Utils.chance(10)) {
                                    player.bleeding.put(R.id.left_arm_bleeding, true);
                                    injury = true;
                                }

                                if (Utils.chance(10)) {
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
                } else if (chance < 5) {
                    tiles[i][j] = new Tile(TileType.HOUSE, "House", "The house that survived the explosion.", R.drawable.house, 3, Map.ofEntries(
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
                            Map.entry(ItemType.BREAD, 5)
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
                                    if (multitool.use()) {
                                        player.inventory.remove(multitool);
                                    }
                                }
                            },
                            "Break down",
                            (player, tile, searchesLeft, search) -> {
                                tile.searchesLeft += 2;
                                searchesLeft.setText(tile.searchesLeft + " searches left");
                                if (Utils.chance(30)) {
                                    player.fractures.put(R.id.right_arm_fracture, true);
                                    Toast.makeText(searchesLeft.getContext(), "You have suffered a new injury.", Toast.LENGTH_SHORT).show();
                                }
                            },
                            true,
                            ItemType.MULTITOOL
                    ));
                } else {
                    tiles[i][j] = new Tile(TileType.WASTELAND, "Wasteland", "There was vegetation here once.", R.drawable.wasteland, 10, Map.of(
                            ItemType.BRANCH, 50,
                            ItemType.STONE, 30
                    ));
                }
            }
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new InventoryFragment(player, tiles[player.position.y][player.position.x]))
                .commit();

        ((BottomNavigationView) findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.inventory) {
                fragment = new InventoryFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.map) {
                fragment = new MapFragment(player, tiles);
            } else if (id == R.id.craft) {
                fragment = new CraftFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.area) {
                fragment = new AreaFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.health) {
                fragment = new HealthFragment(player);
            }

            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
            return true;
        });

        updateIndicators();
    }

    public void updateIndicators() {
        sanityIndicator.setProgress(player.getSanity());
        hungerIndicator.setProgress(player.getHunger());
        thirstIndicator.setProgress(player.getThirst());
        energyIndicator.setProgress(player.getEnergy());
        toxinsIndicator.setProgress(player.getToxins());
        painIndicator.setProgress(player.getPain());

        if (player.getSanity() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("You're dead!")
                    .setMessage("You survived for " + player.getTurns() + " turns.")
                    .setCancelable(false)
                    .show();
        }
    }

}