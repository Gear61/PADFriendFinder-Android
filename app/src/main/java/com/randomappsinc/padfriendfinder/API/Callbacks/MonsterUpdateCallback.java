package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Events.MonsterUpdateEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.Models.IgnoredResponse;
import com.randomappsinc.padfriendfinder.Activities.MonsterFormActivity;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.R;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class MonsterUpdateCallback implements Callback<IgnoredResponse>{
    private String mode;
    private Monster monster;

    public MonsterUpdateCallback(String mode, Monster monster) {
        this.mode = mode;
        this.monster = monster;
    }

    @Override
    public void onResponse(Call<IgnoredResponse> call, Response<IgnoredResponse> response) {
        if (response.code() == ApiConstants.STATUS_OK) {
            EventBus.getDefault().post(new MonsterUpdateEvent(mode, monster));
        }
        else {
            broadcastErrorMessage();
        }
    }

    @Override
    public void onFailure(Call<IgnoredResponse> call, Throwable t) {
        broadcastErrorMessage();
    }

    private void broadcastErrorMessage() {
        String errorMessage = mode.equals(Constants.ADD_MODE)
                ? MyApplication.getAppContext().getString(R.string.add_monster_fail)
                : MyApplication.getAppContext().getString(R.string.update_monster_fail);
        EventBus.getDefault().post(new SnackbarEvent(MonsterFormActivity.LOG_TAG, errorMessage));
    }
}
