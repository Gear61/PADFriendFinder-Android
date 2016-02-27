package com.randomappsinc.padfriendfinder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderchiou on 7/20/15.
 */
public class Friend {
    @SerializedName("pad_ID")
    @Expose
    private String padId;

    @SerializedName("level")
    @Expose
    private int level;

    @SerializedName("skill_level")
    @Expose
    private int skillLevel;

    @SerializedName("awakenings")
    @Expose
    private int awakenings;

    @SerializedName("plus_eggs")
    @Expose
    private int plusEggs;

    public String getPadId()
    {
        return padId;
    }

    public int getLevel() {
        return level;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getAwakenings() {
        return awakenings;
    }

    public int getPlusEggs() {
        return plusEggs;
    }
}
