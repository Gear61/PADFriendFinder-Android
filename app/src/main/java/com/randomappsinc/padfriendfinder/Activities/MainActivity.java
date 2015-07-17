package com.randomappsinc.padfriendfinder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;


public class MainActivity extends Activity
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
        }
    }

    public void findFriends(View view)
    {
        Intent intent = new Intent(this, MonsterSearchActivity.class);
        startActivity(intent);
        finish();
    }
}
