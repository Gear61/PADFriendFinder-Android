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
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class MonsterSearchAdapter extends ArrayAdapter<String>
{
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
    public MonsterSearchAdapter(Context context, int viewResourceId, ArrayList<String> items)
    {
        super(context, viewResourceId, items);
        this.context = context;
        this.items = items;
        this.itemsAll = (ArrayList<String>) items.clone();
        this.suggestions = new ArrayList<>();
        monsterServer = MonsterServer.getMonsterServer();
    }

    public static class ViewHolder
    {
        public ImageView monsterIcon;
        public TextView monsterName;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.monster_ac_item, null);
            holder = new ViewHolder();
            holder.monsterIcon = (ImageView) v.findViewById(R.id.monster_icon);
            holder.monsterName = (TextView) v.findViewById(R.id.monster_name);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)v.getTag();
        }

        final String monsterName = (items.get(position)).toString();
        if (monsterName != null)
        {
            holder.monsterName.setText(monsterName.trim());
            MonsterAttributes monsterAttributes = monsterServer.getMonsterAttributes(monsterName);
            holder.monsterName.setText(monsterName.trim());
            Picasso.with(context).load(monsterAttributes.getImageUrl()).into(holder.monsterIcon);
        }
        return v;
    }

    @Override
    public android.widget.Filter getFilter()
    {
        return nameFilter;
    }

    Filter nameFilter = new Filter()
    {
        public String convertResultToString(Object resultValue)
        {
            String str = (resultValue).toString();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            if (constraint != null)
            {
                suggestions.clear();

                int constraintLen = constraint.toString().length();
                if (constraintLen == 0)
                {
                    suggestions.addAll(Arrays.asList(DEFAULT_LEADERS));
                }
                else if (constraintLen > 1)
                {
                    for (int i = 0, j = 0; i < itemsAll.size() && j <= 10; i++)
                    {
                        if (itemsAll.get(i).toString().toLowerCase().contains(constraint.toString().toLowerCase()))
                        {
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
            else
            {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            clear();
            ArrayList<String> filteredList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0)
            {
                for (String c : filteredList)
                {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
