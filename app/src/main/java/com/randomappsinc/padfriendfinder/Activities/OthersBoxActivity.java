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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.API.GetMonsterBox;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OthersBoxActivity extends AppCompatActivity
{
    @Bind(R.id.star_icon) FontAwesomeText star;
    @Bind(R.id.others_list) ListView othersList;
    @Bind(R.id.loading_box) ProgressBar loadingBox;
    @Bind(R.id.nothing) TextView nothing;

    EditText othersId;
    Context context = this;
    String pad_id = null;
    private MonsterBoxAdapter boxAdapter;
    private OthersBoxReceiver othersBoxReceiver;

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
        this.registerReceiver(othersBoxReceiver, new IntentFilter(Constants.MONSTER_BOX_KEY));

        othersId = (EditText) findViewById(R.id.PAD_ID);
        othersId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    TextView id = (TextView) findViewById(R.id.entered_id);
                    pad_id = othersId.getText().toString();
                    if (pad_id.length() == 9 && (pad_id.charAt(0) == '3'))
                        format_result(id, pad_id);
                    else
                        Toast.makeText(getApplicationContext(), "Error: Invalid Input.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    public void format_result(TextView id, String pad_id) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(othersId.getWindowToken(), 0);
        id.setText(pad_id);
        othersId.setText(null);
        if (PreferencesManager.get().isFavorited(pad_id))
            star.setTextColor(getResources().getColor(R.color.gold));
        else
            star.setTextColor(getResources().getColor(R.color.silver));

        loadingBox.setVisibility(View.VISIBLE);
        new GetMonsterBox(this, pad_id).execute();
    }

    @OnClick(R.id.star_icon)
    public void onStar(View view) {
        String user_id = ((TextView) findViewById(R.id.entered_id)).getText().toString();
        if (!user_id.isEmpty()) {
            if (PreferencesManager.get().isFavorited(user_id)) {
                star.setTextColor(getResources().getColor(R.color.silver));
                PreferencesManager.get().removeFavorite(user_id);
            } else {
                star.setTextColor(getResources().getColor(R.color.gold));
                PreferencesManager.get().addFavorite(user_id);
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
        getMenuInflater().inflate(R.menu.menu_others_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
