package ru.dzyubaka.postextermination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private int points = 1;

    private CheckBox survivalKit;
    private CheckBox injury;
    private CheckBox reducedThirst;
    private CheckBox strongBack;
    private CheckBox fragileHealth;
    private CheckBox strongHealth;
    private CheckBox weakStomach;
    private CheckBox badFighter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        findViewById(R.id.continue_button).setOnClickListener(this);

        survivalKit = findViewById(R.id.survival_kit);
        injury = findViewById(R.id.injury);
        reducedThirst = findViewById(R.id.reduced_thirst);
        strongBack = findViewById(R.id.strong_back);
        fragileHealth = findViewById(R.id.fragile_health);
        strongHealth = findViewById(R.id.strong_health);
        weakStomach = findViewById(R.id.weak_stomach);
        badFighter = findViewById(R.id.bad_fighter);

        survivalKit.setOnCheckedChangeListener(this);
        injury.setOnCheckedChangeListener(this);
        reducedThirst.setOnCheckedChangeListener(this);
        strongBack.setOnCheckedChangeListener(this);
        fragileHealth.setOnCheckedChangeListener(this);
        strongHealth.setOnCheckedChangeListener(this);
        weakStomach.setOnCheckedChangeListener(this);
        badFighter.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (points < 0) {
            Toast.makeText(this, "Not enough trait points to start", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("survival_kit", survivalKit.isChecked());
            intent.putExtra("injury", injury.isChecked());
            intent.putExtra("reduced_thirst", reducedThirst.isChecked());
            intent.putExtra("strong_back", strongBack.isChecked());
            intent.putExtra("fragile_health", fragileHealth.isChecked());
            intent.putExtra("strong_health", strongHealth.isChecked());
            intent.putExtra("weak_stomach", weakStomach.isChecked());
            intent.putExtra("bad_fighter", badFighter.isChecked());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.survival_kit) {
            if (isChecked) {
                points--;
            } else {
                points++;
            }
        } else if (buttonView.getId() == R.id.strong_back) {
            if (isChecked) {
                points--;
            } else {
                points++;
            }
        } else if (buttonView.getId() == R.id.reduced_thirst) {
            if (isChecked) {
                points -= 2;
            } else {
                points += 2;
            }
        } else if (buttonView.getId() == R.id.injury) {
            if (isChecked) {
                points++;
            } else {
                points--;
            }
        } else if (buttonView.getId() == R.id.strong_health) {
            if (isChecked) {
                points -= 3;
            } else {
                points += 3;
            }
        } else if (buttonView.getId() == R.id.fragile_health) {
            if (isChecked) {
                points += 2;
            } else {
                points -= 2;
            }
        } else if (buttonView.getId() == R.id.weak_stomach) {
            if (isChecked) {
                points++;
            } else {
                points--;
            }
        } else if (buttonView.getId() == R.id.bad_fighter) {
            if (isChecked) {
                points += 2;
            } else {
                points -= 2;
            }
        }

        ((TextView) findViewById(R.id.points)).setText(points + " points");
    }
}