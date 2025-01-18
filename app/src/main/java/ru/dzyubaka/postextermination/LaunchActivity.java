package ru.dzyubaka.postextermination;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.dzyubaka.postextermination.model.Trait;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    private int points = 1;
    private final Trait[] traits = {
            new Trait("Survival Kit", 1),
            new Trait("Strong Back", 1),
            new Trait("Reduced Thirst", 2),
            new Trait("Strong Health", 3),
            new Trait("Injury", -1),
            new Trait("Fragile Health", -2),
            new Trait("Weak Stomach", -1),
            new Trait("Bad Fighter", -2)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ListView traitsList = findViewById(R.id.list);
        int resource = R.layout.trait;
        traitsList.setAdapter(new ArrayAdapter<>(this, resource, traits) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(resource, parent, false);
                if (position == traits.length / 2) {
                    view.setPadding(0, 100, 0, 0);
                }
                Trait trait = traits[position];
                ((CheckBox) view.findViewById(R.id.check)).setOnCheckedChangeListener((buttonView, isChecked) -> {
                    trait.checked = isChecked;
                    if (isChecked) {
                        points -= trait.points;
                    } else {
                        points += trait.points;
                    }
                    ((TextView) findViewById(R.id.points)).setText(points + " points");
                });
                ((TextView) view.findViewById(R.id.name)).setText(trait.name);
                ((TextView) view.findViewById(R.id.points)).setText(trait.points < 0 ? "+" + -trait.points : Integer.toString(-trait.points));
                return view;
            }
        });
        findViewById(R.id.continue_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (points < 0) {
            Toast.makeText(this, "Not enough trait points to start", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            for (Trait trait : traits) {
                intent.putExtra(trait.name, trait.checked);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}