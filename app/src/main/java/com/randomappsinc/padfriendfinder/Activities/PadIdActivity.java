package com.randomappsinc.padfriendfinder.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    @Bind(R.id.pad_id_input) EditText padIdInput;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.pad_id_form);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_pad_id)
    public void onClick(View v)
    {
        final String input = padIdInput.getText().toString();

        if (FormUtils.validatePadId(padIdInput.getText().toString()))
        {
            FormUtils.hideKeyboard(this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle(Constants.PAD_ID_CONFIRMATION);
            alertDialogBuilder
                    .setMessage("You have entered " + input + " as your PAD ID. " +
                            "Are you absolutely sure that this is correct? " +
                            "Remember, you can only enter your ID in once.")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            PreferencesManager.get().setPadId(input);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), Constants.WELCOME_MESSAGE, Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
    }
}
