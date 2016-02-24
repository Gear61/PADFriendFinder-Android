package com.randomappsinc.padfriendfinder.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.UpdateMonster;
import com.randomappsinc.padfriendfinder.Adapters.MonsterSearchAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;
import com.randomappsinc.padfriendfinder.Utils.MonsterSearchUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * Created by alexanderchiou on 7/13/15.
 */

// Used for searching for friends and the user updating their monster box
public class MonsterFormActivity extends StandardActivity {
    private String mode;

    // Views
    @Bind(R.id.monster_search_box) AutoCompleteTextView monsterEditText;
    @Bind(R.id.monster_picture) ImageView monsterPicture;
    @Bind(R.id.monster_name) TextView monsterName;
    @Bind(R.id.level) EditText level;
    @Bind(R.id.num_awakenings) EditText numAwakenings;
    @Bind(R.id.skill_level) EditText skillLevel;
    @Bind(R.id.num_plus_eggs) EditText numPlusEggs;

    private MaterialDialog updatingBoxDialog;
    private MonsterAttributes monsterChosen;
    private MonsterUpdateReceiver updateReceiver;
    private MonsterSearchAdapter monsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.monster_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        monsterAdapter = new MonsterSearchAdapter(this, android.R.layout.simple_dropdown_item_1line,
                MonsterServer.getMonsterServer().getFriendFinderMonsterList());
        updatingBoxDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .build();

        setUpPage();

