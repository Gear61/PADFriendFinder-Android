package com.randomappsinc.padfriendfinder.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.Monster;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class FriendRequest {
    @SerializedName("pad_ID")
    @Expose
    private String padId;

    @SerializedName("monster")
    @Expose
    private Monster monster;

    public FriendRequest(Monster monster) {
        this.padId = PreferencesManager.get().getPadId();
        this.monster = monster;
    }
}
