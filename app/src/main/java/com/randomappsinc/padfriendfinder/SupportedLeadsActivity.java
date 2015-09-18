package com.randomappsinc.padfriendfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.randomappsinc.padfriendfinder.Adapters.SupportedLeadsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class SupportedLeadsActivity extends AppCompatActivity
{
    @Bind(R.id.monster_matches) ListView monsterMatches;

    private SupportedLeadsAdapter supportedLeadsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supported_leads);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        supportedLeadsAdapter = new SupportedLeadsAdapter(this);
        monsterMatches.setAdapter(supportedLeadsAdapter);
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s)
    {
        supportedLeadsAdapter.updateWithPrefix(s.toString());
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
