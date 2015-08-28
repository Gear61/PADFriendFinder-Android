package com.randomappsinc.padfriendfinder.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.randomappsinc.padfriendfinder.API.GetMonsterList;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // If they haven't set their PAD ID yet, redirect them to that screen
        if (PreferencesManager.get().getPadId().isEmpty())
        {
            Intent intent = new Intent(this, PadIdActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            setContentView(R.layout.activity_main);
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Loading monster list...");
            new GetMonsterList(this, progress).execute();
        }
    }

    public void findFriends(View view)
    {
        Intent intent = new Intent(this, MonsterFormActivity.class);
        intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
        startActivity(intent);
    }

    public void monsterBox(View view)
    {
        Intent intent = new Intent(this, MonsterBoxActivity.class);
        startActivity(intent);
    }

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
        return super.onOptionsItemSelected(item);
    }
}
