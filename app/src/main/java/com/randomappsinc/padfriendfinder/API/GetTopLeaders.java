package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.os.AsyncTask;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by jman0_000 on 9/30/2015.
 */
public class GetTopLeaders extends AsyncTask<String, Integer, Long> {

    private Context context;
    private RestCallResponse restCallResponse;

    public GetTopLeaders(Context context) {
        this.restCallResponse = new RestCallResponse();
        this.context = context;
    }

    @Override
    protected Long doInBackground(String... strings) {
        String endpoint = Constants.SERVER_URL + Constants.TOP_LEADERS_KEY;
        try {
            HttpGet topLeadersRequest = new HttpGet(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(topLeadersRequest);
            restCallResponse.setStatusCode(response.getStatusLine().getStatusCode());

            if (restCallResponse.getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                restCallResponse.setResponse(EntityUtils.toString(entity));
            }
        }
        catch(IOException e) {
            return (long) -1;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        EventBus.getDefault().post(restCallResponse);
    }
}
