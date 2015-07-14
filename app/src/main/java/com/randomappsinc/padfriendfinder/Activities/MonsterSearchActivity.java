package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
public class MonsterSearchActivity extends FragmentActivity
{
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

    public boolean killKeyboard()
    {
        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
        Rect r = new Rect();
        view.getWindowVisibleDisplayFrame(r);

        int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
        if (heightDiff > 100)
        {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.monster_search);

        context = this;
        godMapper = GodMapper.getGodMapper();

        // Find views
        hypermax = (Button) findViewById(R.id.hypermax);
        monsterEditText = (AutoCompleteTextView) findViewById(R.id.monster_search_box);
        submitMonster = (Button) findViewById(R.id.submit_monster);
        monsterPicture = (ImageView) findViewById(R.id.monster_picture);
        level = (EditText) findViewById(R.id.monster_level);
        numAwakenings = (EditText) findViewById(R.id.monster_num_awakenings);
        skillLevel = (EditText) findViewById(R.id.monster_skill_level);
        numPlusEggs = (EditText) findViewById(R.id.monster_num_plus_eggs);

        // Set listeners and adapters
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
                    monsterPicture.setImageResource(monsterAttributes.getDrawableID());
                }
                else
                {
                    monsterPicture.setImageDrawable(null);
                }
            }
        });
        monsterEditText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                killKeyboard();
            }
        });
        hypermax.setOnClickListener(hypermaxListener);
        submitMonster.setOnClickListener(submitListener);

        MonsterSearchAdapter adapter = new MonsterSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, godMapper.getFriendFinderMonsterList());
        monsterEditText.setAdapter(adapter);
    }

    View.OnClickListener hypermaxListener = new View.OnClickListener() {
        public void onClick(View v)
        {
            String monsterName = monsterEditText.getText().toString();
            MonsterAttributes monsterAttributes = godMapper.getMonsterAttributes(monsterName);
            if (monsterAttributes != null)
            {
                level.setText(String.valueOf(monsterAttributes.getMaxLevel()));
                numAwakenings.setText(String.valueOf(monsterAttributes.getMaxAwakenings()));
                skillLevel.setText(String.valueOf(monsterAttributes.getMaxSkill()));
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
                    Toast.makeText(context, Constants.MONSTER_FORM_INCOMPLETE, Toast.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
