package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageResource(R.drawable.wasteland);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
                params.rowSpec = params.columnSpec;
                params.setMargins(-3 * i, 0, 0, 0);
                imageView.setLayoutParams(params);
                layout.addView(imageView);
            }
        }
        return layout;
    }

}