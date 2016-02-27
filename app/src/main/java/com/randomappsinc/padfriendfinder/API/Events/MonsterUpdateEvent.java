package com.randomappsinc.padfriendfinder.API.Events;

import com.randomappsinc.padfriendfinder.Models.Monster;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class MonsterUpdateEvent {
    private String mode;
    private Monster monster;

    public MonsterUpdateEvent(String mode, Monster monster) {
        this.mode = mode;
        this.monster = monster;
    }

    public String getMode() {
        return mode;
    }

    public Monster getMonster() {
        return monster;
    }
}
