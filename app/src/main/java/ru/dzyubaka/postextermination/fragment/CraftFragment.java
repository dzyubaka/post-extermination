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
import android.widget.TextView;

import java.util.ArrayList;

import ru.dzyubaka.postextermination.model.Craft;
import ru.dzyubaka.postextermination.model.Item;
import ru.dzyubaka.postextermination.model.ItemType;
import ru.dzyubaka.postextermination.Player;
import ru.dzyubaka.postextermination.R;
import ru.dzyubaka.postextermination.model.Tile;
import ru.dzyubaka.postextermination.model.Tool;

public class CraftFragment extends Fragment {

    private final Player player;
    private final Tile tile;
    private final ArrayList<Craft> possibleCrafts = new ArrayList<>();

    private final Craft[] crafts = {
            new Craft("Open canned beans", ItemType.CANNED_BEANS, 1, ItemType.MULTITOOL, ItemType.BEANS),
            new Craft(null, ItemType.BRANCH, 2, ItemType.MATCHES, ItemType.CAMPFIRE),
            new Craft(null, ItemType.WATER, 1, ItemType.FLOUR, ItemType.BREAD, ItemType.CAMPFIRE),
            new Craft(null, ItemType.BRANCH, 1, ItemType.STONE, ItemType.KNIFE)
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
                if (craft.count > 1) {
                    ((TextView) view.findViewById(R.id.count)).setText(craft.count + "x");
                }
                ((ImageView) view.findViewById(R.id.left_item)).setImageResource(Item.getDrawable(craft.leftItem));
                ((ImageView) view.findViewById(R.id.right_item)).setImageResource(Item.getDrawable(craft.rightItem));
                ((ImageView) view.findViewById(R.id.result)).setImageResource(Item.getDrawable(craft.result));
                view.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                        .setTitle(Item.getName(craft.result))
                        .setMessage(craft.description)
                        .setPositiveButton("Craft", (dialog, which) -> {
                            for (int i = 0; i < craft.count; i++) {
                                player.inventory.remove(player.get(craft.leftItem));
                            }

                            Item result = Item.create(craft.result);

                            if (player.get(craft.rightItem) instanceof Tool tool) {
                                tool.use(player.inventory);
                            } else {
                                player.inventory.remove(player.get(craft.rightItem));
                            }

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

            if (player.has(craft.leftItem, craft.count) && player.has(craft.rightItem) && meets) {
                possibleCrafts.add(craft);
            }
        }
    }

}