package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class TopLeadersActivity extends AppCompatActivity {

    Context context;
    @Bind(R.id.topLeadersLV) ListView topLeadersLV;
    @Bind(R.id.topLeadersPB) ProgressBar topLeadersPB;

    TopMonsterAdapter topMonsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_leaders_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        topMonsterAdapter = new TopMonsterAdapter(this);
        context = this;
        topLeadersPB.setVisibility(View.VISIBLE);
        new GetTopLeaders(context).execute();
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

    @OnItemClick(R.id.topLeadersLV)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
        String name = topMonsterAdapter.getItem(position).getName();
        MonsterAttributes monster = MonsterServer.getMonsterServer().getMonsterAttributes(name);
        monster.setPlusEggs(Constants.MAX_PLUS_EGGS);
        Intent intent = new Intent(context, FriendResultsActivity.class);
        intent.putExtra(Constants.MONSTER_KEY, monster);
        startActivity(intent);
    }

    public void onEvent(RestCallResponse restCallResponse) {
        topLeadersPB.setVisibility(View.GONE);
        if (restCallResponse.getStatusCode() == 200) {
            List<TopLeader> topLeaderList = JSONParser.parseTopLeaders(restCallResponse.getResponse());
            topMonsterAdapter.setTopMonsters(topLeaderList);
            topLeadersLV.setAdapter(topMonsterAdapter);
            topLeadersLV.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(context, Constants.FAILED_TO_FETCH, Toast.LENGTH_SHORT).show();
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
