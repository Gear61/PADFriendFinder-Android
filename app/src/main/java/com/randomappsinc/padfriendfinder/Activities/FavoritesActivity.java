package com.randomappsinc.padfriendfinder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class FavoritesActivity extends AppCompatActivity {

    @Bind(R.id.favoritesView) ListView favView;
    @Bind(R.id.no_favs) TextView noFavs;

    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        List<String> favorites = new ArrayList<>(PreferencesManager.get().getFavorites());
        Collections.sort(favorites);
        favoritesAdapter = new FavoritesAdapter(this);
        favoritesAdapter.setFavorites(favorites);
        favView.setAdapter(favoritesAdapter);
        display_stuff();
    }

    @Override
    public void onResume() {
        List<String> favorites = new ArrayList<>(PreferencesManager.get().getFavorites());
        favoritesAdapter.setFavorites(favorites);
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
        getMenuInflater().inflate(R.menu.blank_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
