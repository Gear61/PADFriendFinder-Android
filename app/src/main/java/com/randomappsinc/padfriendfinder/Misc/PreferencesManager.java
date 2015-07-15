package com.randomappsinc.padfriendfinder.Misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PreferencesManager
{
    private static PreferencesManager instance;

    public static PreferencesManager get()
    {
        if (instance == null)
        {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized PreferencesManager getSync()
    {
        if (instance == null)
        {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private Context context;
    private SharedPreferences prefs;

    private static final String PREFS_KEY = "com.randomappsinc.padfriendfinder";
    private static final String PAD_ID = "com.randomappsinc.padfriendfinder.padId";

    private PreferencesManager()
    {
        this.context = Application.get().getApplicationContext();
        prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    public String getPadId()
    {
        return prefs.getString(PAD_ID, "");
    }

    public PreferencesManager setPadId(String padId)
    {
        prefs.edit().putString(PAD_ID, padId).apply();
        return this;
    }
}
