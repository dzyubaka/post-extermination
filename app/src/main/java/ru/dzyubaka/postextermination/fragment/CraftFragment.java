package ru.dzyubaka.postextermination.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import ru.dzyubaka.postextermination.Craft;
import ru.dzyubaka.postextermination.Item;
import ru.dzyubaka.postextermination.ItemType;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.Tile;
import ru.dzyubaka.postextermination.Tool;

public class CraftFragment extends Fragment {

    private final Player player;
    private final Tile tile;
    private final ArrayList<Craft> possibleCrafts = new ArrayList<>();

    private final Craft[] crafts = {
            new Craft("Open canned beans", ItemType.CANNED_BEANS, ItemType.MULTITOOL, ItemType.BEANS),
            new Craft(null, ItemType.BRANCH, ItemType.MATCHES, ItemType.CAMPFIRE),
            new Craft(null, ItemType.WATER, ItemType.FLOUR, ItemType.BREAD, ItemType.CAMPFIRE)
    };

    public CraftFragment(Player player, Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView list = (ListView) inflater.inflate(R.layout.fragment_craft, container, false);
        updatePossibleCrafts();
        int layout = R.layout.item_craft;

        list.setAdapter(new ArrayAdapter<>(container.getContext(), layout, possibleCrafts) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = LayoutInflater.from(requireContext()).inflate(layout, parent, false);
                Craft craft = possibleCrafts.get(position);
                ((ImageView) view.findViewById(R.id.left_item)).setImageResource(Item.getDrawable(craft.leftItem));
                ((ImageView) view.findViewById(R.id.right_item)).setImageResource(Item.getDrawable(craft.rightItem));
                ((ImageView) view.findViewById(R.id.result)).setImageResource(Item.getDrawable(craft.result));
                view.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                        .setTitle(craft.description)
                        .setMessage(Item.getDescription(ItemType.BEANS))
                        .setPositiveButton("Craft", (dialog, which) -> {
                            player.inventory.remove(player.get(craft.leftItem));
                            Tool tool = (Tool) player.get(craft.rightItem);
                            Item result = Item.create(craft.result);
                            tool.use(player.inventory);

                            if (result.weight > 0) {
                                player.inventory.add(result);
                            } else {
                                tile.items.add(result);
                            }

                            updatePossibleCrafts();
                            notifyDataSetChanged();
                        })
                        .setNegativeButton("Close", null)
                        .show());
                return view;
            }
        });
        return list;
    }

    private void updatePossibleCrafts() {
        possibleCrafts.clear();

        for (Craft craft : crafts) {
            boolean meets = craft.requirement == null;

            for (Item item : tile.items) {
                if (item.type == craft.requirement) {
                    meets = true;
                    break;
                }
            }

            if (player.get(craft.leftItem) != null && player.get(craft.rightItem) != null && meets) {
                possibleCrafts.add(craft);
            }
        }
    }

}