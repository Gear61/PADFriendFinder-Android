package com.randomappsinc.padfriendfinder.Models;

/**
 * Created by jman0_000 on 10/1/2015.
 */
public class TopLeader {

    String name;
    int leaderCount;

    public TopLeader() {
        this.name = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeaderCount(int leaderCount) {
        this.leaderCount = leaderCount;
    }

    public String getName() {
        return name;
    }

    public int getLeaderCount() {
        return leaderCount;
    }
}
