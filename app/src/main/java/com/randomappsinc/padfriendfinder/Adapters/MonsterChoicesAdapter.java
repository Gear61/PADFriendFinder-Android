package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 7/28/15.
 */
public class MonsterChoicesAdapter extends BaseAdapter
{
    private Context context;
    private List<String> monsterChoices;

    public MonsterChoicesAdapter(Context context, String monsterName)
    {
        this.context = context;
        this.monsterChoices = new ArrayList<>();
        this.monsterChoices.add(Constants.FIND_OTHER + " \"" + monsterName + "\"");
        this.monsterChoices.add(Constants.EDIT + " \"" + monsterName + "\"");
        this.monsterChoices.add(Constants.DELETE + " \"" + monsterName + "\"");
    }

    public int getCount() {
        return monsterChoices.size();
    }

    public String getItem(int position) {
        return monsterChoices.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        public TextView action;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        final ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.choices_dialog_item, parent, false);
            holder = new ViewHolder();
            holder.action = (TextView) v.findViewById(R.id.action);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        holder.action.setText(monsterChoices.get(position));

        return v;
    }
}
