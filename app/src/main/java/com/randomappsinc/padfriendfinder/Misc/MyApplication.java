package com.randomappsinc.padfriendfinder.Misc;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by alexanderchiou on 7/14/15.
 */

public final class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Iconify.with(new FontAwesomeModule());
    }

    public static Context getAppContext() {
        return context;
    }
}
