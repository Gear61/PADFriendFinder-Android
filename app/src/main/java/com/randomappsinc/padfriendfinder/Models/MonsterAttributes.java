package com.randomappsinc.padfriendfinder.Models;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class MonsterAttributes
{
    private int level;
    private int skillLevel;
    private int awakenings;
    private int plusEggs;
    private int drawableId;

    // For hard-coded hashmap
    public MonsterAttributes(int level, int skillLevel, int awakenings, int drawableId)
    {
        setWithoutPlusEggs(level, skillLevel, awakenings, drawableId);
    }

    // For monsters from DB
    public MonsterAttributes(int level, int skillLevel, int awakenings, int plusEggs, int drawableId)
    {
        setWithoutPlusEggs(level, skillLevel, awakenings, drawableId);
        this.plusEggs = plusEggs;
    }

    private void setWithoutPlusEggs(int level, int skillLevel, int awakenings, int drawableID)
    {
        this.level = level;
        this.skillLevel = skillLevel;
        this.awakenings = awakenings;
        this.drawableId = drawableID;
    }

    public int getLevel()
    {
        return level;
    }

    public int getSkillLevel()
    {
        return skillLevel;
    }

    public int getAwakenings()
    {
        return awakenings;
    }

    public int getDrawableId()
    {
        return drawableId;
    }

    public int getPlusEggs()
    {
        return plusEggs;
    }
}

