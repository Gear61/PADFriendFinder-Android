package com.randomappsinc.padfriendfinder.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.Stubber;
import com.randomappsinc.padfriendfinder.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class MonsterBoxActivity extends ActionBarActivity
{
    private Activity activity;
    private Context context;

    // Views
    private ProgressBar loadingMonsters;
    private TextView instructions;
    private TextView noMonsters;
    private ListView monsterList;

    private MonsterBoxAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_box);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;
        context = this;
        loadingMonsters = (ProgressBar) findViewById(R.id.loading_monsters);
        instructions = (TextView) findViewById(R.id.monster_box_instructions);
        noMonsters = (TextView) findViewById(R.id.no_monsters);
        monsterList = (ListView) findViewById(R.id.monster_list);
        boxAdapter = new MonsterBoxAdapter(context);
        boxAdapter.addMonsters(Stubber.getMonsterBox());
        monsterList.setAdapter(boxAdapter);
        monsterList.setOnItemClickListener(monsterListListener);

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loadingMonsters.setVisibility(View.GONE);
                        instructions.setVisibility(View.VISIBLE);
                        monsterList.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 1500);
    }

    // Student list item clicked
    AdapterView.OnItemClickListener monsterListListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
        {
            Intent intent = new Intent(context, MonsterFormActivity.class);
            intent.putExtra(Constants.MONSTER_KEY, boxAdapter.getItem(position));
            intent.putExtra(Constants.MODE_KEY, Constants.UPDATE_MODE);
            startActivity(intent);
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