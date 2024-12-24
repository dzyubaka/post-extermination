package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {

    static MainActivity instance;

    int sanity = 50,
            hunger = 50,
            thirst = 50,
            sleep = 50,
            toxins = 50,
            pain = 50;

    private final InventoryFragment inventoryFragment = new InventoryFragment();
    private final MapFragment mapFragment = new MapFragment();

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
        instance = this;
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, inventoryFragment)
                .add(R.id.fragment_container, mapFragment)
                .hide(mapFragment)
                .commit();

        ((BottomNavigationView) findViewById(R.id.bottom_navigation)).setOnItemSelectedListener(item -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (item.getItemId() == R.id.inventory) {
                transaction.hide(mapFragment);
            } else if (item.getItemId() == R.id.map) {
                transaction.show(mapFragment);
            }
            transaction.commit();
            return true;
        });
    }

    public void updateIndicators() {
        ((LinearProgressIndicator) findViewById(R.id.sanity)).setProgress(sanity);
        ((LinearProgressIndicator) findViewById(R.id.hunger)).setProgress(hunger);
        ((LinearProgressIndicator) findViewById(R.id.thirst)).setProgress(thirst);
        ((LinearProgressIndicator) findViewById(R.id.sleep)).setProgress(sleep);
        ((LinearProgressIndicator) findViewById(R.id.toxins)).setProgress(toxins);
        ((LinearProgressIndicator) findViewById(R.id.pain)).setProgress(pain);
    }
}