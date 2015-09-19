package com.randomappsinc.padfriendfinder.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.API.ChangeID;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PadIdActivity extends AppCompatActivity
{
    @Bind(R.id.padID_textView) TextView padID_textView;
    @Bind(R.id.pad_id_input) EditText padIdInput;

    private Context context;
    private boolean settingsMode;
    private idChangeReceiver idChangeReceiver;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.pad_id_form);
        ButterKnife.bind(this);
        settingsMode = getIntent().getBooleanExtra(Constants.SETTINGS_KEY, false);

        idChangeReceiver = new idChangeReceiver();
        this.registerReceiver(idChangeReceiver, new IntentFilter(Constants.ID_CHECKED_KEY));

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Constants.CHANGING_ID);
        if (settingsMode) {
            String currentPadId = PreferencesManager.get().getPadId();
            padIdInput.setText(currentPadId);
            padIdInput.setSelection(currentPadId.length());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            padID_textView.setText(Constants.CHANGE_ID_STRING);
        }
    }

    @OnClick(R.id.submit_pad_id)
    public void onClick(View v)
    {
        final String input = padIdInput.getText().toString();
        if (FormUtils.validatePadId(padIdInput.getText().toString()))
        {
            FormUtils.hideKeyboard(this);
            if (!settingsMode)
            {
                showDialog(input);
            }
            else
            {
                if (!input.equals(PreferencesManager.get().getPadId()))
                {
                    progressDialog.show();
                    new ChangeID(context, input).execute();
                }
                else
                {
                    Toast.makeText(context, Constants.ID_THE_SAME, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showDialog(final String input) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(Constants.PAD_ID_CONFIRMATION);
        alertDialogBuilder.setMessage("You have entered " + input + " as your PAD ID. " +
                "Are you absolutely sure that this is correct?").setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        PreferencesManager.get().setPadId(input);
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(context, Constants.WELCOME_MESSAGE, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private class idChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(Constants.ID_STATUS_KEY, 0);
            progressDialog.dismiss();
            if (status == 200) {
                Toast.makeText(context, Constants.ID_CHANGED, Toast.LENGTH_SHORT).show();
                finish();
            }
            else if (status == 400) {
                Toast.makeText(context, Constants.ID_ALREADY_IN_USE, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,  Constants.CANT_CHANGE_ID, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(idChangeReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blank_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
