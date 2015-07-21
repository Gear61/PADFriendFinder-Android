package com.randomappsinc.padfriendfinder.Activities;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.randomappsinc.padfriendfinder.R;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class MonsterBoxActivity extends ActionBarActivity
{


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}