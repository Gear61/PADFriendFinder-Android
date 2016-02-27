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
public class MonsterServer {
    private static MonsterServer instance = null;
    private static HashMap<String, MonsterAttributes> nameToAttributes = new HashMap<>();
    private static List<MonsterAttributes> allMonsters = new ArrayList<>();
    private static Set<String> friendFinderMonsterList = new HashSet<>();

    private MonsterServer() {}

    public static MonsterServer getMonsterServer() {
        if (instance == null)
        {
            instance = new MonsterServer();
        }
        return instance;
    }

    public void setUpMonsterMap(List<MonsterAttributes> monsters) {
        if (allMonsters.isEmpty()) {
            for (MonsterAttributes monster : monsters)
            {
                nameToAttributes.put(monster.getName(), monster);
                allMonsters.add(monster);
            }
            Collections.sort(allMonsters);
            setUpFriendFinderMonsterList();
        }
    }

    private void setUpFriendFinderMonsterList() {
        for (String key : nameToAttributes.keySet())
        {
            friendFinderMonsterList.add(key);
        }
    }

    public ArrayList<String> getFriendFinderMonsterList() {
        ArrayList<String> monsters = new ArrayList<>();
        monsters.addAll(friendFinderMonsterList);
        Collections.sort(monsters);
        return monsters;
    }

    public MonsterAttributes getMonsterAttributes(String monsterName) {
        if (monsterName == null) {
            return null;
        }
        return nameToAttributes.get(monsterName);
    }

    public List<MonsterAttributes> getMatches(String prefix) {
        if (prefix.isEmpty()) {
            return allMonsters;
        }
        List<MonsterAttributes> matches = new ArrayList<>();
        for (MonsterAttributes monster : nameToAttributes.values()) {
            if (monster.getName().toLowerCase().contains(prefix.toLowerCase())) {
                matches.add(monster);
            }
        }
        Collections.sort(matches);
        return matches;
    }
}

