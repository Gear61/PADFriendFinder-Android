package com.randomappsinc.padfriendfinder.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.API.DeleteMonster;
import com.randomappsinc.padfriendfinder.API.GetMonsterBox;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.Adapters.MonsterChoicesAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class MonsterBoxActivity extends ActionBarActivity
{
    private Context context;

    // Views
    private ProgressBar loadingMonsters;
    private TextView instructions;
    private TextView noMonsters;
    private ListView monsterList;
    private ProgressDialog deletingMonsterDialog;

    private MonsterBoxAdapter boxAdapter;
    private MonsterBoxReceiver boxReceiver;
    private MonsterUpdateReceiver updateReceiver;
    private MonsterDeleteReceiver deleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_box);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        loadingMonsters = (ProgressBar) findViewById(R.id.loading_monsters);
        instructions = (TextView) findViewById(R.id.monster_box_instructions);
        noMonsters = (TextView) findViewById(R.id.no_monsters);
        monsterList = (ListView) findViewById(R.id.monster_list);
        deletingMonsterDialog = new ProgressDialog(context);
        deletingMonsterDialog.setMessage(Constants.DELETING_MONSTER_MESSAGE);
        monsterList.setOnItemClickListener(monsterListListener);
        boxAdapter = new MonsterBoxAdapter(context);
        monsterList.setAdapter(boxAdapter);

        updateReceiver = new MonsterUpdateReceiver();
        boxReceiver = new MonsterBoxReceiver();
        deleteReceiver = new MonsterDeleteReceiver();
        context.registerReceiver(updateReceiver, new IntentFilter(Constants.MONSTER_UPDATE_KEY));
        context.registerReceiver(boxReceiver, new IntentFilter(Constants.MONSTER_BOX_KEY));
        context.registerReceiver(deleteReceiver, new IntentFilter(Constants.DELETE_KEY));

        List<MonsterAttributes> monsters = MonsterBoxManager.getInstance().getMonsterList();
        // If we've made the call before, and everything's cached...
        if (monsters != null)
        {
            if (!monsters.isEmpty())
            {
                boxAdapter.addMonsters(monsters);
            }
            refreshContent();
        }
        else
        {
            new GetMonsterBox(context).execute();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            context.unregisterReceiver(boxReceiver);
            context.unregisterReceiver(updateReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    private class MonsterDeleteReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            deletingMonsterDialog.dismiss();
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                String monsterName = intent.getStringExtra(Constants.NAME_KEY);
                boxAdapter.deleteMonster(monsterName);
                MonsterBoxManager.getInstance().deleteMonster(monsterName);
                refreshContent();
                Toast.makeText(context, Constants.MONSTER_DELETE_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, Constants.MONSTER_DELETE_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class MonsterUpdateReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                MonsterAttributes monster = intent.getParcelableExtra(Constants.MONSTER_KEY);
                boxAdapter.updateMonster(monster);
                boxAdapter.notifyDataSetChanged();
                MonsterBoxManager.getInstance().updateMonster(monster);
                refreshContent();
            }
        }
    }

    private class MonsterBoxReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                List<MonsterAttributes> monsterBox = JSONParser.parseMonsterBoxResponse(response.getResponse());
                boxAdapter.addMonsters(monsterBox);
                MonsterBoxManager.getInstance().addMonsters(monsterBox);
                refreshContent();
            }
            else
            {
                refreshContent();
                Toast.makeText(context, Constants.BOX_FETCH_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }
    }

    // Refresh the page after an API call is made (or after initial loading)
    public void refreshContent()
    {
        loadingMonsters.setVisibility(View.GONE);
        instructions.setVisibility(View.VISIBLE);
        if (boxAdapter.getCount() == 0)
        {
            monsterList.setVisibility(View.GONE);
            noMonsters.setVisibility(View.VISIBLE);
        }
        else
        {
            noMonsters.setVisibility(View.GONE);
            monsterList.setVisibility(View.VISIBLE);
        }
    }

    // Monster clicked
    AdapterView.OnItemClickListener monsterListListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(R.layout.ordinary_listview, null);
            alertDialogBuilder.setView(convertView);
            final String monsterName = boxAdapter.getItem(position).getName();
            ListView monsterChoices = (ListView) convertView.findViewById(R.id.listView1);
            final MonsterChoicesAdapter adapter = new MonsterChoicesAdapter(context, monsterName);
            monsterChoices.setAdapter(adapter);
            final AlertDialog monsterChosenDialog = alertDialogBuilder.show();
            monsterChoices.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int dialogPosition, long id)
                {
                    monsterChosenDialog.dismiss();
                    String action = adapter.getItem(dialogPosition);
                    if (action.startsWith(Constants.FIND_OTHER))
                    {
                        Intent intent = new Intent(context, MonsterFormActivity.class);
                        intent.putExtra(Constants.NAME_KEY, monsterName);
                        intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
                        startActivity(intent);
                    }
                    else if (action.startsWith(Constants.EDIT))
                    {
                        Intent intent = new Intent(context, MonsterFormActivity.class);
                        intent.putExtra(Constants.MONSTER_KEY, boxAdapter.getItem(position));
                        intent.putExtra(Constants.MODE_KEY, Constants.UPDATE_MODE);
                        startActivity(intent);
                    }
                    else if (action.startsWith(Constants.DELETE))
                    {
                        deletingMonsterDialog.show();
                        new DeleteMonster(context, monsterName).execute();
                    }
                }
            });
            monsterChosenDialog.setCanceledOnTouchOutside(true);
            monsterChosenDialog.setCancelable(true);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.monster_box_menu, menu);
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
        else if (id == R.id.add_monster)
        {
            Intent intent = new Intent(context, MonsterFormActivity.class);
            intent.putExtra(Constants.MODE_KEY, Constants.ADD_MODE);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}