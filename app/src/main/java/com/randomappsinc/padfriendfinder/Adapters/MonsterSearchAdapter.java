package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class MonsterSearchAdapter extends ArrayAdapter<String> {
    private static final String[] DEFAULT_LEADERS = {"Marvelous Red Dragon Caller, Sonia",
                                                     "Sparkling Goddess of Secrets, Kali",
                                                     "Chaotic Flying General, Lu Bu",
                                                     "Guardian of the Sacred City, Athena",
                                                     "Keeper of the Sacred Texts, Metatron"};

    private ArrayList<String> items;
    private ArrayList<String> itemsAll;
    private ArrayList<String> suggestions;
    private MonsterServer monsterServer;

    private Context context;

    @SuppressWarnings("unchecked")
    public MonsterSearchAdapter(Context context, int viewResourceId, ArrayList<String> items) {
        super(context, viewResourceId, items);
        this.context = context;
        this.items = items;
        this.itemsAll = (ArrayList<String>) items.clone();
        this.suggestions = new ArrayList<>();
        monsterServer = MonsterServer.getMonsterServer();
    }

    public static class MonsterSuggestionViewHolder {
        @Bind(R.id.monster_icon) ImageView monsterIcon;
        @Bind(R.id.monster_name) TextView monsterName;

        public MonsterSuggestionViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        MonsterSuggestionViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.monster_ac_item, parent, false);
            holder = new MonsterSuggestionViewHolder(view);
            holder.monsterIcon = (ImageView) view.findViewById(R.id.monster_icon);
            holder.monsterName = (TextView) view.findViewById(R.id.monster_name);
            view.setTag(holder);
        }
        else {
            holder = (MonsterSuggestionViewHolder) view.getTag();
        }

        holder.monsterName.setText(items.get(position));
        Monster monster = monsterServer.getMonsterAttributes(items.get(position));
        Picasso.with(context).load(monster.getImageUrl()).into(holder.monsterIcon);
        return view;
    }

    @Override
    public android.widget.Filter getFilter()
    {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            return (resultValue).toString();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();

                int constraintLen = constraint.toString().length();
                if (constraintLen == 0) {
                    suggestions.addAll(Arrays.asList(DEFAULT_LEADERS));
                }
                else if (constraintLen > 1) {
                    for (int i = 0, j = 0; i < itemsAll.size() && j <= 10; i++) {
                        if (itemsAll.get(i).toLowerCase().contains(constraint.toString().toLowerCase())) {
                            j++;
                            suggestions.add(itemsAll.get(i));
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }
            else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            List<String> filteredList = (ArrayList<String>) results.values;
            if (results.count > 0) {
                for (String c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
