package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OthersBoxActivity extends AppCompatActivity
{
    @Bind(R.id.star_icon)
    FontAwesomeText star;

    EditText othersId;
    String pad_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_box);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        othersId = (EditText) findViewById(R.id.PAD_ID);

        othersId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    TextView id = (TextView) findViewById(R.id.entered_id);
                    pad_id = othersId.getText().toString();
                    if (pad_id.length() == 9 && (pad_id.charAt(0) == '3'))
                        format_result(id, pad_id);
                    else
                        Toast.makeText(getApplicationContext(), "Error: Invalid Input.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    public void format_result(TextView id, String pad_id) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(othersId.getWindowToken(), 0);
        id.setText(pad_id);
        othersId.setText(null);
        if (PreferencesManager.get().isFavorited(pad_id))
            star.setTextColor(getResources().getColor(R.color.gold));
        else
            star.setTextColor(getResources().getColor(R.color.silver));
    }

    @OnClick(R.id.star_icon)
    public void onStar(View view) {
        String user_id = ((TextView) findViewById(R.id.entered_id)).getText().toString();
        if (user_id != null) {
            if (PreferencesManager.get().isFavorited(user_id)) {
                star.setTextColor(getResources().getColor(R.color.silver));
                PreferencesManager.get().removeFavorite(user_id);
            } else {
                star.setTextColor(getResources().getColor(R.color.gold));
                PreferencesManager.get().addFavorite(user_id);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_others_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
