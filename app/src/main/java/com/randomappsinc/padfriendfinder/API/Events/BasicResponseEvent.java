package com.randomappsinc.padfriendfinder.API.Events;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class BasicResponseEvent {
    private String eventType;
    private int responseCode;

    public BasicResponseEvent(String eventType, int responseCode) {
        this.eventType = eventType;
        this.responseCode = responseCode;
    }

    public String getEventType() {
        return eventType;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
