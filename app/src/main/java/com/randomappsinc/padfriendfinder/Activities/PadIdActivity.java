package com.randomappsinc.padfriendfinder.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PadIdActivity extends ActionBarActivity
{

    private EditText padIdInput;
    private Button submitPadId;
    private Context context;

    public void showKeyboard()
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(padIdInput.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.pad_id_form);

        padIdInput = (EditText) findViewById(R.id.pad_id_input);
        submitPadId = (Button) findViewById(R.id.submit_pad_id);
        submitPadId.setOnClickListener(padIdSubmitListener);

        showKeyboard();
    }

    View.OnClickListener padIdSubmitListener = new View.OnClickListener() {
        public void onClick(View v) {
            final String input = padIdInput.getText().toString();

            if (input.length() != 9)
            {
                Toast.makeText(getApplicationContext(), Constants.INCOMPLETE_PAD_ID_MESSAGE, Toast.LENGTH_LONG).show();
            }
            else if (input.charAt(0) != '3')
            {
                Toast.makeText(getApplicationContext(), Constants.INCORRECT_FIRST_DIGIT_MESSAGE, Toast.LENGTH_LONG).show();
            }
            else
            {
                hideKeyboard();
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
        return super.onOptionsItemSelected(item);
    }
}
