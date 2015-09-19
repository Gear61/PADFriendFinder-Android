package com.randomappsinc.padfriendfinder.Misc;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

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
    private static final String PAD_ID_KEY = "com.randomappsinc.padfriendfinder.padId";
    private static final String FAVORITES_KEY = "com.randomappsinc.padfriendfinder.favorites";
    private static final String AVATAR_ID_KEY = "com.randomappsinc.padfriendfinder.avatarId";

    private PreferencesManager()
    {
        this.context = Application.get().getApplicationContext();
        prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    public String getPadId()
    {
        return prefs.getString(PAD_ID_KEY, "");
    }

    public void addFavorite(String padId) {
        Set<String> favorites = getFavorites();
        favorites.add(padId);
        setFavorites(favorites);
    }

    public void removeFavorite(String padId) {
        Set<String> favorites = getFavorites();
        favorites.remove(padId);
        setFavorites(favorites);
    }

    public boolean isFavorited(String padId) {
        return getFavorites().contains(padId);
    }

    private void setFavorites(Set<String> favorites) {
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply();
    }

    public Set<String> getFavorites() {
        return new HashSet<>(prefs.getStringSet(FAVORITES_KEY, new HashSet<String>()));
    }

    public void setPadId(String padId)
    {
        prefs.edit().putString(PAD_ID_KEY, padId).apply();
    }

    public int getAvatarId()
    {
        return prefs.getInt(AVATAR_ID_KEY, 0);
    }

    public void setAvatarId(int avatarId)
    {
        prefs.edit().putInt(AVATAR_ID_KEY, avatarId).apply();
    }
}
