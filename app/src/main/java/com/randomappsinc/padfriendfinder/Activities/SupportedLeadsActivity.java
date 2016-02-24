package com.randomappsinc.padfriendfinder.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Adapters.MonsterItemAdapter;
import com.randomappsinc.padfriendfinder.Adapters.SupportedLeadsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class SupportedLeadsActivity extends StandardActivity {
    @Bind(R.id.monster_matches) ListView monsterMatches;

    private Context context;
    private SupportedLeadsAdapter supportedLeadsAdapter;
    private boolean chooseAvatarMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.supported_leads);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseAvatarMode = getIntent().getBooleanExtra(Constants.CHOOSE_AVATAR_MODE, false);
        if (chooseAvatarMode) {
            setTitle(getString(R.string.choose_avatar_label));
        }
        supportedLeadsAdapter = new SupportedLeadsAdapter(this);
        monsterMatches.setAdapter(supportedLeadsAdapter);
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        supportedLeadsAdapter.updateWithPrefix(s.toString());
    }

    @OnItemClick(R.id.monster_matches)
    public void onItemClick(final int position) {
        if (chooseAvatarMode) {
            FormUtils.hideKeyboard(this);
            PreferencesManager.get().setAvatarId(supportedLeadsAdapter.getItem(position).getMonsterId());
            Toast.makeText(this, "Your avatar is now " + supportedLeadsAdapter.getItem(position).getName() + ".",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(R.layout.ordinary_listview, null);
            alertDialogBuilder.setView(convertView);
            final String monsterName = supportedLeadsAdapter.getItem(position).getName();
            ListView monsterChoices = (ListView) convertView.findViewById(R.id.listView1);
            final MonsterItemAdapter adapter = new MonsterItemAdapter(context, monsterName);
            monsterChoices.setAdapter(adapter);
            final AlertDialog monsterChosenDialog = alertDialogBuilder.show();
            monsterChoices.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int dialogPosition, long id)
                {
                    monsterChosenDialog.dismiss();
                    String action = adapter.getItem(dialogPosition);
                    String name = supportedLeadsAdapter.getItem(position).getName();
                    MonsterAttributes monster;
                    MonsterAttributes monsterChosen = MonsterServer.getMonsterServer().getMonsterAttributes(name);
                    TopLeadersActivity.openSearchResults(action, name, monsterChosen, context);
                }
            });
            monsterChosenDialog.setCanceledOnTouchOutside(true);
            monsterChosenDialog.setCancelable(true);
        }
    }
}
