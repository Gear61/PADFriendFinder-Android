package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.Callbacks.TopLeadersCallback;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.Events.TopLeadersEvent;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Adapters.TopMonsterAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

public class TopLeadersActivity extends StandardActivity {
    public static final String LOG_TAG = "TopLeadersActivity";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.top_leaders) ListView topLeaders;
    @Bind(R.id.loading_top_leaders) View loading;

    private TopMonsterAdapter topMonsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_leaders_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        topMonsterAdapter = new TopMonsterAdapter(this);
        topLeaders.setAdapter(topMonsterAdapter);
        TopLeadersCallback callback = new TopLeadersCallback();
        RestClient.getInstance().getPffService().getTopLeaders().enqueue(callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnItemClick(R.id.top_leaders)
    public void onItemClick(int position) {
        final Context context = this;
        final String monsterName = topMonsterAdapter.getItem(position).getName();

        String[] choices = new String[2];
        choices[0] = getString(R.string.search_for) + "\"" + monsterName + "\"";
        choices[1] = getString(R.string.find_hypermaxed) + "\"" + monsterName + "\"";

        new MaterialDialog.Builder(this)
                .items(choices)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Intent intent = null;
                        switch (which) {
                            case 0:
                                intent = new Intent(context, MonsterFormActivity.class);
                                intent.putExtra(Constants.NAME_KEY, monsterName);
                                intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
                                break;
                            case 1:
                                Monster chosenMonster =
                                        MonsterServer.getMonsterServer().getMonsterAttributes(monsterName);
                                chosenMonster.setPlusEggs(Constants.MAX_PLUS_EGGS);
                                intent = new Intent(context, FriendResultsActivity.class);
                                intent.putExtra(Constants.MONSTER_KEY, chosenMonster);
                        }
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void onEvent(TopLeadersEvent event) {
        loading.setVisibility(View.GONE);
        topMonsterAdapter.setTopMonsters(event.getTopLeaderList());
    }

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(LOG_TAG)) {
            loading.setVisibility(View.GONE);
            FormUtils.showSnackbar(parent, event.getMessage());
        }
    }
}
