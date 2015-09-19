package com.randomappsinc.padfriendfinder.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexanderchiou on 7/13/15.
 */

public class MonsterAttributes implements Parcelable, Comparable<MonsterAttributes>
{
    private String name;
    private int level;
    private int skillLevel;
    private int awakenings;
    private int plusEggs;
    private int monsterId;
    private String imageUrl;

    // Default constructor
    public MonsterAttributes()
    {
        this.name = "";
    }

    // For hard-coded hashmap
    public MonsterAttributes(int level, int skillLevel, int awakenings)
    {
        this.level = level;
        this.skillLevel = skillLevel;
        this.awakenings = awakenings;
    }

    public void setImageUrl(int monsterId) {
        this.monsterId = monsterId;
        this.imageUrl = "http://www.puzzledragonx.com/en/img/book/" + String.valueOf(monsterId) + ".png";
    }

    public void setImageUrl(String imageUrl) {
        String monsterId = imageUrl.replace("http://www.puzzledragonx.com/en/img/book/", "").replace(".png", "");
        this.monsterId = Integer.valueOf(monsterId);
        this.imageUrl = imageUrl;
    }

    // For when the user searches for monsters/updates their box
    public MonsterAttributes(String name, int level, int awakenings, int plusEggs, int skillLevel)
    {
        this.name = name;
        this.level = level;
        this.awakenings = awakenings;
        this.plusEggs = plusEggs;
        this.skillLevel = skillLevel;
    }

    // Getters
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPlusEggs()
    {
        return plusEggs;
    }

    public String getName()
    {
        return name;
    }

    public int getMonsterId()
    {
        return monsterId;
    }

    // Setters
    public void setName(String name)
    {
        this.name = name;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setSkillLevel(int skillLevel)
    {
        this.skillLevel = skillLevel;
    }

    public void setAwakenings(int awakenings)
    {
        this.awakenings = awakenings;
    }

    public void setPlusEggs(int plusEggs)
    {
        this.plusEggs = plusEggs;
    }

    protected MonsterAttributes(Parcel in)
    {
        name = in.readString();
        level = in.readInt();
        skillLevel = in.readInt();
        awakenings = in.readInt();
        plusEggs = in.readInt();
        imageUrl = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeInt(skillLevel);
        dest.writeInt(awakenings);
        dest.writeInt(plusEggs);
        dest.writeString(imageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MonsterAttributes> CREATOR = new Parcelable.Creator<MonsterAttributes>()
    {
        @Override
        public MonsterAttributes createFromParcel(Parcel in)
        {
            return new MonsterAttributes(in);
        }

        @Override
        public MonsterAttributes[] newArray(int size)
        {
            return new MonsterAttributes[size];
        }
    };

    @Override
    public int compareTo(MonsterAttributes another)
    {
        return another.monsterId - this.monsterId;
    }
}

