package com.randomappsinc.padfriendfinder.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.API.Callbacks.FindFriendsCallback;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.Models.PlayerMonster;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Adapters.FriendResultsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class FriendResultsActivity extends StandardActivity {
    public static final String LOG_TAG = "FriendResults";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading_friend_results) View loadingFriendResults;
    @Bind(R.id.friend_results_intro) TextView intro;
    @Bind(R.id.monster_picture) ImageView monsterPicture;
    @Bind(R.id.monster_name) TextView monsterName;
    @Bind(R.id.friend_results_instructions) TextView instructions;
    @Bind(R.id.no_results) TextView noResults;
    @Bind(R.id.friend_results_list) ListView friendResultsList;

    private FriendResultsAdapter friendResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_results);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);

        Monster monster = getIntent().getParcelableExtra(Constants.MONSTER_KEY);
        Picasso.with(this).load(monster.getImageUrl()).into(monsterPicture);
        monsterName.setText(monster.getName());
        friendResultsAdapter = new FriendResultsAdapter(this);
        friendResultsList.setAdapter(friendResultsAdapter);

        FindFriendsCallback callback = new FindFriendsCallback();
        PlayerMonster request = new PlayerMonster(monster);
        RestClient.getInstance().getPffService().findFriends(request).enqueue(callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(List<Friend> friends) {
        friendResultsAdapter.addFriends(friends);
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

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(LOG_TAG)) {
            loadingFriendResults.setVisibility(View.GONE);
            FormUtils.showSnackbar(parent, event.getMessage());
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
