package com.randomappsinc.padfriendfinder.Models;

/**
 * Created by alexanderchiou on 7/13/15.
 */
public class MonsterAttributes
{
    private String monsterName;
    private int level;
    private int skillLevel;
    private int awakenings;
    private int plusEggs;
    private int drawableId;

    // For hard-coded hashmap
    public MonsterAttributes(int level, int skillLevel, int awakenings, int drawableId)
    {
        this.level = level;
        this.skillLevel = skillLevel;
        this.awakenings = awakenings;
        this.drawableId = drawableId;
    }

    // For when the user searches for monsters/updates their box
    public MonsterAttributes(String name, int level, int skillLevel, int awakenings, int plusEggs)
    {
        this.plusEggs = plusEggs;
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

