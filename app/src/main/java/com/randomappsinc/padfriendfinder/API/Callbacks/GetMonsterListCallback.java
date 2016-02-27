package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Events.BasicResponseEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.Activities.MainActivity;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class GetMonsterListCallback implements Callback<List<MonsterAttributes>> {
    @Override
    public void onResponse(Call<List<MonsterAttributes>> call, Response<List<MonsterAttributes>> response) {
        if (response.code() == ApiConstants.STATUS_OK) {
            MonsterServer.getMonsterServer().setUpMonsterMap(response.body());
            EventBus.getDefault().post(new BasicResponseEvent(Constants.GET_MONSTERS_KEY, ApiConstants.STATUS_OK));
        }
    }

    @Override
    public void onFailure(Call<List<MonsterAttributes>> call, Throwable t) {
        EventBus.getDefault().post(new SnackbarEvent(MainActivity.LOG_TAG,
                MyApplication.getAppContext().getString(R.string.monster_list_fail)));
    }
}
