package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Adapters.FavoritesAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class FavoritesActivity extends AppCompatActivity {


    @Bind(R.id.favoritesView) ListView favView;
    @Bind(R.id.no_favs) TextView noFavs;

    Context context = this;
    Set<String> favs = PreferencesManager.get().getFavorites();
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        favoritesAdapter = new FavoritesAdapter(this);
        favoritesAdapter.addFavorites(new ArrayList<>(favs));
        favView.setAdapter(favoritesAdapter);
        display_stuff();
    }

    @Override
    public void onResume() {
        favoritesAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void display_stuff() {
        if (favoritesAdapter.getCount() == 0) {
            noFavs.setVisibility(View.VISIBLE);
            favView.setVisibility(View.GONE);
        }
        else {
            noFavs.setVisibility(View.GONE);
            favView.setVisibility(View.VISIBLE);
        }
    }

    @OnItemClick(R.id.favoritesView)
    public void onItemClick(AdapterView<?> adapterView, View v, final int position, long id) {
        String fav_id = favoritesAdapter.getItem(position); //I didn't create an adapter of a custom class so this is fine
        Intent intent = new Intent(this, OthersBoxActivity.class);
        intent.putExtra(Constants.OTHERS_ID_KEY, fav_id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorites_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
