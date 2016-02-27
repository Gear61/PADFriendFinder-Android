package com.randomappsinc.padfriendfinder.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Callbacks.GetMonsterListCallback;
import com.randomappsinc.padfriendfinder.API.Events.BasicResponseEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Fragments.MonsterBoxFragment;
import com.randomappsinc.padfriendfinder.Fragments.NavigationDrawerFragment;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static final String LOG_TAG = "MainActivity";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.add_monster) FloatingActionButton addMonster;

    private MaterialDialog loadingMonsters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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

            loadingMonsters = new MaterialDialog.Builder(this)
                    .content(R.string.loading_monster_list)
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            GetMonsterListCallback callback = new GetMonsterListCallback();
            RestClient.getInstance().getPffService().getMonsterList().enqueue(callback);
        }
    }

    public void onEvent(BasicResponseEvent event) {
        if (event.getEventType().equals(Constants.GET_MONSTERS_KEY) &&
            event.getResponseCode() == ApiConstants.STATUS_OK) {
            loadingMonsters.dismiss();
            FormUtils.showSnackbar(parent, getString(R.string.monster_list_loaded));
        }
    }

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(LOG_TAG)) {
            FormUtils.showSnackbar(parent, event.getMessage());
        }
    }

    public void showSnackbar(String message) {
        FormUtils.showSnackbar(parent, message);
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
        EventBus.getDefault().unregister(this);
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
