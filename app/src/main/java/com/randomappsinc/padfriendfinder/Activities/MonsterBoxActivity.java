package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_box);

        context = this;
        loadingMonsters = (ProgressBar) findViewById(R.id.loading_monsters);
        instructions = (TextView) findViewById(R.id.monster_box_instructions);
        noMonsters = (TextView) findViewById(R.id.no_monsters);
        monsterList = (ListView) findViewById(R.id.monster_list);

        MonsterBoxAdapter boxAdapter = new MonsterBoxAdapter(context);
    }

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
        return super.onOptionsItemSelected(item);
    }
}