        updateReceiver = new MonsterUpdateReceiver();
        registerReceiver(updateReceiver, new IntentFilter(Constants.MONSTER_UPDATE_KEY));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            unregisterReceiver(updateReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    @Override
    public void onPause()
    {
        super.onPause();
        FormUtils.hideKeyboard(this);
    }

    // Processes API call response
    private class MonsterUpdateReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            updatingBoxDialog.dismiss();
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                if (mode.equals(Constants.ADD_MODE))
                {
                    Toast.makeText(context, Constants.MONSTER_ADD_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
                    clearEverything();
                }
                else if (mode.equals(Constants.UPDATE_MODE))
                {
                    Toast.makeText(context, Constants.MONSTER_UPDATE_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else
            {
                if (mode.equals(Constants.ADD_MODE))
                {
                    Toast.makeText(context, Constants.MONSTER_ADD_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
                }
                else if (mode.equals(Constants.UPDATE_MODE))
                {
                    Toast.makeText(context, Constants.MONSTER_UPDATE_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void setUpPage()
    {
        mode = getIntent().getStringExtra(Constants.MODE_KEY);

        // Hint setting
        String minimumPrefix = mode.equals(Constants.SEARCH_MODE) ? "Min " : "";
        String searchHint = mode.equals(Constants.SEARCH_MODE) ? Constants.LOOKING_FOR_HINT : Constants.HAVE_A_HINT;
        monsterEditText.setHint(searchHint);
        level.setHint(minimumPrefix + Constants.LEVEL_HINT);
        numAwakenings.setHint(minimumPrefix + Constants.AWAKENINGS_HINT);
        skillLevel.setHint(minimumPrefix + Constants.SKILL_LEVEL_HINT);
        numPlusEggs.setHint(minimumPrefix + Constants.PLUS_EGGS_HINT);

        if (mode.equals(Constants.SEARCH_MODE))
        {
            setUpSearchMode();
            String monsterName = getIntent().getStringExtra(Constants.NAME_KEY);
            if (monsterName != null)
            {
                monsterChosen = MonsterServer.getMonsterServer().getMonsterAttributes(monsterName);
                if (monsterChosen != null)
                {
                    Picasso.with(this).load(monsterChosen.getImageUrl()).into(monsterPicture);
                    this.monsterName.setText(monsterName);
                    monsterEditText.setVisibility(View.GONE);
                    level.requestFocus();
                }
            }
        }
        else if (mode.equals(Constants.ADD_MODE)) {
            setUpAddMode();
        }
        else if (mode.equals(Constants.UPDATE_MODE)) {
            setUpUpdateMode();
        }
    }

    private void setUpSearchMode() {
        setTitle(R.string.find_friends);
        setUpMonsterInput();
    }

    private void setUpAddMode() {
        setTitle(R.string.add_monster);
        updatingBoxDialog.setContent(Constants.ADDING_MONSTER);
        setUpMonsterInput();
    }

    public void setUpMonsterInput() {
        // Text listener for monster search with AC
        monsterEditText.setAdapter(monsterAdapter);
        FormUtils.showKeyboard(this);
    }

    private void setUpUpdateMode() {
        setTitle(R.string.update_monster);
        MonsterAttributes monster = getIntent().getParcelableExtra(Constants.MONSTER_KEY);
        monsterChosen = MonsterServer.getMonsterServer().getMonsterAttributes(monster.getName());
        Picasso.with(this).load(monster.getImageUrl()).into(monsterPicture);
        monsterEditText.setVisibility(View.GONE);
        monsterName.setText(monster.getName());
        level.setText(String.valueOf(monster.getLevel()));
        numAwakenings.setText(String.valueOf(monster.getAwakenings()));
        numPlusEggs.setText(String.valueOf(monster.getPlusEggs()));
        skillLevel.setText(String.valueOf(monster.getSkillLevel()));
        updatingBoxDialog.setContent(Constants.UPDATING_MONSTER);
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
    public void afterTextChanged (Editable s) {
        MonsterAttributes monsterAttributes = MonsterServer.getMonsterServer().getMonsterAttributes(s.toString());
        if (monsterAttributes != null) {
            monsterChosen = monsterAttributes;
            Picasso.with(this).load(monsterAttributes.getImageUrl()).into(monsterPicture);
            monsterName.setText(s.toString());
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
    public void minimum(View v) {
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
            if (view.getId() == R.id.max_level) {
                level.setText(String.valueOf(monsterChosen.getLevel()));
            }
            else if (view.getId() == R.id.max_awakenings) {
                numAwakenings.setText(String.valueOf(monsterChosen.getAwakenings()));
            }
            else if (view.getId() == R.id.max_plus_eggs) {
                numPlusEggs.setText(String.valueOf(Constants.MAX_PLUS_EGGS));
            }
            else if (view.getId() == R.id.max_skill_level) {
                skillLevel.setText(String.valueOf(monsterChosen.getSkillLevel()));
            }
        }
    }

    @OnClick(R.id.submit_monster)
    public void onClick(View v)
    {
        if (monsterChosen != null) {
            if (level.getText().toString().isEmpty() || skillLevel.getText().toString().isEmpty() ||
                    numAwakenings.getText().toString().isEmpty() || numPlusEggs.getText().toString().isEmpty()) {
                Toast.makeText(this, Constants.MONSTER_FORM_INCOMPLETE_MESSAGE, Toast.LENGTH_LONG).show();
            }
            else {
                int monLevel = Integer.parseInt(level.getText().toString());
                int monNumAwakenings = Integer.parseInt(numAwakenings.getText().toString());
                int monSkillLevel = Integer.parseInt(skillLevel.getText().toString());
                int monNumPlusEggs = Integer.parseInt(numPlusEggs.getText().toString());
                String message = MonsterSearchUtils.createMonsterFormMessage(monLevel, monNumAwakenings,
                        monSkillLevel, monNumPlusEggs, monsterChosen);
                if (!message.isEmpty()) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                else if (MonsterBoxManager.getInstance().alreadyContainsMonster(monsterName.getText().toString()) &&
                        mode.equals(Constants.ADD_MODE)) {
                    Toast.makeText(this, "Your monster box already has a " + monsterName.getText().toString() + ".",
                            Toast.LENGTH_LONG).show();
                }
                // Everything is A-OK
                else {
                    FormUtils.hideKeyboard(this);
                    MonsterAttributes monster = new MonsterAttributes(monsterName.getText().toString(), monLevel,
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
                        new UpdateMonster(this, monster).execute();
                    }
                }
            }
        }
        else {
            Toast.makeText(this, Constants.INVALID_MONSTER_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }

    @OnFocusChange(R.id.monster_search_box)
    public void onFocusChange(boolean hasFocus) {
        if (hasFocus && getWindow().isActive()) {
            monsterEditText.showDropDown();
        }
    }
}
