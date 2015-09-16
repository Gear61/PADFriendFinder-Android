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
import android.view.inputmethod.InputMethodManager;
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

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OthersBoxActivity extends AppCompatActivity
{
    @Bind(R.id.star_icon) FontAwesomeText star;
    @Bind(R.id.others_list) ListView othersList;
    @Bind(R.id.loading_box) ProgressBar loadingBox;
    @Bind(R.id.nothing) TextView nothing;
    @Bind(R.id.entered_id) TextView id;
    @Bind(R.id.PAD_ID) AutoCompleteTextView othersId;

    Context context = this;
    String pad_id = null;
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
            display_result(id, extras.getString(Constants.OTHERS_ID_KEY));

        othersId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    pad_id = othersId.getText().toString();
                    if (pad_id.length() == 9 && (pad_id.charAt(0) == '3'))
                        display_result(id, pad_id);
                    else
                        Toast.makeText(getApplicationContext(), "Error: Invalid Input.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    public void setUpAdapter() {
        String[] favorites = mySet.toArray(new String[mySet.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favorites);
        othersId.setAdapter(adapter);
    }

    //Processes output once I have enetred in a valid 9 digit ID
    public void display_result(TextView id, String pad_id) {
        othersList.setVisibility(View.GONE);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(othersId.getWindowToken(), 0);
        id.setText(pad_id);
        othersId.setText(null);
        if (PreferencesManager.get().isFavorited(pad_id))
            star.setTextColor(getResources().getColor(R.color.gold));
        else
            star.setTextColor(getResources().getColor(R.color.silver));

        loadingBox.setVisibility(View.VISIBLE);
        nothing.setVisibility(View.GONE);
        new GetMonsterBox(this, pad_id, true).execute();
    }

    @OnClick(R.id.star_icon)
    //This function does the favoriting and unfavoriting. Because I'm lazy, only favoriting ID interacts with auto-complete.
    public void onStar(View view) {
        String user_id = ((TextView) findViewById(R.id.entered_id)).getText().toString();
        String favorites[] = null;
        //othersList.getVisibility() == View.VISIBLE
        if (!user_id.isEmpty()) {
            if (PreferencesManager.get().isFavorited(user_id)) {
                star.setTextColor(getResources().getColor(R.color.silver));
                PreferencesManager.get().removeFavorite(user_id);
            } else {
                star.setTextColor(getResources().getColor(R.color.gold));
                PreferencesManager.get().addFavorite(user_id);
                mySet.add(user_id);
                //can I use an array list for adapters? I think so.
                setUpAdapter();
            }
        }
        else
            Toast.makeText(context, Constants.ID_HAS_NOTHING_MESSAGE, Toast.LENGTH_LONG).show();
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
    public void onDestroy() {
        super.onDestroy();
        try {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
