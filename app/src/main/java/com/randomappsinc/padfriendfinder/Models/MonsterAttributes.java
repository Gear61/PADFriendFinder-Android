package com.randomappsinc.padfriendfinder.Models;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class MonsterAttributes
{
    private int maxLevel;
    private int maxSkill;
    private int maxAwakenings;
    private int drawableID;

    public MonsterAttributes(int maxLevel, int maxSkill, int numAwakenings, int drawableID)
    {
        this.maxLevel = maxLevel;
        this.maxSkill = maxSkill;
        this.maxAwakenings = numAwakenings;
        this.drawableID = drawableID;

    }

    public int getMaxLevel()
    {
        return maxLevel;
    }

    public int getMaxSkill()
    {
        return maxSkill;
    }

    public int getMaxAwakenings()
    {
        return maxAwakenings;
    }

    public int getDrawableID()
    {
        return drawableID;
    }
}

