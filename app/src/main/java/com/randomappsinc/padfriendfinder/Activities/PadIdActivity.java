package com.randomappsinc.padfriendfinder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Callbacks.BasicCallback;
import com.randomappsinc.padfriendfinder.API.Events.BasicResponseEvent;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.R;
import com.randomappsinc.padfriendfinder.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PadIdActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.pad_id_instructions) TextView instructions;
    @Bind(R.id.pad_id_input) EditText padIdInput;

    private boolean settingsMode;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_id_form);
        ButterKnife.bind(this);
        settingsMode = getIntent().getBooleanExtra(Constants.SETTINGS_KEY, false);
        EventBus.getDefault().register(this);

        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.changing_id)
                .progress(true, 0)
                .build();

        if (settingsMode) {
            String currentPadId = PreferencesManager.get().getPadId();
            padIdInput.setText(currentPadId);
            padIdInput.setSelection(currentPadId.length());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            instructions.setText(R.string.enter_in_new_id);
        }
    }

    @OnClick(R.id.submit_pad_id)
    public void onClick() {
        String newPadId = padIdInput.getText().toString();
        if (FormUtils.validatePadId(newPadId, parent)) {
            FormUtils.hideKeyboard(this);
            if (!settingsMode) {
                showConfirmDialog(newPadId);
            }
            else {
                if (!newPadId.equals(PreferencesManager.get().getPadId())) {
                    progressDialog.show();
                    BasicCallback callback = new BasicCallback(Constants.CHANGE_ID_KEY);
                    RestClient.getInstance().getPffService()
                            .changePadId(PreferencesManager.get().getPadId(), newPadId).enqueue(callback);

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

    public void onEvent(BasicResponseEvent event) {
        if (event.getEventType().equals(Constants.CHANGE_ID_KEY)) {
            progressDialog.dismiss();
            switch (event.getResponseCode()) {
                case ApiConstants.STATUS_OK:
                    PreferencesManager.get().setPadId(padIdInput.getText().toString());
                    Toast.makeText(this, R.string.id_change_success, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ApiConstants.BAD_REQUEST:
                    FormUtils.showSnackbar(parent, getString(R.string.id_already_used));
                    break;
                default:
                    FormUtils.showSnackbar(parent, getString(R.string.cannot_change_id));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
