package com.randomappsinc.padfriendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Activities.MonsterFormActivity;
import com.randomappsinc.padfriendfinder.Adapters.SupportedLeadsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class SupportedLeadsActivity extends AppCompatActivity
{
    @Bind(R.id.monster_matches) ListView monsterMatches;

    private SupportedLeadsAdapter supportedLeadsAdapter;
    private boolean chooseAvatarMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supported_leads);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseAvatarMode = getIntent().getBooleanExtra(Constants.CHOOSE_AVATAR_MODE, false);
        if (chooseAvatarMode)
        {
            setTitle(getString(R.string.choose_avatar_label));
        }
        supportedLeadsAdapter = new SupportedLeadsAdapter(this);
        monsterMatches.setAdapter(supportedLeadsAdapter);
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s)
    {
        supportedLeadsAdapter.updateWithPrefix(s.toString());
    }

    @OnItemClick(R.id.monster_matches)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        if (chooseAvatarMode)
        {
            PreferencesManager.get().setAvatarId(supportedLeadsAdapter.getItem(position).getMonsterId());
            Toast.makeText(this, "Your avatar is now " + supportedLeadsAdapter.getItem(position).getName() + ".",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            MonsterAttributes monster = supportedLeadsAdapter.getItem(position);
            Intent intent = new Intent(this, MonsterFormActivity.class);
            intent.putExtra(Constants.NAME_KEY, monster.getName());
            intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blank_menu, menu);
        return true;
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
