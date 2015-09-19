package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by jman0_000 on 9/18/2015.
 */
public class ChangeID extends AsyncTask<String, Integer, Long> {

    private Context context;
    private int statusCode;
    private String padID;

    public ChangeID(Context context, String padID) {
        this.context = context;
        this.padID = padID;
    }

    @Override
    protected Long doInBackground(String... strings) {
        String endpoint = Constants.SERVER_URL + Constants.CHECK_ID_KEY + "/" +
                PreferencesManager.get().getPadId() + "/" + padID;
        try {
            HttpGet checkIDRequest = new HttpGet(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(checkIDRequest);
            statusCode = response.getStatusLine().getStatusCode();
        }
        catch(IOException e) {
            return (long) -1;
        }
        return null;
    }

    @Override
    public void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Intent intent = new Intent();
        if (statusCode == 200)
            PreferencesManager.get().setPadId(padID);

        intent.setAction(Constants.ID_CHECKED_KEY);
        intent.putExtra(Constants.ID_STATUS_KEY, statusCode);
        context.sendBroadcast(intent);
    }
}