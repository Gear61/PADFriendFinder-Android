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
 * Created by jman0_000 on 10/11/2015.
 */
public class MonsterItemAdapter extends BaseAdapter {

    private Context context;
    private List<String> options;

    public MonsterItemAdapter(Context context, String monsterName)
    {
        this.context = context;
        this.options = new ArrayList<>();
        this.options.add(Constants.SEARCH + " \"" + monsterName + "\"");
        this.options.add(Constants.HYPERMAXD + " \"" + monsterName + "\"");
    }

    public int getCount() {
        return options.size();
    }

    public String getItem(int position) {
        return options.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

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

        holder.itemName.setText(getItem(position));
        String iconKey = null;
        switch (position)
        {
            case 0:
                iconKey = context.getString(R.string.search_icon);
                break;
            case 1:
                iconKey = context.getString(R.string.plus_icon);
                break;
        }
        holder.itemIcon.setText(iconKey);

        float scale = context.getResources().getDisplayMetrics().density;
        int fiveDp = (int) (5 * scale + 0.5f);
        int tenDp = (int) (10 * scale + 0.5f);
        view.setPadding(fiveDp, fiveDp, tenDp, fiveDp);
        return view;
    }

}
