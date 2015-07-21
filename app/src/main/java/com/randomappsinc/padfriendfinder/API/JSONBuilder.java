package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by alexanderchiou on 7/17/15.
 */

public class JSONBuilder
{
    public static JSONObject loginBuilder(String email, String password, String publicKey)
    {
        JSONObject monsterJson = new JSONObject();
        try
        {
            monsterJson.put("FISH", "");
        }
        catch (JSONException ignored) {}
        return monsterJson;
    }

    public static String updateMonsterBoxBuilder(List<MonsterAttributes> monsters)
    {
        JSONObject monsterBoxJson = new JSONObject();
        try
        {
            monsterBoxJson.put("", PreferencesManager.get().getPadId());
        }
        catch (JSONException ignored) {}
        return monsterBoxJson.toString();
    }
}
