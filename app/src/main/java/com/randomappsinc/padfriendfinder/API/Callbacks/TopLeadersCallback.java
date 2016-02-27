package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Events.TopLeadersEvent;
import com.randomappsinc.padfriendfinder.Activities.TopLeadersActivity;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.TopLeader;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class TopLeadersCallback extends StandardCallback<List<TopLeader>> {
    public TopLeadersCallback() {
        super(TopLeadersActivity.LOG_TAG, MyApplication.getAppContext().getString(R.string.top_leaders_fail));
    }

    @Override
    public void onResponse(Call<List<TopLeader>> call, Response<List<TopLeader>> response) {
        if (response.code() == ApiConstants.STATUS_OK) {
            EventBus.getDefault().post(new TopLeadersEvent(response.body()));
        }
        else {
            super.onResponse(call, response);
        }
    }
}
