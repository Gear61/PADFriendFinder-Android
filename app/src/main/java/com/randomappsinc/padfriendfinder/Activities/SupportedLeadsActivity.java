package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.padfriendfinder.Adapters.SupportedLeadsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.Monster;
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

        if (PreferencesManager.get().shouldShowSuggestTip()) {
            new MaterialDialog.Builder(this)
                    .content(R.string.suggest_lead_instructions)
                    .positiveText(android.R.string.yes)
                    .show();
        }
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
                                    Monster chosenMonster =
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.supported_leads_menu, menu);
        menu.findItem(R.id.suggest_leader).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_plus)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.suggest_leader) {
            String uriText = "mailto:" + SettingsActivity.SUPPORT_EMAIL +
                    "?subject=" + Uri.encode(getString(R.string.suggest_leader_title));
            Uri mailUri = Uri.parse(uriText);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(mailUri);
            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_email)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
