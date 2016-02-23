package com.randomappsinc.padfriendfinder.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;

/**
 * Created by alexanderchiou on 9/9/15.
 */
public class FormUtils
{
    public static void hideKeyboard(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity)
    {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static boolean validatePadId(String padId) {
        Context context = MyApplication.getAppContext();
        if (padId.length() != 9) {
            Toast.makeText(context, Constants.INCOMPLETE_PAD_ID_MESSAGE, Toast.LENGTH_LONG).show();
            return false;
        }
        if (padId.charAt(0) != '3') {
            Toast.makeText(context, Constants.INCORRECT_FIRST_DIGIT_MESSAGE, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
