package ru.dzyubaka.postextermination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.continue_button).setOnClickListener(this);

        survivalKit = findViewById(R.id.survival_kit);
        injury = findViewById(R.id.injury);
        reducedThirst = findViewById(R.id.reduced_thirst);
        strongBack = findViewById(R.id.strongBack);

        survivalKit.setOnCheckedChangeListener(this);
        injury.setOnCheckedChangeListener(this);
        reducedThirst.setOnCheckedChangeListener(this);
        strongBack.setOnCheckedChangeListener(this);
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
        } else if (buttonView.getId() == R.id.strongBack) {
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
        }

        ((TextView) findViewById(R.id.points)).setText(points + " points");
    }
}