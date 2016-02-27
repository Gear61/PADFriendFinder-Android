package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.Monster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class JSONParser
{
    public static Monster parseMonsterJson(JSONObject monsterJson)
    {
        Monster monster = new Monster();
        try
        {
            String monsterName = monsterJson.getString(Constants.NAME_KEY);
            monster.setName(monsterName);
            Monster mappedMonster = MonsterServer.getMonsterServer().getMonsterAttributes(monsterName);
            if (mappedMonster != null)
                monster.setImageUrl(mappedMonster.getImageUrl());
            else  {
                try{
                    int monsterId = monsterJson.getInt(Constants.MONSTER_ID_KEY);
                    monster.setImageUrl(monsterId);
                }
                catch (JSONException ignored) {}
            }
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
            Set<String> favoritedIds = PreferencesManager.get().getFavorites();
            for (int i = 0; i < friendsArray.length(); i++)
            {
                JSONObject friendJson = friendsArray.getJSONObject(i);
                String padId = friendJson.getString(Constants.PAD_ID_KEY);
                if (favoritedIds.contains(padId))
                {
                    continue;
                }
                // Friend friend = new Friend(padId, parseMonsterJson(friendJson));
                // friends.add(friend);
            }
        }
        catch (JSONException e) {}
        return friends;
    }

    public static List<Monster> parseMonsterBoxResponse(String response)
    {
        List<Monster> monsters = new ArrayList<>();
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

    public static void parseMonsterListResponse(String response) {
        List<Monster> monsters = new ArrayList<>();
        try {
            JSONArray monstersArray = new JSONArray(response);
            for (int i = 0; i < monstersArray.length(); i++)
            {
                JSONObject monsterJson = monstersArray.getJSONObject(i);
                monsters.add(parseMonsterJson(monsterJson));
            }
        }
        catch (JSONException e) {}
        MonsterServer.getMonsterServer().setUpMonsterMap(monsters);
    }
}
