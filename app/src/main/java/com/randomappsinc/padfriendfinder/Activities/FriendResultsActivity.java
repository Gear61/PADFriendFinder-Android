package com.randomappsinc.padfriendfinder.Activities;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.randomappsinc.padfriendfinder.API.FetchFriends;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Adapters.FriendResultsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class FriendResultsActivity extends AppCompatActivity
{
    private Context context;

    // Views
    @Bind(R.id.loading_friend_results) ProgressBar loadingFriendResults;
    @Bind(R.id.friend_results_intro) TextView intro;
    @Bind(R.id.monster_picture) ImageView monsterPicture;
    @Bind(R.id.monster_name) TextView monsterName;
    @Bind(R.id.friend_results_instructions) TextView instructions;
    @Bind(R.id.no_results) TextView noResults;
    @Bind(R.id.friend_results_list) ListView friendResultsList;

    private FriendResultsAdapter friendResultsAdapter;
    private FetchFriendsReceiver friendsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_results);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        MonsterAttributes monster = getIntent().getExtras().getParcelable(Constants.MONSTER_KEY);
        Picasso.with(context).load(monster.getImageUrl()).into(monsterPicture);
        monsterName.setText(monster.getName());
        friendResultsAdapter = new FriendResultsAdapter(context);
        friendResultsList.setAdapter(friendResultsAdapter);

        // Receiver for API call
        friendsReceiver = new FetchFriendsReceiver();
        context.registerReceiver(friendsReceiver, new IntentFilter(Constants.FETCH_FRIENDS_KEY));

        new FetchFriends(context, monster).execute();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            context.unregisterReceiver(friendsReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    private class FetchFriendsReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                List<Friend> friendResults = JSONParser.parseFriendCandidatesResponse(response.getResponse());
                friendResultsAdapter.addFriends(friendResults);
            }
            else
            {
                Toast.makeText(context, Constants.FETCH_FRIENDS_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
            loadingFriendResults.setVisibility(View.GONE);
            intro.setVisibility(View.VISIBLE);
            monsterPicture.setVisibility(View.VISIBLE);
            monsterName.setVisibility(View.VISIBLE);
            instructions.setVisibility(View.VISIBLE);
            if (friendResultsAdapter.getCount() == 0)
            {
                noResults.setVisibility(View.VISIBLE);
            }
            else
            {
                friendResultsList.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnItemClick(R.id.friend_results_list)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        String padId = friendResultsAdapter.getItem(position).getPadId();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(Constants.PAD_ID_KEY, padId);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, Constants.PAD_ID_COPIED_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blank_menu, menu);
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
