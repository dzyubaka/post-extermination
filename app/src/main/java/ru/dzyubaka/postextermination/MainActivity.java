package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Map;
import java.util.Random;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        Random random = new Random();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int chance = random.nextInt(100);

                if (chance < 1) {
                    Map<ItemType, Integer> loot = Map.of(
                            ItemType.CANNED_BEANS, 10,
                            ItemType.WATER, 20,
                            ItemType.CHOCOLATE, 5,
                            ItemType.APPLE, 5,
                            ItemType.ROTTEN_APPLE, 5,
                            ItemType.ENERGY_DRINK, 5,
                            ItemType.CHIPS, 5
                    );
                    tiles[i][j] = new Tile("Market", "Product market", R.drawable.shop, 5, loot);
                } else if (chance < 3) {
                    Map<ItemType, Integer> loot = Map.of(
                            ItemType.ROTTEN_APPLE, 10,
                            ItemType.SHOVEL, 1,
                            ItemType.MULTITOOL, 1,
                            ItemType.BANDAGE, 2
                    );
                    tiles[i][j] = new Tile("Ruins", "Ruins", R.drawable.ruins, 3, loot);
                } else if (chance < 5) {
                    Map<ItemType, Integer> loot = Map.of(
                            ItemType.CANNED_BEANS, 10,
                            ItemType.WATER, 20,
                            ItemType.CHOCOLATE, 5,
                            ItemType.APPLE, 5,
                            ItemType.ROTTEN_APPLE, 5,
                            ItemType.ENERGY_DRINK, 3,
                            ItemType.CHIPS, 3,
                            ItemType.SHOVEL, 1,
                            ItemType.MULTITOOL, 1,
                            ItemType.BANDAGE, 2
                    );
                    tiles[i][j] = new Tile("House", "House", R.drawable.house, 3, loot);
                } else {
                    tiles[i][j] = new Tile("Wasteland", "Wasteland", R.drawable.wasteland, 0, null);
                }
            }
        }

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, new InventoryFragment(player, tiles[player.position.y][player.position.x]))
                .commit();

        ((BottomNavigationView) findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.inventory) {
                fragment = new InventoryFragment(player, tiles[player.position.y][player.position.x]);
            } else if (id == R.id.map) {
                fragment = new MapFragment(player, tiles);
            } else if (id == R.id.craft) {
                fragment = new CraftFragment(player.inventory);
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