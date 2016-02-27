package com.randomappsinc.padfriendfinder.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.API.Callbacks.GetMonsterBoxCallback;
import com.randomappsinc.padfriendfinder.API.Events.MonsterBoxEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import de.greenrobot.event.EventBus;

public class OthersBoxActivity extends StandardActivity {
    public static final String LOG_TAG = "OthersBox";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.star_icon) TextView star;
    @Bind(R.id.others_list) ListView othersList;
    @Bind(R.id.loading_box) View loadingBox;
    @Bind(R.id.no_monsters) TextView nothing;
    @Bind(R.id.entered_id) TextView chosenId;
    @Bind(R.id.pad_id_input) AutoCompleteTextView othersId;

    private MonsterBoxAdapter boxAdapter;
    private Set<String> mySet = PreferencesManager.get().getFavorites();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_search);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);

        boxAdapter = new MonsterBoxAdapter(this);
        othersList.setAdapter(boxAdapter);

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
        FormUtils.hideKeyboard(this);
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
        GetMonsterBoxCallback callback = new GetMonsterBoxCallback(true);
        RestClient.getInstance().getPffService().getMonsterBox(padId).enqueue(callback);
    }

    @OnClick(R.id.star_icon)
    public void onStar() {
        String userId = chosenId.getText().toString();
        if (!userId.equals(getString(R.string.no_id_chosen))) {
            if (PreferencesManager.get().isFavorited(userId)) {
                star.setTextColor(getResources().getColor(R.color.silver));
                PreferencesManager.get().removeFavorite(userId);
            }
            else {
                star.setTextColor(getResources().getColor(R.color.gold));
                PreferencesManager.get().addFavorite(userId);
                mySet.add(userId);
                setUpAdapter();
            }
        }
    }

    @OnClick(R.id.clipboard_icon)
    public void onClipboard() {
        FormUtils.hideKeyboard(this);
        String userId = chosenId.getText().toString();
        if (!userId.equals(getString(R.string.no_id_chosen))) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(Constants.PAD_ID_KEY, userId);
            clipboard.setPrimaryClip(clip);
            FormUtils.showSnackbar(parent, getString(R.string.id_copied));
        }
    }

    // For when you're copy-pasting an ID in instead of typing it.
    @OnClick(R.id.search_icon)
    public void onSearch() {
        FormUtils.hideKeyboard(this);
        if (FormUtils.validatePadId(othersId.getText().toString(), parent)) {
            displayResult(othersId.getText().toString());
        }
    }

    public void onEvent(MonsterBoxEvent event) {
        if (event.isOthersBox()) {
            boxAdapter.clear();
            boxAdapter.addMonsters(event.getMonsterList());
            refreshContent();
        }
    }

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(LOG_TAG)) {
            loadingBox.setVisibility(View.GONE);
            FormUtils.showSnackbar(parent, event.getMessage());
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
