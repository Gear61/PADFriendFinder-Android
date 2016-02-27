package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
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
    @Bind(R.id.parent) View parent;
    @Bind(R.id.monster_matches) ListView monsterMatches;

    private SupportedLeadsAdapter supportedLeadsAdapter;
    private boolean chooseAvatarMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void afterTextChanged(Editable input) {
        supportedLeadsAdapter.updateWithPrefix(input.toString());
    }

    @OnItemClick(R.id.monster_matches)
    public void onItemClick(int position) {
        if (chooseAvatarMode) {
            FormUtils.hideKeyboard(this);
            PreferencesManager.get().setAvatarId(supportedLeadsAdapter.getItem(position).getMonsterId());
            FormUtils.showSnackbar(parent, getString(R.string.avatar_is_now)
                    + supportedLeadsAdapter.getItem(position).getName() +  ".");
        }
        else {
            final Context context = this;
            final String monsterName = supportedLeadsAdapter.getItem(position).getName();

            String[] choices = new String[2];
            choices[0] = getString(R.string.search_for) + "\"" + monsterName + "\"";
            choices[1] = getString(R.string.find_hypermaxed) + "\"" + monsterName + "\"";

            new MaterialDialog.Builder(this)
                    .items(choices)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            Intent intent = null;
                            switch (which) {
                                case 0:
                                    intent = new Intent(context, MonsterFormActivity.class);
                                    intent.putExtra(Constants.NAME_KEY, monsterName);
                                    intent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
                                    break;
                                case 1:
                                    MonsterAttributes chosenMonster =
                                            MonsterServer.getMonsterServer().getMonsterAttributes(monsterName);
                                    chosenMonster.setPlusEggs(Constants.MAX_PLUS_EGGS);
                                    intent = new Intent(context, FriendResultsActivity.class);
                                    intent.putExtra(Constants.MONSTER_KEY, chosenMonster);
                            }
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }
}
