package com.randomappsinc.padfriendfinder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jman0_000 on 10/1/2015.
 */
public class TopLeader {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("count")
    @Expose
    private int leaderCount;

    public String getName() {
        return name;
    }

    public int getLeaderCount() {
        return leaderCount;
    }
}
