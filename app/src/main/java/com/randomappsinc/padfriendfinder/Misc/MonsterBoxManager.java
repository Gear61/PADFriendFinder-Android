package com.randomappsinc.padfriendfinder.Misc;

import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class MonsterBoxManager
{
    private static MonsterBoxManager instance = null;
    // If this is null, this means that we need to make the API call to fetch their box
    private static List<MonsterAttributes> monsters;

    private MonsterBoxManager()
    {
        addMonsters(Stubber.getMonsterBox());
    }

    public static MonsterBoxManager getInstance()
    {
        if (instance == null)
        {
            instance = new MonsterBoxManager();
        }
        return instance;
    }

    public void addMonsters (List<MonsterAttributes> monsters)
    {
        if (this.monsters == null)
        {
            this.monsters = new ArrayList<>();
        }
        this.monsters.addAll(monsters);
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
    }

    public List<MonsterAttributes> getMonsterList()
    {
        return monsters;
    }
}