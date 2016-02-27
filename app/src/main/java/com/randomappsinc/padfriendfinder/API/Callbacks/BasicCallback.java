package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Events.BasicResponseEvent;
import com.randomappsinc.padfriendfinder.API.Models.IgnoredResponse;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class BasicCallback implements Callback<IgnoredResponse> {
    private String eventType;

    public BasicCallback(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public void onResponse(Call<IgnoredResponse> call, Response<IgnoredResponse> response) {
        EventBus.getDefault().post(new BasicResponseEvent(eventType, response.code()));
    }

    @Override
    public void onFailure(Call<IgnoredResponse> call, Throwable t) {
        EventBus.getDefault().post(new BasicResponseEvent(eventType, ApiConstants.SERVER_ERROR));
    }
}
