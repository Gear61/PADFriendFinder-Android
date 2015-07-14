package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.randomappsinc.padfriendfinder.Misc.GodMapper;

import java.util.ArrayList;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class MonsterSearchAdapter extends ArrayAdapter<String>
{
    private ArrayList<String> items;
    private ArrayList<String> itemsAll;
    private ArrayList<String> suggestions;
    private GodMapper godMapper;

    private Context context;

    @SuppressWarnings("unchecked")
    public MonsterSearchAdapter(Context context, int viewResourceId, ArrayList<String> items)
    {
        super(context, viewResourceId, items);
        this.context = context;
        this.items = items;
        this.itemsAll = (ArrayList<String>) items.clone();
        this.suggestions = new ArrayList<>();
        godMapper = GodMapper.getGodMapper();
    }

    /* public static class ViewHolder
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
            MonsterAttributes monsterAttributes = godMapper.getMonsterAttributes(monsterName);
            holder.monsterName.setText(monsterName.trim());
            holder.monsterIcon.setImageResource(monsterAttributes.getDrawableID());
        }
        return v;
    }

    @Override
    public android.widget.Filter getFilter()
    {
        return nameFilter;
    }

    @SuppressLint("DefaultLocale")
    Filter nameFilter = new Filter()
    {
        public String convertResultToString(Object resultValue)
        {
            String str = (resultValue).toString();
            return str;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            if (constraint != null)
            {
                suggestions.clear();

                for (int i = 0, j = 0; i < itemsAll.size() && j <= 10; i++)
                {
                    if (itemsAll.get(i).toString().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        j++;
                        suggestions.add(itemsAll.get(i));
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
    }; */
}
