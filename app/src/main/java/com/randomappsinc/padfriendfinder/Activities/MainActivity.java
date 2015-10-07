package com.randomappsinc.padfriendfinder.Activities;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.randomappsinc.padfriendfinder.API.GetMonsterList;
import com.randomappsinc.padfriendfinder.Fragments.MonsterBoxFragment;
import com.randomappsinc.padfriendfinder.Fragments.NavigationDrawerFragment;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
{
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (PreferencesManager.get().getPadId().isEmpty()) {
            startActivity(new Intent(this, PadIdActivity.class));
            finish();
        }
        else {
            setContentView(R.layout.activity_main);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            // Set up the drawer.
            mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

            FragmentManager fragmentManager = getFragmentManager();

            MonsterBoxFragment monsterBoxFragment = new MonsterBoxFragment();
            fragmentManager.beginTransaction().replace(R.id.container, monsterBoxFragment).commit();

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading_monster_list));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            new GetMonsterList(progressDialog).execute();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(this, FavoritesActivity.class);
                break;
            case 1:
                intent = new Intent(this, SupportedLeadsActivity.class);
                break;
            case 2:
                intent = new Intent(this, TopLeadersActivity.class);
                break;
            case 3:
                intent = new Intent(this, OthersBoxActivity.class);
                break;
            case 4:
                intent = new Intent(this, SettingsActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!PreferencesManager.get().getPadId().isEmpty()) {
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.menu_main, menu);
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.find_friends)
        {
            Intent intent = new Intent(this, MonsterFormActivity.class);
            intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
