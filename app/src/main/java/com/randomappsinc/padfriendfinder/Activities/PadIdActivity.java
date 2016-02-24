package com.randomappsinc.padfriendfinder.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
public class PadIdActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.pad_id_instructions) TextView padID_textView;
    @Bind(R.id.pad_id_input) EditText padIdInput;

    private boolean settingsMode;
    private idChangeReceiver idChangeReceiver;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_id_form);
        ButterKnife.bind(this);
        settingsMode = getIntent().getBooleanExtra(Constants.SETTINGS_KEY, false);

        idChangeReceiver = new idChangeReceiver();
        registerReceiver(idChangeReceiver, new IntentFilter(Constants.ID_CHECKED_KEY));

        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.changing_id)
                .progress(true, 0)
                .build();

        if (settingsMode) {
            String currentPadId = PreferencesManager.get().getPadId();
            padIdInput.setText(currentPadId);
            padIdInput.setSelection(currentPadId.length());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            padID_textView.setText(Constants.CHANGE_ID_STRING);
        }
    }

    @OnClick(R.id.submit_pad_id)
    public void onClick() {
        String userPadId = padIdInput.getText().toString();
        if (FormUtils.validatePadId(userPadId, parent)) {
            FormUtils.hideKeyboard(this);
            if (!settingsMode) {
                showConfirmDialog(userPadId);
            }
            else {
                if (!userPadId.equals(PreferencesManager.get().getPadId())) {
                    progressDialog.show();
                    new ChangeID(this, userPadId).execute();
                }
                else {
                    FormUtils.showSnackbar(parent, getString(R.string.same_pad_id));
                }
            }
        }
    }

    private void showConfirmDialog(final String input) {
        final Context context = this;
        String message = getString(R.string.confirm_id_first) + input + ". " + getString(R.string.confirm_id_second);

        new MaterialDialog.Builder(this)
                .title(R.string.pad_id_confirmation)
                .content(message)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        PreferencesManager.get().setPadId(input);
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(context, R.string.welcome, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .show();
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
}
