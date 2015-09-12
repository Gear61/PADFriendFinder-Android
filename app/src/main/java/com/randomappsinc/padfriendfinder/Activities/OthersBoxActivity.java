package com.randomappsinc.padfriendfinder.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OthersBoxActivity extends AppCompatActivity
{
    @Bind(R.id.star_icon)
    FontAwesomeText star;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_box);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.star_icon)
    public void onStar(View view) {
        if (PreferencesManager.get().isFavorited("329601363")) {
            star.setTextColor(getResources().getColor(R.color.silver));
            PreferencesManager.get().removeFavorite("329601363");
        }
        else {
            star.setTextColor(getResources().getColor(R.color.gold));
            PreferencesManager.get().addFavorite("329601363");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_others_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
