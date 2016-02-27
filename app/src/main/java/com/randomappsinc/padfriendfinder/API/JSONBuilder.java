package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.Monster;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexanderchiou on 7/17/15.
 */

public class JSONBuilder
{
    private static JSONObject createMonsterJson(Monster monster)
    {
        JSONObject monsterJson = new JSONObject();
        try
        {
            monsterJson.put(Constants.NAME_KEY, monster.getName());
            monsterJson.put(Constants.LEVEL_KEY, monster.getLevel());
            monsterJson.put(Constants.AWAKENINGS_KEY, monster.getAwakenings());
            monsterJson.put(Constants.PLUS_EGGS_KEY, monster.getPlusEggs());
            monsterJson.put(Constants.SKILL_LEVEL_KEY, monster.getSkillLevel());
        }
        catch (JSONException ignored) {}
        return monsterJson;
    }

    // For adding/updating creatures in your box
    public static String updateMonsterBuilder(Monster monster)
    {
        JSONObject monsterBoxJson = new JSONObject();
        try
        {
            monsterBoxJson.put(Constants.MONSTER_KEY, createMonsterJson(monster));
            monsterBoxJson.put(Constants.PAD_ID_KEY, PreferencesManager.get().getPadId());
        }
        catch (JSONException ignored) {}
        return monsterBoxJson.toString();
    }

    // For finding friends
    public static String fetchFriendsBuilder(Monster monster)
    {
        return updateMonsterBuilder(monster).toString();
    }

    public static String deleteMonsterBuilder(String monsterName)
    {
        JSONObject deleteMonsterJson = new JSONObject();
        try
        {
            deleteMonsterJson.put(Constants.PAD_ID_KEY, PreferencesManager.get().getPadId());
            deleteMonsterJson.put(Constants.NAME_KEY, monsterName);
        }
        catch (JSONException ignored) {}
        return deleteMonsterJson.toString();
    }
}
