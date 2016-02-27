package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Events.MonsterBoxEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.Activities.OthersBoxActivity;
import com.randomappsinc.padfriendfinder.Fragments.MonsterBoxFragment;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class GetMonsterBoxCallback implements Callback<List<Monster>> {
    private boolean othersBox;

    public GetMonsterBoxCallback(boolean othersBox) {
        this.othersBox = othersBox;
    }

    @Override
    public void onResponse(Call<List<Monster>> call, Response<List<Monster>> response) {
        if (response.code() == ApiConstants.STATUS_OK) {
            EventBus.getDefault().post(new MonsterBoxEvent(othersBox, response.body()));
        }
        else {
            broadcastErrorMessage();
        }
    }

    @Override
    public void onFailure(Call<List<Monster>> call, Throwable t) {
        broadcastErrorMessage();
    }

    public void broadcastErrorMessage() {
        if (othersBox) {
            EventBus.getDefault().post(new SnackbarEvent(OthersBoxActivity.LOG_TAG,
                    MyApplication.getAppContext().getString(R.string.other_box_fail)));
        }
        else {
            EventBus.getDefault().post(new SnackbarEvent(MonsterBoxFragment.LOG_TAG,
                    MyApplication.getAppContext().getString(R.string.box_load_fail)));
        }
    }
}
