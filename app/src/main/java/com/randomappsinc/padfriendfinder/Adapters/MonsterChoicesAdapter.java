package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.FontAwesomeViewHolder;
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
        this.monsterChoices.add(Constants.UPDATE + " \"" + monsterName + "\"");
        this.monsterChoices.add(Constants.SET + " \"" + monsterName + "\"" + " as your avatar");
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

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View view, ViewGroup parent)
    {
        FontAwesomeViewHolder holder;
        if (view == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.font_awesome_list_item, parent, false);
            holder = new FontAwesomeViewHolder(view);
            view.setTag(holder);
        }
        else
        {
            holder = (FontAwesomeViewHolder) view.getTag();
        }

        holder.itemName.setText(monsterChoices.get(position));
        String iconKey = null;
        switch (position)
        {
            case 0:
                iconKey = context.getString(R.string.search_icon);
                break;
            case 1:
                iconKey = context.getString(R.string.edit_icon);
                break;
            case 2:
                iconKey = context.getString(R.string.choose_avatar_icon);
                break;
            case 3:
                iconKey = context.getString(R.string.delete_icon);
                break;
        }
        holder.itemIcon.setIcon(iconKey);

        float scale = context.getResources().getDisplayMetrics().density;
        int fiveDp = (int) (5 * scale + 0.5f);
        int tenDp = (int) (10 * scale + 0.5f);
        view.setPadding(fiveDp, fiveDp, tenDp, fiveDp);
        return view;
    }
}
