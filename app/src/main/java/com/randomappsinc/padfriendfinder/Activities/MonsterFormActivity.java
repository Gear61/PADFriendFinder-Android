package com.randomappsinc.padfriendfinder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.Callbacks.MonsterUpdateCallback;
import com.randomappsinc.padfriendfinder.API.Events.MonsterUpdateEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.Models.PlayerMonster;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Adapters.MonsterSearchAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;
import com.randomappsinc.padfriendfinder.Utils.MonsterSearchUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 7/13/15.
 */

// Used for searching for friends and the user updating their monster box
public class MonsterFormActivity extends StandardActivity {
    public static final String LOG_TAG = "MonsterForm";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.monster_search_box) AutoCompleteTextView monsterEditText;
    @Bind(R.id.monster_picture) ImageView monsterPicture;
    @Bind(R.id.monster_name) TextView monsterName;
    @Bind(R.id.level) EditText level;
    @Bind(R.id.num_awakenings) EditText numAwakenings;
    @Bind(R.id.skill_level) EditText skillLevel;
    @Bind(R.id.num_plus_eggs) EditText numPlusEggs;

    private String mode;
    private MaterialDialog updatingBoxDialog;
    private Monster monsterChosen;
    private MonsterSearchAdapter monsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.monster_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);

        monsterAdapter = new MonsterSearchAdapter(this, android.R.layout.simple_dropdown_item_1line,
                MonsterServer.getMonsterServer().getFriendFinderMonsterList());
        updatingBoxDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .build();

        setUpPage();
    }

    @Override
    public void onPause() {
        super.onPause();
        FormUtils.hideKeyboard(this);
    }

    private void setUpPage() {
        mode = getIntent().getStringExtra(Constants.MODE_KEY);

        // Hint setting
        String minimumPrefix = mode.equals(Constants.SEARCH_MODE) ? "Min " : "";
        String searchHint = mode.equals(Constants.SEARCH_MODE)
                ? getString(R.string.looking_for_hint)
                : getString(R.string.have_a_hint);
        monsterEditText.setHint(searchHint);
        level.setHint(minimumPrefix + getString(R.string.level_hint));
        numAwakenings.setHint(minimumPrefix + getString(R.string.awakenings_hint));
        skillLevel.setHint(minimumPrefix + getString(R.string.skill_level_hint));
        numPlusEggs.setHint(minimumPrefix + getString(R.string.plus_eggs_hint));

        switch (mode) {
            case Constants.SEARCH_MODE:
                setUpSearchMode();
                String monsterName = getIntent().getStringExtra(Constants.NAME_KEY);
                if (monsterName != null) {
                    monsterChosen = MonsterServer.getMonsterServer().getMonsterAttributes(monsterName);
                    if (monsterChosen != null) {
                        Picasso.with(this).load(monsterChosen.getImageUrl()).into(monsterPicture);
                        this.monsterName.setText(monsterName);
                        monsterEditText.setVisibility(View.GONE);
                        level.requestFocus();
                    }
                }
                break;
            case Constants.ADD_MODE:
                setUpAddMode();
                break;
            case Constants.UPDATE_MODE:
                setUpUpdateMode();
        }
    }

    private void setUpSearchMode() {
        setTitle(R.string.find_friends);
        setUpMonsterInput();
    }

    private void setUpAddMode() {
        setTitle(R.string.add_monster);
        updatingBoxDialog.setContent(R.string.adding_monster);
        setUpMonsterInput();
    }

    public void setUpMonsterInput() {
        // Text listener for monster search with AC
        monsterEditText.setAdapter(monsterAdapter);
        FormUtils.showKeyboard(this);
    }

    private void setUpUpdateMode() {
        setTitle(R.string.update_monster);
        Monster monster = getIntent().getParcelableExtra(Constants.MONSTER_KEY);
        monsterChosen = MonsterServer.getMonsterServer().getMonsterAttributes(monster.getName());
        Picasso.with(this).load(monster.getImageUrl()).into(monsterPicture);
        monsterEditText.setVisibility(View.GONE);
        monsterName.setText(monster.getName());
        level.setText(String.valueOf(monster.getLevel()));
        numAwakenings.setText(String.valueOf(monster.getAwakenings()));
        numPlusEggs.setText(String.valueOf(monster.getPlusEggs()));
        skillLevel.setText(String.valueOf(monster.getSkillLevel()));
        updatingBoxDialog.setContent(R.string.updating_monster);
    }

    private void clearEverything() {
        clearForm();
        monsterChosen = null;
        monsterEditText.setText("");
        monsterPicture.setImageResource(R.mipmap.mystery_creature);
        monsterName.setText(getString(R.string.no_monster_chosen));
        monsterEditText.setVisibility(View.VISIBLE);
        monsterPicture.requestFocus();
    }

    private void clearForm() {
        level.setText("");
        numAwakenings.setText("");
        numPlusEggs.setText("");
        skillLevel.setText("");
    }

    @OnTextChanged(value = R.id.monster_search_box, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged (Editable input) {
        Monster monster = MonsterServer.getMonsterServer().getMonsterAttributes(input.toString());
        if (monster != null) {
            monsterChosen = monster;
            Picasso.with(this).load(monster.getImageUrl()).into(monsterPicture);
            monsterName.setText(input.toString());
            monsterEditText.setText("");
            level.requestFocus();
        }
    }

    @OnClick(R.id.hypermax)
    public void hypermax() {
        if (monsterChosen != null) {
            FormUtils.hideKeyboard(this);
            level.setText(String.valueOf(monsterChosen.getLevel()));
            numAwakenings.setText(String.valueOf(monsterChosen.getAwakenings()));
            skillLevel.setText(String.valueOf(monsterChosen.getSkillLevel()));
            numPlusEggs.setText(String.valueOf(Constants.MAX_PLUS_EGGS));
            monsterPicture.requestFocus();
        }
    }

    @OnClick(R.id.minimum)
    public void minimum() {
        FormUtils.hideKeyboard(this);
        level.setText(String.valueOf(1));
        numAwakenings.setText(String.valueOf(0));
        skillLevel.setText(String.valueOf(1));
        numPlusEggs.setText(String.valueOf(0));
        monsterPicture.requestFocus();
    }

    @OnClick({R.id.max_level, R.id.max_awakenings, R.id.max_plus_eggs, R.id.max_skill_level})
    public void max(View view) {
        if (monsterChosen != null) {
            switch (view.getId()) {
                case R.id.max_level:
                    level.setText(String.valueOf(monsterChosen.getLevel()));
                    break;
                case R.id.max_awakenings:
                    numAwakenings.setText(String.valueOf(monsterChosen.getAwakenings()));
                    break;
                case R.id.max_plus_eggs:
                    numPlusEggs.setText(String.valueOf(Constants.MAX_PLUS_EGGS));
                    break;
                case R.id.max_skill_level:
                    skillLevel.setText(String.valueOf(monsterChosen.getSkillLevel()));
            }
        }
    }

    @OnClick(R.id.submit_monster)
    public void onClick(View v) {
        if (monsterChosen != null) {
            if (level.getText().toString().isEmpty() || skillLevel.getText().toString().isEmpty() ||
                    numAwakenings.getText().toString().isEmpty() || numPlusEggs.getText().toString().isEmpty()) {
                FormUtils.showSnackbar(parent, getString(R.string.incomplete_monster_form));
            }
            else {
                int monLevel = Integer.parseInt(level.getText().toString());
                int monNumAwakenings = Integer.parseInt(numAwakenings.getText().toString());
                int monSkillLevel = Integer.parseInt(skillLevel.getText().toString());
                int monNumPlusEggs = Integer.parseInt(numPlusEggs.getText().toString());
                String message = MonsterSearchUtils.createMonsterFormMessage(monLevel, monNumAwakenings,
                        monSkillLevel, monNumPlusEggs, monsterChosen);
                String enteredName = monsterName.getText().toString();
                if (!message.isEmpty()) {
                    FormUtils.showSnackbar(parent, message);
                }
                else if (MonsterBoxManager.getInstance().alreadyContainsMonster(enteredName) &&
                        mode.equals(Constants.ADD_MODE)) {
                    FormUtils.showSnackbar(parent, getString(R.string.monster_box_dupe) + enteredName + ".");
                }
                // Everything is A-OK
                else {
                    FormUtils.hideKeyboard(this);
                    Monster monster = new Monster(enteredName, monLevel,
                            monNumAwakenings, monNumPlusEggs, monSkillLevel);
                    monster.setImageUrl(monsterChosen.getImageUrl());
                    if (mode.equals(Constants.SEARCH_MODE)) {
                        Intent intent = new Intent(this, FriendResultsActivity.class);
                        intent.putExtra(Constants.MONSTER_KEY, monster);
                        startActivity(intent);
                        clearEverything();
                    }
                    else if (mode.equals(Constants.ADD_MODE) || mode.equals(Constants.UPDATE_MODE)) {
                        updatingBoxDialog.show();
                        MonsterUpdateCallback callback = new MonsterUpdateCallback(mode, monster);
                        PlayerMonster playerMonster = new PlayerMonster(monster);
                        RestClient.getInstance().getPffService().updateMonster(playerMonster).enqueue(callback);
                    }
                }
            }
        }
        else {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_monster));
        }
    }

    public void onEvent(MonsterUpdateEvent event) {
        updatingBoxDialog.dismiss();
        if (event.getMode().equals(Constants.ADD_MODE)) {
            clearEverything();
            FormUtils.showSnackbar(parent, getString(R.string.add_monster_success));
        }
        else {
            finish();
        }
    }

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(LOG_TAG)) {
            updatingBoxDialog.dismiss();
            FormUtils.showSnackbar(parent, event.getMessage());
        }
    }

    @OnFocusChange(R.id.monster_search_box)
    public void onFocusChange(boolean hasFocus) {
        if (hasFocus && getWindow().isActive()) {
            monsterEditText.showDropDown();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
