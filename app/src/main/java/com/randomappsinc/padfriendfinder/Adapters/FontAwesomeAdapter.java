package com.randomappsinc.padfriendfinder.Adapters;

/**
 * Created by alexanderchiou on 9/15/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.randomappsinc.padfriendfinder.Models.FontAwesomeViewHolder;
import com.randomappsinc.padfriendfinder.R;

/**
 * Created by Alex on 1/18/2015.
 */
public class FontAwesomeAdapter extends BaseAdapter {
    private String[] tabNames;
    private String[] icons;
    private Context context;

    public FontAwesomeAdapter(Context context, String[] tabNames, String[] icons) {
        this.context = context;
        this.tabNames = tabNames;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public String getItem(int position) {
        return tabNames[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public View getView(int position, View view, ViewGroup parent) {
        FontAwesomeViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.font_awesome_list_item, parent, false);
            holder = new FontAwesomeViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (FontAwesomeViewHolder) view.getTag();
        }

        holder.itemName.setText(tabNames[position]);
        holder.itemIcon.setText(icons[position]);

        return view;
    }
}
