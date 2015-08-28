package com.randomappsinc.padfriendfinder.Misc;

/**
 * Created by alexanderchiou on 7/13/15.
 */

import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodMapper
{
    private static GodMapper instance = null;
    private static HashMap<String, MonsterAttributes> nameToAttributes = new HashMap<>();
    private static Set<String> friendFinderMonsterList = new HashSet<>();

    private GodMapper() {}

    public static GodMapper getGodMapper()
    {
        if (instance == null)
        {
            instance = new GodMapper();
        }
        return instance;
    }

    public static void setUpMonsterMap(List<MonsterAttributes> monsters)
    {
        for (MonsterAttributes monster: monsters) {
            nameToAttributes.put(monster.getName(), monster);
        }
        setUpFriendFinderMonsterList();
    }

    private static void setUpFriendFinderMonsterList()
    {
        for (String key : nameToAttributes.keySet())
        {
            friendFinderMonsterList.add(key);
        }
    }

    public ArrayList<String> getFriendFinderMonsterList()
    {
        ArrayList<String> monsters = new ArrayList<>();
        monsters.addAll(friendFinderMonsterList);
        Collections.sort(monsters);
        return monsters;
    }

    public MonsterAttributes getMonsterAttributes(String monsterName)
    {
        return nameToAttributes.get(monsterName);
    }
}

