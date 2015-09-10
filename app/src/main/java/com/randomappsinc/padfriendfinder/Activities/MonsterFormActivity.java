package com.randomappsinc.padfriendfinder.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.API.UpdateMonster;
import com.randomappsinc.padfriendfinder.Adapters.MonsterSearchAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.GodMapper;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;
import com.randomappsinc.padfriendfinder.Utils.MonsterSearchUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 7/13/15.
 */

// Used for searching for friends and the user updating their monster box
public class MonsterFormActivity extends AppCompatActivity
{
    private Context context;
    private String mode;

    // Views
    @Bind(R.id.monster_search_box) AutoCompleteTextView monsterEditText;
    @Bind(R.id.monster_picture) ImageView monsterPicture;
    @Bind(R.id.level) EditText level;
    @Bind(R.id.num_awakenings) EditText numAwakenings;
    @Bind(R.id.skill_level) EditText skillLevel;
    @Bind(R.id.num_plus_eggs) EditText numPlusEggs;

    private ProgressDialog updatingBoxDialog;
    private String monsterChosen;
    private MonsterUpdateReceiver updateReceiver;
    private MonsterSearchAdapter monsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.monster_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        monsterAdapter = new MonsterSearchAdapter(context, android.R.layout.simple_dropdown_item_1line,
                GodMapper.getGodMapper().getFriendFinderMonsterList());
        updatingBoxDialog = new ProgressDialog(context);

        Intent intent = getIntent();
        setUpPage(intent);

