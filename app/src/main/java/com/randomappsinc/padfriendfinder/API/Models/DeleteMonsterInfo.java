package com.randomappsinc.padfriendfinder.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class DeleteMonsterInfo {
    @SerializedName("pad_ID")
    @Expose
    private String padId;

    @SerializedName("name")
    @Expose
    private String monsterName;

    public DeleteMonsterInfo(String monsterName) {
        this.padId = PreferencesManager.get().getPadId();
        this.monsterName = monsterName;
    }
}
