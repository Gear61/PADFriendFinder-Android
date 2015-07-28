package com.randomappsinc.padfriendfinder.Misc;

import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class MonsterBoxManager
{
    private static MonsterBoxManager instance = null;
    // If this is null, this means that we need to make the API call to fetch their box
    private static List<MonsterAttributes> monsters;
    private static Set<String> monsterNames;

    private MonsterBoxManager() {}

    public static MonsterBoxManager getInstance()
    {
        if (instance == null)
        {
            instance = new MonsterBoxManager();
            monsterNames = new HashSet<>();
        }
        return instance;
    }

    public boolean alreadyContainsMonster(String monsterName)
    {
        return monsterNames.contains(monsterName);
    }

    public void addMonsters (List<MonsterAttributes> monsters)
    {
        if (this.monsters == null)
        {
            this.monsters = new ArrayList<>();
        }
        for (MonsterAttributes monster : monsters)
        {
            this.monsters.add(monster);
            this.monsterNames.add(monster.getName());
        }
    }

    // For when the user adds/updates a monster
    public void updateMonster(MonsterAttributes monster)
    {
        if (this.monsters == null)
        {
            this.monsters = new ArrayList<>();
        }
        // This loop updates the monster if it's already there
        for (int i = 0; i < monsters.size(); i++)
        {
            if (monsters.get(i).getName().equals(monster.getName()))
            {
                monsters.set(i, monster);
                return;
            }
        }
        // If we made it out of the loop without returning, it's a new monster
        monsters.add(monster);
        monsterNames.add(monster.getName());
    }

    public void deleteMonster(String monsterName)
    {
        monsterNames.remove(monsterName);
        // This loop updates the monster if it's already there
        for (int i = 0; i < monsters.size(); i++)
        {
            if (monsters.get(i).getName().equals(monsterName))
            {
                monsters.remove(i);
                break;
            }
        }
    }

    public List<MonsterAttributes> getMonsterList()
    {
        return monsters;
    }
}