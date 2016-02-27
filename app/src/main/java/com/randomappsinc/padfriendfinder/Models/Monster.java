package com.randomappsinc.padfriendfinder.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;

/**
 * Created by alexanderchiou on 7/13/15.
 */

public class Monster implements Parcelable, Comparable<Monster> {
    @SerializedName("name")
    @Expose
    private String name;

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

    @SerializedName("monsterId")
    @Expose
    private int monsterId;

    private String imageUrl;

    // Default constructor
    public Monster() {
        this.name = "";
    }

    // For hard-coded hashmap
    public Monster(int level, int skillLevel, int awakenings) {
        this.level = level;
        this.skillLevel = skillLevel;
        this.awakenings = awakenings;
    }

    public void setImageUrl(int monsterId) {
        this.monsterId = monsterId;
        this.imageUrl = "http://www.puzzledragonx.com/en/img/book/" + String.valueOf(monsterId) + ".png";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // For when the user searches for monsters/updates their box
    public Monster(String name, int level, int awakenings, int plusEggs, int skillLevel) {
        this.name = name;
        this.level = level;
        this.awakenings = awakenings;
        this.plusEggs = plusEggs;
        this.skillLevel = skillLevel;
    }

    // Getters
    public int getLevel() {
        return level;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getAwakenings() {
        return awakenings;
    }

    public String getImageUrl() {
        if (imageUrl == null) {
            if (monsterId != 0) {
                imageUrl = "http://www.puzzledragonx.com/en/img/book/" + String.valueOf(monsterId) + ".png";
            }
            else {
                imageUrl = MonsterServer.getMonsterServer().getImageUrl(name);
            }
        }
        return imageUrl;
    }

    public int getPlusEggs() {
        return plusEggs;
    }

    public String getName() {
        return name;
    }

    public int getMonsterId() {
        if (monsterId == 0) {
            String idString = imageUrl.replace("http://www.puzzledragonx.com/en/img/book/", "").replace(".png", "");
            return Integer.valueOf(idString);
        }
        return monsterId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public void setAwakenings(int awakenings) {
        this.awakenings = awakenings;
    }

    public void setPlusEggs(int plusEggs) {
        this.plusEggs = plusEggs;
    }

    protected Monster(Parcel in) {
        name = in.readString();
        level = in.readInt();
        skillLevel = in.readInt();
        awakenings = in.readInt();
        plusEggs = in.readInt();
        imageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeInt(skillLevel);
        dest.writeInt(awakenings);
        dest.writeInt(plusEggs);
        dest.writeString(imageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Monster> CREATOR = new Parcelable.Creator<Monster>() {
        @Override
        public Monster createFromParcel(Parcel in)
        {
            return new Monster(in);
        }

        @Override
        public Monster[] newArray(int size)
        {
            return new Monster[size];
        }
    };

    @Override
    public int compareTo(@NonNull Monster another) {
        return another.monsterId - this.monsterId;
    }
}

