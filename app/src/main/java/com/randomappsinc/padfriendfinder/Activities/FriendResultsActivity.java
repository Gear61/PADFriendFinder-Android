package com.randomappsinc.padfriendfinder.Activities;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.randomappsinc.padfriendfinder.Utils.FormUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class FriendResultsActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading_friend_results) View loadingFriendResults;
    @Bind(R.id.friend_results_intro) TextView intro;
    @Bind(R.id.monster_picture) ImageView monsterPicture;
    @Bind(R.id.monster_name) TextView monsterName;
    @Bind(R.id.friend_results_instructions) TextView instructions;
    @Bind(R.id.no_results) TextView noResults;
    @Bind(R.id.friend_results_list) ListView friendResultsList;

    private FriendResultsAdapter friendResultsAdapter;
    private FetchFriendsReceiver friendsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_results);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MonsterAttributes monster = getIntent().getExtras().getParcelable(Constants.MONSTER_KEY);
        Picasso.with(this).load(monster.getImageUrl()).into(monsterPicture);
        monsterName.setText(monster.getName());
        friendResultsAdapter = new FriendResultsAdapter(this);
        friendResultsList.setAdapter(friendResultsAdapter);

        // Receiver for API call
        friendsReceiver = new FetchFriendsReceiver();
        registerReceiver(friendsReceiver, new IntentFilter(Constants.FETCH_FRIENDS_KEY));

        new FetchFriends(this, monster).execute();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            unregisterReceiver(friendsReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    private class FetchFriendsReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200) {
                List<Friend> friendResults = JSONParser.parseFriendCandidatesResponse(response.getResponse());
                friendResultsAdapter.addFriends(friendResults);
            }
            else {
                Toast.makeText(context, Constants.FETCH_FRIENDS_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
            loadingFriendResults.setVisibility(View.GONE);
            String isAre = friendResultsAdapter.getCount() == 1 ? "is" : "are";
            String results = friendResultsAdapter.getCount() == 1 ? "result" : "results";
            String introText = "Here " + isAre + " your <b>" + String.valueOf(friendResultsAdapter.getCount())+ "</b>" +
                    "</b> search " + results + " for:";
            intro.setText(Html.fromHtml(introText));
            intro.setVisibility(View.VISIBLE);
            monsterPicture.setVisibility(View.VISIBLE);
            monsterName.setVisibility(View.VISIBLE);
            instructions.setVisibility(View.VISIBLE);
            if (friendResultsAdapter.getCount() == 0) {
                noResults.setVisibility(View.VISIBLE);
            }
            else {
                friendResultsList.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnItemClick(R.id.friend_results_list)
    public void onItemClick(int position) {
        String padId = friendResultsAdapter.getItem(position).getPadId();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(Constants.PAD_ID_KEY, padId);
        clipboard.setPrimaryClip(clip);
        FormUtils.showSnackbar(parent, getString(R.string.id_copied));
    }
}
