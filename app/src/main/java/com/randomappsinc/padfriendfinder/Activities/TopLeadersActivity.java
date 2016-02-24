package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.GetTopLeaders;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Adapters.TopMonsterAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.Models.TopLeader;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

public class TopLeadersActivity extends StandardActivity {
    @Bind(R.id.top_leaders) ListView topLeaders;
    @Bind(R.id.loading_top_leaders) View loading;

    private TopMonsterAdapter topMonsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_leaders_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        topMonsterAdapter = new TopMonsterAdapter(this);
        loading.setVisibility(View.VISIBLE);
        new GetTopLeaders(this).execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
                                MonsterAttributes chosenMonster =
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

    //EventBus listener. Listens for a class to be broadcast, then executes onEcent() code once it receives the class
    public void onEvent(RestCallResponse restCallResponse) {
        loading.setVisibility(View.GONE);
        if (restCallResponse.getStatusCode() == 200) {
            List<TopLeader> topLeaderList = JSONParser.parseTopLeaders(restCallResponse.getResponse());
            topMonsterAdapter.setTopMonsters(topLeaderList);
            topLeaders.setAdapter(topMonsterAdapter);
            topLeaders.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(this, Constants.FAILED_TO_FETCH, Toast.LENGTH_SHORT).show();
    }
}
