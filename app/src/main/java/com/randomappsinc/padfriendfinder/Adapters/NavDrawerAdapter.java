package com.randomappsinc.padfriendfinder.Adapters;

/**
 * Created by alexanderchiou on 9/15/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.randomappsinc.padfriendfinder.Models.FontAwesomeViewHolder;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

/**
 * Created by Alex on 1/18/2015.
 */
public class NavDrawerAdapter extends ArrayAdapter<String>
{
    private List<String> tabNames;
    private Context context;

    @SuppressWarnings("unchecked")
    public NavDrawerAdapter(Context context, int viewResourceId, List<String> tabNames) {
        super(context, viewResourceId, tabNames);
        this.context = context;
        this.tabNames = tabNames;
    }

    public View getView(int position, View view, ViewGroup parent) {
        FontAwesomeViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.font_awesome_list_item, parent, false);
            holder = new FontAwesomeViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (FontAwesomeViewHolder) view.getTag();
        }

        String tabName = tabNames.get(position);
        holder.itemName.setText(tabName);

        if (tabName.equals(context.getString(R.string.favorites)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.star_icon));
        }
        else if (tabName.equals(context.getString(R.string.supported_leads)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.support_icon));
        }
        else if (tabName.equals(context.getString(R.string.id_search)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.search_icon));
        }
        else if (tabName.equals(context.getString(R.string.settings)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.settings_icon));
        }
        return view;
    }
}
