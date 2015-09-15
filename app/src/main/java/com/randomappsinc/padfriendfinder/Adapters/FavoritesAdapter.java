package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jman0_000 on 9/14/2015.
 */
public class FavoritesAdapter extends BaseAdapter {

    private Context context;
    private List<String> favorites;

    public FavoritesAdapter(Context context) {
        this.context = context;
        this.favorites = new ArrayList<>();
    }

    public void addFavorites(List<String> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public int getCount() {
        return favorites.size();
    }

    public String getItem(int position) {
        return favorites.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public FontAwesomeText star;
        public TextView pad_id;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.favorites_list_item, null);
            holder = new ViewHolder();
            holder.star = (FontAwesomeText) v.findViewById(R.id.star_icon1);
            holder.pad_id = (TextView) v.findViewById(R.id.favorite_id);
            v.setTag(holder);
        }
        else
            holder = (ViewHolder) v.getTag();

        String id = favorites.get(position);
        holder.star.setTextColor(context.getResources().getColor(R.color.gold));
        holder.pad_id.setText(id);

        return v;
    }

}
