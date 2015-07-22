package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.GodMapper;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class JSONParser
{
    public static MonsterAttributes parseMonsterJson(JSONObject monsterJson)
    {
        MonsterAttributes monster = new MonsterAttributes();
        try
        {
            String monsterName = monsterJson.getString(Constants.NAME_KEY);
            monster.setName(monsterName);
            int drawableId = GodMapper.getGodMapper().getMonsterAttributes(monsterName).getDrawableId();
            monster.setDrawableId(drawableId);
        }
        catch (JSONException ignored) {}
        try
        {
            monster.setLevel(monsterJson.getInt(Constants.LEVEL_KEY));
            monster.setSkillLevel(monsterJson.getInt(Constants.SKILL_LEVEL_KEY));
            monster.setAwakenings(monsterJson.getInt(Constants.AWAKENINGS_KEY));
            monster.setPlusEggs(monsterJson.getInt(Constants.PLUS_EGGS_KEY));
        }
        catch (JSONException e) {}
        return monster;
    }

    public static List<Friend> parseFriendCandidatesResponse(String response)
    {
        List<Friend> friends = new ArrayList<>();
        try
        {
            JSONArray friendsArray = new JSONArray(response);
            for (int i = 0; i < friendsArray.length(); i++)
            {
                JSONObject friendJson = friendsArray.getJSONObject(i);
                String padId = friendJson.getString(Constants.PLAYER_ID_KEY);
                Friend friend = new Friend(padId, parseMonsterJson(friendJson));
                friends.add(friend);
            }
        }
        catch (JSONException e) {}
        return friends;
    }

    public static List<MonsterAttributes> parseMonsterBoxResponse(String response)
    {
        List<MonsterAttributes> monsters = new ArrayList<>();
        try
        {
            JSONArray monstersArray = new JSONArray(response);
            for (int i = 0; i < monstersArray.length(); i++)
            {
                JSONObject monsterJson = monstersArray.getJSONObject(i);
                monsters.add(parseMonsterJson(monsterJson));
            }
        }
        catch (JSONException e) {}
        return monsters;
    }
}
