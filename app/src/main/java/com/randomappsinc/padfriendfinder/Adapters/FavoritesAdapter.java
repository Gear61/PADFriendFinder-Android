package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jman0_000 on 9/14/2015.
 */

//Adapter I wrote for favorites functionality. Simple layout: a star and an EditText.
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

    public void onStar_Click(View view, String id) {
        FontAwesomeText star = (FontAwesomeText) view;
        if (star.getCurrentTextColor() == context.getResources().getColor(R.color.gold)) {
            star.setTextColor(context.getResources().getColor(R.color.silver));
            PreferencesManager.get().removeFavorite(id);
        }
        else {
            star.setTextColor(context.getResources().getColor(R.color.gold));
            PreferencesManager.get().addFavorite(id);
        }
    }

    //This function sets up + recycles the individual views for the ListView
    //First I get the components that I'm filling up my ListView with (if I haven't already)
    //Then I fill up the views with the appropriate items
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        //recycling
        if (v == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.favorites_list_item, null);
            holder = new ViewHolder();
            holder.star = (FontAwesomeText) v.findViewById(R.id.star_icon);
            holder.pad_id = (TextView) v.findViewById(R.id.favorite_id);
            v.setTag(holder);
        }
        else
            holder = (ViewHolder) v.getTag();

        //set up
        final String id = favorites.get(position);
        if (PreferencesManager.get().isFavorited(id))
            holder.star.setTextColor(context.getResources().getColor(R.color.gold));
        else
            holder.star.setTextColor(context.getResources().getColor(R.color.silver));
        holder.star.setOnClickListener(new FontAwesomeText.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStar_Click(view, id);
            }
        });
        holder.pad_id.setText(id);

        return v;
    }

}
