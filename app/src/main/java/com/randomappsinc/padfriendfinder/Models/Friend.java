package com.randomappsinc.padfriendfinder.Models;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class Friend {
    private String padId;
    private MonsterAttributes monster;

    public Friend(String padId, MonsterAttributes monster) {
        this.padId = padId;
        this.monster = monster;
    }

    public String getPadId()
    {
        return padId;
    }

    public MonsterAttributes getMonster()
    {
        return monster;
    }
}
