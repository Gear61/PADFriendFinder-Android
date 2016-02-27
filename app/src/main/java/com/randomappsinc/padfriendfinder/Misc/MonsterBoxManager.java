package com.randomappsinc.padfriendfinder.Misc;

import com.randomappsinc.padfriendfinder.Models.Monster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class MonsterBoxManager {
    private static MonsterBoxManager instance;

    private List<Monster> monsters;
    private Set<String> monsterNames;

    private MonsterBoxManager() {
        monsterNames = new HashSet<>();
    }

    public static MonsterBoxManager getInstance() {
        if (instance == null) {
            instance = new MonsterBoxManager();
        }
        return instance;
    }

    public boolean alreadyContainsMonster(String monsterName) {
        return monsterNames.contains(monsterName);
    }

    public void addMonsters (List<Monster> monsters) {
        if (this.monsters == null) {
            this.monsters = new ArrayList<>();
        }
        for (Monster monster : monsters) {
            this.monsters.add(monster);
            this.monsterNames.add(monster.getName());
        }
    }

    // For when the user adds/updates a monster
    public void updateMonster(Monster monster) {
        if (this.monsters == null) {
            this.monsters = new ArrayList<>();
        }
        // This loop updates the monster if it's already there
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getName().equals(monster.getName())) {
                monsters.set(i, monster);
                return;
            }
        }
        // If we made it out of the loop without returning, it's a new monster
        monsters.add(monster);
        monsterNames.add(monster.getName());
    }

    public void deleteMonster(String monsterName) {
        monsterNames.remove(monsterName);
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getName().equals(monsterName)) {
                monsters.remove(i);
                break;
            }
        }
    }
}