package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.TopLeader;

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
    public static MonsterAttributes parseMonsterJson(JSONObject monsterJson)
    {
        MonsterAttributes monster = new MonsterAttributes();
        try
        {
            String monsterName = monsterJson.getString(Constants.NAME_KEY);
            monster.setName(monsterName);
            MonsterAttributes mappedMonster = MonsterServer.getMonsterServer().getMonsterAttributes(monsterName);
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

    public static void parseMonsterListResponse(String response) {
        List<MonsterAttributes> monsters = new ArrayList<>();
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

    public static TopLeader parseTopLeaderJSON(JSONObject topLeaderJSON) {
        TopLeader topLeader = new TopLeader();
        try {
            topLeader.setName(topLeaderJSON.getString("name"));
            topLeader.setLeaderCount(topLeaderJSON.getInt("count"));
        }
        catch (JSONException e) {}
        return topLeader;
    }

    public static List<TopLeader> parseTopLeaders(String response) {
        List<TopLeader> topLeaders = new ArrayList<>();
        try {
            JSONArray topLeadersArray = new JSONArray(response);
            for (int i = 0; i < topLeadersArray.length(); i++) {
                JSONObject topLeaderJSON = topLeadersArray.getJSONObject(i);
                topLeaders.add(parseTopLeaderJSON(topLeaderJSON));
            }
        }
        catch (JSONException e) {}
        return topLeaders;
    }
}