        updateReceiver = new MonsterUpdateReceiver();
        context.registerReceiver(updateReceiver, new IntentFilter(Constants.MONSTER_UPDATE_KEY));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            context.unregisterReceiver(updateReceiver);
        }
        catch (IllegalArgumentException ignored) {}
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

    private void setUpPage(Intent intent)
    {
        mode = intent.getStringExtra(Constants.MODE_KEY);

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
            String monsterName = intent.getStringExtra(Constants.NAME_KEY);
            if (monsterName != null)
            {
                MonsterAttributes seededMonster = GodMapper.getGodMapper().getMonsterAttributes(monsterName);
                if (seededMonster != null)
                {
                    monsterEditText.setText(monsterName);
                    Picasso.with(context).load(seededMonster.getImageUrl()).into(monsterPicture);
                    monsterEditText.setAdapter(null);
                    level.requestFocus();
                }
            }
        }
        else if (mode.equals(Constants.ADD_MODE))
        {
            setUpAddMode();
        }
        else if (mode.equals(Constants.UPDATE_MODE))
        {
            setUpUpdateMode(intent);
        }
    }

    private void setUpSearchMode()
    {
        setTitle(Constants.FIND_FRIENDS_LABEL);
        setUpMonsterInput();
    }

    private void setUpAddMode()
    {
        setTitle(Constants.ADD_MONSTER_LABEL);
        updatingBoxDialog.setMessage(Constants.ADDING_MONSTER);
        setUpMonsterInput();
    }

    public void setUpMonsterInput()
    {
        // Text listener for monster search with AC
        monsterEditText.addTextChangedListener(monsterInputListener);
        monsterEditText.setAdapter(monsterAdapter);
        monsterEditText.setOnFocusChangeListener(monsterSearchFocusListener);
        FormUtils.showKeyboard(this);
    }

    private void setUpUpdateMode(Intent intent)
    {
        setTitle(Constants.UPDATE_MONSTER_LABEL);
        Bundle data = intent.getExtras();
        MonsterAttributes monster = data.getParcelable(Constants.MONSTER_KEY);
        Picasso.with(context).load(monster.getImageUrl()).into(monsterPicture);
        monsterEditText.setText(monster.getName());
        level.setText(String.valueOf(monster.getLevel()));
        numAwakenings.setText(String.valueOf(monster.getAwakenings()));
        numPlusEggs.setText(String.valueOf(monster.getPlusEggs()));
        skillLevel.setText(String.valueOf(monster.getSkillLevel()));
        monsterEditText.setEnabled(false);
        updatingBoxDialog.setMessage(Constants.UPDATING_MONSTER);
    }

    private void clearEverything()
    {
        clearForm();
        monsterChosen = null;
        monsterEditText.setText("");
        monsterEditText.setAdapter(monsterAdapter);
        monsterPicture.setImageResource(R.mipmap.mystery_creature);
        monsterPicture.requestFocus();
    }

    private void clearForm()
    {
        level.setText("");
        numAwakenings.setText("");
        numPlusEggs.setText("");
        skillLevel.setText("");
    }

    TextWatcher monsterInputListener = new TextWatcher()
    {
        @Override
        public void afterTextChanged (Editable s){}

        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after){}

        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
            String input = s.toString();
            if (monsterChosen != null)
            {
                // Prevent them from adding to target after it's chosen
                if (monsterChosen.length() + 1 == input.length())
                {
                    monsterEditText.setText(input.substring(0, input.length() - 1));
                }
                // If they're deleting and something is there, clear the entire thing
                else if (monsterChosen.length() - 1 == input.length())
                {
                    monsterChosen = null;
                    monsterEditText.setText("");
                    monsterEditText.setAdapter(monsterAdapter);
                }
            }
            else
            {
                MonsterAttributes monsterAttributes = GodMapper.getGodMapper().getMonsterAttributes(s.toString());
                if (monsterAttributes != null)
                {
                    monsterChosen = input;
                    Picasso.with(context).load(monsterAttributes.getImageUrl()).into(monsterPicture);
                    monsterEditText.setAdapter(null);
                }
                else
                {
                    monsterChosen = null;
                    monsterPicture.setImageResource(R.mipmap.mystery_creature);
                    monsterEditText.setAdapter(monsterAdapter);
                }
            }
        }
    };

    @OnClick(R.id.hypermax)
    public void hypermax(View v)
    {
        String monsterName = monsterEditText.getText().toString();
        MonsterAttributes monsterAttributes = GodMapper.getGodMapper().getMonsterAttributes(monsterName);
        if (monsterAttributes != null)
        {
            level.setText(String.valueOf(monsterAttributes.getLevel()));
            numAwakenings.setText(String.valueOf(monsterAttributes.getAwakenings()));
            skillLevel.setText(String.valueOf(monsterAttributes.getSkillLevel()));
            numPlusEggs.setText(String.valueOf(Constants.MAX_PLUS_EGGS));
        }
    }

    @OnClick(R.id.minimum)
    public void minimum(View v)
    {
        level.setText(String.valueOf(1));
        numAwakenings.setText(String.valueOf(0));
        skillLevel.setText(String.valueOf(1));
        numPlusEggs.setText(String.valueOf(0));
    }

    @OnClick({R.id.max_level, R.id.max_num_awakenings, R.id.max_plus_eggs, R.id.max_skill_level})
    public void max(View view)
    {
        MonsterAttributes monsterAttributes = GodMapper.getGodMapper().getMonsterAttributes(monsterChosen);
        if (monsterAttributes != null)
        {
            if (view.getId() == R.id.max_level)
            {
                level.setText(String.valueOf(monsterAttributes.getLevel()));
            }
            else if (view.getId() == R.id.max_num_awakenings)
            {
                numAwakenings.setText(String.valueOf(monsterAttributes.getAwakenings()));
            }
            else if (view.getId() == R.id.max_plus_eggs)
            {
                numPlusEggs.setText(String.valueOf(Constants.MAX_PLUS_EGGS));
            }
            else if (view.getId() == R.id.max_skill_level)
            {
                skillLevel.setText(String.valueOf(monsterAttributes.getSkillLevel()));
            }
        }
    }

    @OnClick(R.id.submit_monster)
    public void onClick(View v)
    {
        String monsterName = monsterEditText.getText().toString();
        MonsterAttributes monsterAttributes = GodMapper.getGodMapper().getMonsterAttributes(monsterName);
        if (monsterAttributes != null)
        {
            if (level.getText().toString().isEmpty() || skillLevel.getText().toString().isEmpty() ||
                    numAwakenings.getText().toString().isEmpty() || numPlusEggs.getText().toString().isEmpty())
            {
                Toast.makeText(context, Constants.MONSTER_FORM_INCOMPLETE_MESSAGE, Toast.LENGTH_LONG).show();
            }
            else
            {
                int monLevel = Integer.parseInt(level.getText().toString());
                int monNumAwakenings = Integer.parseInt(numAwakenings.getText().toString());
                int monSkillLevel = Integer.parseInt(skillLevel.getText().toString());
                int monNumPlusEggs = Integer.parseInt(numPlusEggs.getText().toString());
                String message = MonsterSearchUtils.createMonsterFormMessage(monLevel, monNumAwakenings,
                        monSkillLevel, monNumPlusEggs, monsterAttributes);
                if (!message.isEmpty())
                {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
                else if (MonsterBoxManager.getInstance().alreadyContainsMonster(monsterName) &&
                        mode.equals(Constants.ADD_MODE))
                {
                    Toast.makeText(context, "Your monster box already has a " + monsterName + ".",
                            Toast.LENGTH_LONG).show();
                }
                // Everything is A-OK
                else
                {
                    FormUtils.hideKeyboard(this);
                    MonsterAttributes monster = new MonsterAttributes(monsterName, monLevel,
                            monNumAwakenings, monNumPlusEggs, monSkillLevel);
                    monster.setImageUrl(GodMapper.getGodMapper().getMonsterAttributes(monsterName).getImageUrl());
                    if (mode.equals(Constants.SEARCH_MODE))
                    {
                        Intent intent = new Intent(context, FriendResultsActivity.class);
                        intent.putExtra(Constants.MONSTER_KEY, monster);
                        startActivity(intent);
                        clearEverything();
                    }
                    else if (mode.equals(Constants.ADD_MODE) || mode.equals(Constants.UPDATE_MODE))
                    {
                        updatingBoxDialog.show();
                        new UpdateMonster(context, monster).execute();
                    }
                }
            }
        }
        else
        {
            Toast.makeText(context, Constants.INVALID_MONSTER_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }

    // Monster AC focus change listener to allow for default suggestions
    AutoCompleteTextView.OnFocusChangeListener monsterSearchFocusListener = new AutoCompleteTextView.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && getWindow().isActive() && monsterChosen == null)
            {
                monsterEditText.showDropDown();
            }
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
