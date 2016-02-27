package com.randomappsinc.padfriendfinder.API.Events;

import com.randomappsinc.padfriendfinder.Models.TopLeader;

import java.util.List;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class TopLeadersEvent {
    private List<TopLeader> topLeaderList;

    public TopLeadersEvent(List<TopLeader> topLeaderList) {

        this.topLeaderList = topLeaderList;
    }

    public List<TopLeader> getTopLeaderList() {
        return topLeaderList;
    }
}
