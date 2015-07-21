package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
public class MonsterSearchActivity extends ActionBarActivity
{
    public static final String LOOKING_FOR_HINT = "I am looking for...";
    public static final String MINIMUM_LEVEL = "Minimum Level";
    public static final String MINIMUM_AWAKENINGS = "Minimum # of awakenings";
    public static final String MINIMUM_PLUS_EGGS = "Minimum # of + eggs";
    public static final String MINIMUM_SKILL_LEVEL = "Minimum skill level";

    private Context context;
    private GodMapper godMapper;

    public static final int MAX_PLUS_EGGS = 297;

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
        monsterEditText.setHint(LOOKING_FOR_HINT);
        submitMonster = (Button) findViewById(R.id.submit_monster);
        monsterPicture = (ImageView) findViewById(R.id.monster_picture);
        level = (EditText) findViewById(R.id.level);
        level.setHint(MINIMUM_LEVEL);
        numAwakenings = (EditText) findViewById(R.id.num_awakenings);
        numAwakenings.setHint(MINIMUM_AWAKENINGS);
        skillLevel = (EditText) findViewById(R.id.skill_level);
        skillLevel.setHint(MINIMUM_SKILL_LEVEL);
        numPlusEggs = (EditText) findViewById(R.id.num_plus_eggs);
        numPlusEggs.setHint(MINIMUM_PLUS_EGGS);

        // Text listener for monster search with AC
        monsterEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                MonsterAttributes monsterAttributes = godMapper.getMonsterAttributes(s.toString());
                if (monsterAttributes != null)
                {
                    monsterPicture.setImageResource(monsterAttributes.getDrawableId());
                }
                else
                {
                    monsterPicture.setImageResource(R.mipmap.mystery_creature);
                    clearForm();
                }
            }
        });

        hypermax.setOnClickListener(hypermaxListener);
        submitMonster.setOnClickListener(submitListener);

        MonsterSearchAdapter adapter = new MonsterSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, godMapper.getFriendFinderMonsterList());
        monsterEditText.setAdapter(adapter);
    }

    private void clearForm()
    {
        level.setText("");
        numAwakenings.setText("");
        numPlusEggs.setText("");
        skillLevel.setText("");
    }

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
                numPlusEggs.setText(String.valueOf(MAX_PLUS_EGGS));
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
                    else
                    {
                        hideKeyboard();
                        // Make REST call and do legit business
                    }
                }
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
