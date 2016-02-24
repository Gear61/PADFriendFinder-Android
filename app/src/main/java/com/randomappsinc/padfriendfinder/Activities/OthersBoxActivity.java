package com.randomappsinc.padfriendfinder.Activities;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class OthersBoxActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.star_icon) TextView star;
    @Bind(R.id.others_list) ListView othersList;
    @Bind(R.id.loading_box) ProgressBar loadingBox;
    @Bind(R.id.no_monsters) TextView nothing;
    @Bind(R.id.entered_id) TextView chosenId;
    @Bind(R.id.pad_id_input) AutoCompleteTextView othersId;

    private MonsterBoxAdapter boxAdapter;
    private OthersBoxReceiver othersBoxReceiver;
    private Set<String> mySet = PreferencesManager.get().getFavorites();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_search);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        boxAdapter = new MonsterBoxAdapter(this);
        othersList.setAdapter(boxAdapter);
        othersBoxReceiver = new OthersBoxReceiver();
        this.registerReceiver(othersBoxReceiver, new IntentFilter(Constants.OTHER_BOX_KEY));

        setUpAdapter();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            displayResult(extras.getString(Constants.OTHERS_ID_KEY));
        }
        else {
            FormUtils.showKeyboard(this);
        }
    }

    @OnEditorAction(R.id.pad_id_input)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (FormUtils.validatePadId(othersId.getText().toString(), parent)) {
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
    public void displayResult(String padId) {
        FormUtils.hideKeyboard(this);
        chosenId.setText(padId);
        othersList.setVisibility(View.GONE);
        loadingBox.setVisibility(View.VISIBLE);
        nothing.setVisibility(View.GONE);
        othersId.setText("");
        if (PreferencesManager.get().isFavorited(padId)) {
            star.setTextColor(getResources().getColor(R.color.gold));
        }
        else {
            star.setTextColor(getResources().getColor(R.color.silver));
        }
        new GetMonsterBox(this, padId, true).execute();
    }

    @OnClick(R.id.star_icon)
    public void onStar() {
        String userId = chosenId.getText().toString();
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

    @OnClick(R.id.clipboard_icon)
    public void onClipboard() {
        String userId = chosenId.getText().toString();
        if (!userId.equals(getString(R.string.no_id_chosen))) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(Constants.PAD_ID_KEY, userId);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, Constants.PAD_ID_COPIED_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    // For when you're copy-pasting an ID in instead of typing it.
    @OnClick(R.id.search_icon)
    public void onSearch() {
        if (FormUtils.validatePadId(othersId.getText().toString(), parent)) {
            displayResult(othersId.getText().toString());
        }
    }

    private class OthersBoxReceiver extends BroadcastReceiver {
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
                Toast.makeText(context, Constants.OTHERS_BOX_FETCH_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }
    }

    // Updating the layout when I'm loading in new data
    public void refreshContent() {
        loadingBox.setVisibility(View.GONE);
        if (boxAdapter.getCount() == 0) {
            othersList.setVisibility(View.GONE);
            nothing.setVisibility(View.VISIBLE);
        }
        else {
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
}
