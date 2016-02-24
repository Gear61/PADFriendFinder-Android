package com.randomappsinc.padfriendfinder.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Adapters.FontAwesomeAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SettingsActivity extends StandardActivity {
    public static final String SUPPORT_EMAIL = "chessnone@gmail.com";
    public static final String OTHER_APPS_URL = "https://play.google.com/store/apps/dev?id=9093438553713389916";

    @Bind(R.id.settings_options) ListView settingsOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] options = getResources().getStringArray(R.array.settings_options);
        String[] icons = getResources().getStringArray(R.array.settings_icons);

        settingsOptions.setAdapter(new FontAwesomeAdapter(this, options, icons));
    }

    @OnItemClick(R.id.settings_options)
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, PadIdActivity.class);
                intent.putExtra(Constants.SETTINGS_KEY, true);
                break;
            case 1:
                intent = new Intent(this, SupportedLeadsActivity.class);
                intent.putExtra(Constants.CHOOSE_AVATAR_MODE, true);
                break;
            case 2:
                String uriText = "mailto:" + SUPPORT_EMAIL +
                                 "?subject=" + Uri.encode(getString(R.string.email_subject));
                Uri mailUri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(mailUri);
                startActivity(Intent.createChooser(sendIntent, getString(R.string.send_email)));
                return;
            case 3:
                Uri uri =  Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                intent = new Intent(Intent.ACTION_VIEW, uri);
                if (!(getPackageManager().queryIntentActivities(intent, 0).size() > 0)) {
                    Toast.makeText(this, getString(R.string.play_store_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case 4:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(OTHER_APPS_URL));
                break;
        }
        startActivity(intent);
    }
}
