package com.randomappsinc.padfriendfinder.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Adapters.SettingsAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.SupportedLeadsActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.settings_options) ListView settingsOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> optionsList = Arrays.asList(getResources().getStringArray(R.array.settings_options));
        settingsOptions.setAdapter(new SettingsAdapter(this, android.R.layout.simple_dropdown_item_1line, optionsList));
    }

    @OnItemClick(R.id.settings_options)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(this, PadIdActivity.class);
                intent.putExtra(Constants.SETTINGS_KEY, true);
                break;
            case 1:
                intent = new Intent(this, SupportedLeadsActivity.class);
                intent.putExtra(Constants.CHOOSE_AVATAR_MODE, true);
                break;
            case 2:
                String uriText = "mailto:" + getString(R.string.support_email) +
                                 "?subject=" + Uri.encode(getString(R.string.email_subject));
                Uri mailUri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(mailUri);
                startActivity(Intent.createChooser(sendIntent, "Send email"));
                return;
            case 3:
                Uri uri =  Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                intent = new Intent(Intent.ACTION_VIEW, uri);
                if (!(getPackageManager().queryIntentActivities(intent, 0).size() > 0))
                {
                    Toast.makeText(this, getString(R.string.play_store_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blank_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
