package com.randomappsinc.padfriendfinder.Utils;

/**
 * Created by alexanderchiou on 7/13/15.
 */

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;

/**
 * Created by Alex on 10/26/2014.
 */
public class MonsterSearchUtils
{
    public static String createMonsterFormMessage(int level, int numAwakenings, int skillLevel, int numPlusEgges, MonsterAttributes monsterAttributes)
    {
        if (level <= 0)
        {
            return "A monster's level has to be at least 1!";
        }
        if (level > monsterAttributes.getLevel())
        {
            return "This monster cannot achieve a level higher than " + String.valueOf(monsterAttributes.getLevel()) + "!";
        }
        if (numAwakenings > monsterAttributes.getAwakenings())
        {
            return "This monster cannot be awakened more than " + String.valueOf(monsterAttributes.getAwakenings()) + " times!";
        }
        if (skillLevel <= 0)
        {
            return "A monster's skill level has to be at least 1!";
        }
        if (skillLevel > monsterAttributes.getSkillLevel())
        {
            return "This monster cannot achieve a skill level higher than " + String.valueOf(monsterAttributes.getSkillLevel()) + "!";
        }
        if (numPlusEgges > Constants.MAX_PLUS_EGGS)
        {
            return "You cannot give more than " + String.valueOf(Constants.MAX_PLUS_EGGS) + " plus eggs to a creature!";
        }
        return "";
    }
}


