package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Adapters.MonsterSearchAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.GodMapper;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.MonsterSearchUtils;

/**
 * Created by alexanderchiou on 7/13/15.
 */

// Used for searching for friends and the user updating their monster box
public class MonsterFormActivity extends ActionBarActivity
{
    private Context context;
    private GodMapper godMapper;
    private String mode;

    // Views
    private AutoCompleteTextView monsterEditText;
    private Button hypermax;
    private Button submitMonster;
    private ImageView monsterPicture;
    private EditText level;
    private EditText numAwakenings;
    private EditText skillLevel;
    private EditText numPlusEggs;

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(monsterEditText.getWindowToken(), 0);
    }

    public void showKeyboard()
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.monster_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        godMapper = GodMapper.getGodMapper();

        // Find views
        hypermax = (Button) findViewById(R.id.hypermax);
        monsterEditText = (AutoCompleteTextView) findViewById(R.id.monster_search_box);
        submitMonster = (Button) findViewById(R.id.submit_monster);
        monsterPicture = (ImageView) findViewById(R.id.monster_picture);
        level = (EditText) findViewById(R.id.level);
        numAwakenings = (EditText) findViewById(R.id.num_awakenings);
        skillLevel = (EditText) findViewById(R.id.skill_level);
        numPlusEggs = (EditText) findViewById(R.id.num_plus_eggs);

        Intent intent = getIntent();
        setUpPage(intent);

        hypermax.setOnClickListener(hypermaxListener);
        submitMonster.setOnClickListener(submitListener);
    }

    private void setUpPage(Intent intent)
    {
        mode = intent.getStringExtra(Constants.MODE_KEY);

        // Hint setting
        String minimumPrefix = mode.equals(Constants.SEARCH_MODE) ? "Minimum " : "";
        String searchHint = mode.equals(Constants.SEARCH_MODE) ? Constants.LOOKING_FOR_HINT : Constants.HAVE_A_HINT;
        monsterEditText.setHint(searchHint);
        level.setHint(minimumPrefix + Constants.LEVEL_HINT);
        numAwakenings.setHint(minimumPrefix + Constants.AWAKENINGS_HINT);
        skillLevel.setHint(minimumPrefix + Constants.SKILL_LEVEL_HINT);
        numPlusEggs.setHint(minimumPrefix + Constants.PLUS_EGGS_HINT);

        if (mode.equals(Constants.SEARCH_MODE))
        {
            setUpSearchMode();
            showKeyboard();
        }
        else if (mode.equals(Constants.ADD_MODE))
        {
            setUpAddMode();
            showKeyboard();
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
        setUpMonsterInput();
    }

    public void setUpMonsterInput()
    {
        // Text listener for monster search with AC
        monsterEditText.addTextChangedListener(monsterInputListener);
        MonsterSearchAdapter adapter = new MonsterSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, godMapper.getFriendFinderMonsterList());
        monsterEditText.setAdapter(adapter);
    }

    private void setUpUpdateMode(Intent intent)
    {
        setTitle(Constants.UPDATE_MONSTER_LABEL);
        Bundle data = intent.getExtras();
        MonsterAttributes monster = data.getParcelable(Constants.MONSTER_KEY);
        monsterPicture.setImageResource(monster.getDrawableId());
        monsterEditText.setText(monster.getName());
        level.setText(String.valueOf(monster.getLevel()));
        numAwakenings.setText(String.valueOf(monster.getAwakenings()));
        numPlusEggs.setText(String.valueOf(monster.getPlusEggs()));
        skillLevel.setText(String.valueOf(monster.getSkillLevel()));
        monsterEditText.setEnabled(false);
    }

    private void clearEverything()
    {
        clearForm();
        monsterEditText.setText("");
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
        public void beforeTextChanged (CharSequence s,int start, int count, int after){}

        @Override
        public void onTextChanged (CharSequence s,int start, int before, int count)
        {
            MonsterAttributes monsterAttributes = godMapper.getMonsterAttributes(s.toString());
            if (monsterAttributes != null)
            {
                monsterPicture.setImageResource(monsterAttributes.getDrawableId());
            }
            else
            {
                monsterPicture.setImageResource(R.mipmap.mystery_creature);
            }
        }
    };

    // Fills in form with hypermaxed values
    View.OnClickListener hypermaxListener = new View.OnClickListener() {
        public void onClick(View v)
        {
            String monsterName = monsterEditText.getText().toString();
            MonsterAttributes monsterAttributes = godMapper.getMonsterAttributes(monsterName);
            if (monsterAttributes != null)
            {
                level.setText(String.valueOf(monsterAttributes.getLevel()));
                numAwakenings.setText(String.valueOf(monsterAttributes.getAwakenings()));
                skillLevel.setText(String.valueOf(monsterAttributes.getSkillLevel()));
                numPlusEggs.setText(String.valueOf(Constants.MAX_PLUS_EGGS));
            }
        }
    };

    View.OnClickListener submitListener = new View.OnClickListener() {
        public void onClick(View v)
        {
            String monsterName = monsterEditText.getText().toString();
            MonsterAttributes monsterAttributes = godMapper.getMonsterAttributes(monsterName);
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
                    // Everything is A-OK
                    else
                    {
                        hideKeyboard();
                        MonsterAttributes monster = new MonsterAttributes(monsterName, monLevel,
                                monNumAwakenings, monNumPlusEggs, monSkillLevel);
                        monster.setDrawableId(monsterAttributes.getDrawableId());
                        if (mode.equals(Constants.SEARCH_MODE))
                        {
                            Intent intent = new Intent(context, FriendResultsActivity.class);
                            intent.putExtra(Constants.MONSTER_KEY, monster);
                            startActivity(intent);
                            clearEverything();
                        }
                    }
                }
            }
            else
            {
                Toast.makeText(context, Constants.INVALID_MONSTER_MESSAGE, Toast.LENGTH_LONG).show();
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
