package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jman0_000 on 9/14/2015.
 */

// Adapter I wrote for favorites functionality. Simple layout: a star and an EditText.
public class FavoritesAdapter extends BaseAdapter {
    private Context context;
    private List<String> favorites;

    public FavoritesAdapter(Context context) {
        this.context = context;
        this.favorites = new ArrayList<>();
    }

    public void setFavorites(List<String> favorites) {
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

    public static class FavoriteViewHolder {
        @Bind(R.id.star_icon) TextView star;
        @Bind(R.id.favorite_id) TextView pad_id;

        public FavoriteViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void onStar_Click(View view, String id) {
        TextView star = (TextView) view;
        if (star.getCurrentTextColor() == context.getResources().getColor(R.color.gold)) {
            star.setTextColor(context.getResources().getColor(R.color.silver));
            PreferencesManager.get().removeFavorite(id);
        }
        else {
            star.setTextColor(context.getResources().getColor(R.color.gold));
            PreferencesManager.get().addFavorite(id);
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        FavoriteViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.favorites_list_item, parent, false);
            holder = new FavoriteViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (FavoriteViewHolder) view.getTag();
        }

        final String id = favorites.get(position);
        if (PreferencesManager.get().isFavorited(id))
            holder.star.setTextColor(context.getResources().getColor(R.color.gold));
        else
            holder.star.setTextColor(context.getResources().getColor(R.color.silver));

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStar_Click(view, id);
            }
        });
        holder.pad_id.setText(id);

        return view;
    }
}
