package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.randomappsinc.padfriendfinder.Models.FontAwesomeViewHolder;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

/**
 * Created by alexanderchiou on 9/18/15.
 */
public class SettingsAdapter extends ArrayAdapter<String>
{
    private List<String> optionNames;
    private Context context;

    @SuppressWarnings("unchecked")
    public SettingsAdapter(Context context, int viewResourceId, List<String> optionNames)
    {
        super(context, viewResourceId, optionNames);
        this.context = context;
        this.optionNames = optionNames;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        FontAwesomeViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.font_awesome_list_item, parent, false);
            holder = new FontAwesomeViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (FontAwesomeViewHolder) view.getTag();
        }

        String tabName = optionNames.get(position);
        holder.itemName.setText(tabName);

        if (tabName.equals(context.getString(R.string.change_pad_id)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.edit_icon));
        }
        else if (tabName.equals(context.getString(R.string.choose_avatar)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.choose_avatar_icon));
        }
        else if (tabName.equals(context.getString(R.string.rate_us)))
        {
            holder.itemIcon.setIcon(context.getString(R.string.rate_us_icon));
        }
        return view;
    }
}
