package ru.dzyubaka.postextermination;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Player player = new Player();
    private final Tile[][] tiles = new Tile[49][49];

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        Random random = new Random();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (random.nextInt(10) == 0) {
                    tiles[i][j] = new Tile("House", "House", R.drawable.house, 3, Map.of(Item.BEANS, 10, Item.WATER, 20, Item.CHOCOLATE, 5));
                } else {
                    tiles[i][j] = new Tile("Wasteland", "Wasteland", R.drawable.wasteland, 0, null);
                }
            }
        }

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, new InventoryFragment(player.items, tiles[player.position.y][player.position.x], player))
                .commit();

        ((BottomNavigationView) findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            int id = item.getItemId();
            Fragment fragment;

            if (id == R.id.inventory) {
                fragment = new InventoryFragment(player.items, tiles[player.position.y][player.position.x], player);
            } else if (id == R.id.map) {
                fragment = new MapFragment(tiles, player.position);
            } else if (id == R.id.area) {
                fragment = new AreaFragment(tiles[player.position.y][player.position.x], player);
            } else {
                Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                return false;
            }

            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
            return true;
        });
    }

    public void updateIndicators() {
        ((LinearProgressIndicator) findViewById(R.id.sanity)).setProgress(player.sanity);
        ((LinearProgressIndicator) findViewById(R.id.hunger)).setProgress(player.hunger);
        ((LinearProgressIndicator) findViewById(R.id.thirst)).setProgress(player.thirst);
        ((LinearProgressIndicator) findViewById(R.id.sleep)).setProgress(player.sleep);
        ((LinearProgressIndicator) findViewById(R.id.toxins)).setProgress(player.toxins);
        ((LinearProgressIndicator) findViewById(R.id.pain)).setProgress(player.pain);
    }

}