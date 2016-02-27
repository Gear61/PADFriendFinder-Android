package com.randomappsinc.padfriendfinder.Utils;

/**
 * Created by alexanderchiou on 7/13/15.
 */

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.Monster;

/**
 * Created by Alex on 10/26/2014.
 */
public class MonsterSearchUtils {
    public static String createMonsterFormMessage(int level, int numAwakenings, int skillLevel, int numPlusEgges, Monster monster) {
        if (level <= 0) {
            return "A monster's level has to be at least 1!";
        }
        if (level > monster.getLevel()) {
            return "This monster cannot achieve a level higher than " + String.valueOf(monster.getLevel()) + "!";
        }
        if (numAwakenings > monster.getAwakenings()) {
            return "This monster cannot be awakened more than " + String.valueOf(monster.getAwakenings()) + " times!";
        }
        if (skillLevel <= 0) {
            return "A monster's skill level has to be at least 1!";
        }
        if (skillLevel > monster.getSkillLevel()) {
            return "This monster cannot achieve a skill level higher than " + String.valueOf(monster.getSkillLevel()) + "!";
        }
        if (numPlusEgges > Constants.MAX_PLUS_EGGS) {
            return "You cannot give more than " + String.valueOf(Constants.MAX_PLUS_EGGS) + " plus eggs to a monster!";
        }
        return "";
    }
}


