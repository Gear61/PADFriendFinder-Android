package com.randomappsinc.padfriendfinder.Activities;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.padfriendfinder.API.GetMonsterList;
import com.randomappsinc.padfriendfinder.Fragments.MonsterBoxFragment;
import com.randomappsinc.padfriendfinder.Fragments.NavigationDrawerFragment;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    @Bind(R.id.add_monster) FloatingActionButton addMonster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferencesManager.get().getPadId().isEmpty()) {
            startActivity(new Intent(this, PadIdActivity.class));
            finish();
        }
        else {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            addMonster.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_plus).colorRes(R.color.white));

            NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

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

    @OnClick(R.id.add_monster)
    public void addMonster() {
        Intent intent = new Intent(this, MonsterFormActivity.class);
        intent.putExtra(Constants.MODE_KEY, Constants.ADD_MODE);
        startActivity(intent);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Intent intent = null;
        switch (position) {
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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.find_friends).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_users)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.find_friends) {
            Intent intent = new Intent(this, MonsterFormActivity.class);
            intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
