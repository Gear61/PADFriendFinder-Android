package com.randomappsinc.padfriendfinder.Activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Adapters.FriendResultsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.Stubber;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class FriendResultsActivity extends ActionBarActivity
{
    private Activity activity;
    private Context context;

    // Views
    private ProgressBar loadingFriendResults;
    private TextView intro;
    private ImageView monsterPicture;
    private TextView monsterName;
    private TextView noResults;
    private ListView friendResultsList;

    private MonsterAttributes monster;
    private FriendResultsAdapter friendResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        monster = getIntent().getExtras().getParcelable(Constants.MONSTER_KEY);

        activity = this;
        context = this;
        loadingFriendResults = (ProgressBar) findViewById(R.id.loading_friend_results);
        intro = (TextView) findViewById(R.id.friend_results_intro);
        monsterPicture = (ImageView) findViewById(R.id.monster_picture);
        monsterPicture.setImageResource(monster.getDrawableId());
        monsterName = (TextView) findViewById(R.id.monster_name);
        monsterName.setText(monster.getName());
        noResults = (TextView) findViewById(R.id.no_results);
        friendResultsList = (ListView) findViewById(R.id.friend_results_list);
        friendResultsAdapter = new FriendResultsAdapter(context);
        friendResultsAdapter.addFriends(Stubber.getFriendResults());
        friendResultsList.setAdapter(friendResultsAdapter);
        friendResultsList.setOnItemClickListener(friendResultsListener);

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
                        loadingFriendResults.setVisibility(View.GONE);
                        intro.setVisibility(View.VISIBLE);
                        monsterPicture.setVisibility(View.VISIBLE);
                        monsterName.setVisibility(View.VISIBLE);
                        friendResultsList.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 1500);
    }

    // Student list item clicked
    AdapterView.OnItemClickListener friendResultsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
        {
            String padId = friendResultsAdapter.getItem(position).getPadId();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(Constants.PLAYER_ID_KEY, padId);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, Constants.PAD_ID_COPIED_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
