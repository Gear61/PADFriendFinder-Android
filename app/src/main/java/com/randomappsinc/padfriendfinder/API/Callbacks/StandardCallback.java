package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class StandardCallback<T> implements Callback<T> {
    protected String screen;
    protected String errorMessage;

    public StandardCallback (String screen, String errorMessage) {
        this.screen = screen;
        this.errorMessage = errorMessage;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() != ApiConstants.STATUS_OK) {
            EventBus.getDefault().post(new SnackbarEvent(screen, errorMessage));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        EventBus.getDefault().post(new SnackbarEvent(screen, errorMessage));
    }
}
