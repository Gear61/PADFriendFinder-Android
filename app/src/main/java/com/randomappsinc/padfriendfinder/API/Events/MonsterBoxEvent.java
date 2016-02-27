package com.randomappsinc.padfriendfinder.API.Events;

import com.randomappsinc.padfriendfinder.Models.Monster;

import java.util.List;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class MonsterBoxEvent {
    private boolean othersBox;
    private List<Monster> monsterList;

    public MonsterBoxEvent(boolean othersBox, List<Monster> monsterList) {
        this.othersBox = othersBox;
        this.monsterList = monsterList;
    }

    public boolean isOthersBox() {
        return othersBox;
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }
}
