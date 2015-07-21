package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexanderchiou on 7/17/15.
 */

public class JSONBuilder
{
    public static JSONObject createMonsterJson(MonsterAttributes monster)
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

    public static String updateMonsterBoxBuilder(MonsterAttributes monster)
    {
        JSONObject monsterBoxJson = new JSONObject();
        try
        {
            monsterBoxJson.put(Constants.MONSTER_KEY, createMonsterJson(monster));
            monsterBoxJson.put(Constants.PLAYER_ID_KEY, PreferencesManager.get().getPadId());
        }
        catch (JSONException ignored) {}
        return monsterBoxJson.toString();
    }

    public static String monsterSearchBuilder(MonsterAttributes monster)
    {
        return createMonsterJson(monster).toString();
    }
}
