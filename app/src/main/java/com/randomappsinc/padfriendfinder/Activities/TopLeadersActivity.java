package com.randomappsinc.padfriendfinder.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.API.GetTopLeaders;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Adapters.MonsterItemAdapter;
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.ordinary_listview, null);
        alertDialogBuilder.setView(convertView);
        final String monsterName = topMonsterAdapter.getItem(position).getName();
        ListView monsterChoices = (ListView) convertView.findViewById(R.id.listView1);
        final MonsterItemAdapter adapter = new MonsterItemAdapter(context, monsterName);
        monsterChoices.setAdapter(adapter);
        final AlertDialog monsterChosenDialog = alertDialogBuilder.show();
        //Setting up the AlertDialog above.
        monsterChoices.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int dialogPosition, long id)
            {
                monsterChosenDialog.dismiss();
                String action = adapter.getItem(dialogPosition);
                String name = topMonsterAdapter.getItem(position).getName();
                MonsterAttributes monsterChosen = MonsterServer.getMonsterServer().getMonsterAttributes(name);
                openSearchResults(action, name, monsterChosen, context);
            }
        });
        monsterChosenDialog.setCanceledOnTouchOutside(true);
        monsterChosenDialog.setCancelable(true);
    }

    //This function loads the search resulsts once an item has been clicked on.
    //Made its own separate function since SupportedLeadsActivity has the same onClick for its ListView
    static public void openSearchResults(String action, String name, MonsterAttributes monsterChosen, Context context) {
        MonsterAttributes monster;
        Intent intent;
        if (action.startsWith(Constants.SEARCH)) {
            intent = new Intent(context, MonsterFormActivity.class);
            intent.putExtra(Constants.NAME_KEY, name);
            intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
        }
        else {
            monster = monsterChosen;
            monster.setPlusEggs(Constants.MAX_PLUS_EGGS);
            intent = new Intent(context, FriendResultsActivity.class);
            intent.putExtra(Constants.MONSTER_KEY, monster);
        }
        context.startActivity(intent);
    }


    //EventBus listener. Listens for a class to be broadcast, then executes onEcent() code once it receives the class
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