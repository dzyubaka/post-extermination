package ru.dzyubaka.postextermination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InventoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        RecyclerView recycler = root.findViewById(R.id.recycler);
        recycler.setAdapter(new ItemAdapter());
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 6));
        return root;
    }

}