package com.randomappsinc.padfriendfinder.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.API.GetMonsterBox;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class OthersBoxActivity extends AppCompatActivity
{
    @Bind(R.id.star_icon) FontAwesomeText star;
    @Bind(R.id.others_list) ListView othersList;
    @Bind(R.id.loading_box) ProgressBar loadingBox;
    @Bind(R.id.nothing) TextView nothing;
    @Bind(R.id.entered_id) TextView id;
    @Bind(R.id.PAD_ID) AutoCompleteTextView othersId;

    private MonsterBoxAdapter boxAdapter;
    private OthersBoxReceiver othersBoxReceiver;
    private Set<String> mySet = PreferencesManager.get().getFavorites();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_box);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        boxAdapter = new MonsterBoxAdapter(this);
        othersList.setAdapter(boxAdapter);
        othersBoxReceiver = new OthersBoxReceiver();
        this.registerReceiver(othersBoxReceiver, new IntentFilter(Constants.OTHER_BOX_KEY));

        setUpAdapter();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            displayResult(extras.getString(Constants.OTHERS_ID_KEY));
        }
    }

    @OnEditorAction(R.id.PAD_ID)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            if (FormUtils.validatePadId(othersId.getText().toString()))
            {
                displayResult(othersId.getText().toString());
            }
            return true;
        }
        return false;
    }

    public void setUpAdapter() {
        String[] favorites = mySet.toArray(new String[mySet.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favorites);
        othersId.setAdapter(adapter);
    }

    // Processes output once I have entered in a valid 9 digit ID
    public void displayResult(String padId)
    {
        FormUtils.hideKeyboard(this);
        id.setText(padId);
        othersList.setVisibility(View.GONE);
        loadingBox.setVisibility(View.VISIBLE);
        nothing.setVisibility(View.GONE);
        othersId.setText("");
        if (PreferencesManager.get().isFavorited(padId))
        {
            star.setTextColor(getResources().getColor(R.color.gold));
        }
        else
        {
            star.setTextColor(getResources().getColor(R.color.silver));
        }
        new GetMonsterBox(this, padId, true).execute();
    }

    @OnClick(R.id.star_icon)
    // This function does the favoriting and unfavoriting.
    // Because I'm lazy, only favoriting ID interacts with auto-complete.
    public void onStar(View view) {
        String userId = ((TextView) findViewById(R.id.entered_id)).getText().toString();
        if (!userId.equals(getString(R.string.no_id_chosen))) {
            if (PreferencesManager.get().isFavorited(userId)) {
                star.setTextColor(getResources().getColor(R.color.silver));
                PreferencesManager.get().removeFavorite(userId);
            } else {
                star.setTextColor(getResources().getColor(R.color.gold));
                PreferencesManager.get().addFavorite(userId);
                mySet.add(userId);
                setUpAdapter();
            }
        }
    }

    private class OthersBoxReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                List<MonsterAttributes> monsterBox = JSONParser.parseMonsterBoxResponse(response.getResponse());
                boxAdapter.clear();
                boxAdapter.addMonsters(monsterBox);
                refreshContent();
            }
            else
            {
                refreshContent();
                Toast.makeText(context, Constants.BOX_FETCH_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }
    }

    //Updating the layout when I'm loading in new data
    public void refreshContent()
    {
        loadingBox.setVisibility(View.GONE);
        if (boxAdapter.getCount() == 0)
        {
            othersList.setVisibility(View.GONE);
            nothing.setVisibility(View.VISIBLE);
        }
        else
        {
            nothing.setVisibility(View.GONE);
            othersList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            unregisterReceiver(othersBoxReceiver);
        }
        catch (IllegalArgumentException ignored) {}
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
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
