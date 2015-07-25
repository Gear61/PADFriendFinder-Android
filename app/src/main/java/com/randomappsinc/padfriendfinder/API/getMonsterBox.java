package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by alexanderchiou on 7/23/15.
 */
public class GetMonsterBox extends AsyncTask<String, Integer, Long>
{
    private Context context;
    private RestCallResponse restCallResponse;

    public GetMonsterBox(Context context)
    {
        this.context = context;
        restCallResponse = new RestCallResponse();
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + PreferencesManager.get().getPadId();
        try
        {
            HttpGet getMonsterBoxRequest = new HttpGet(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(getMonsterBoxRequest);

            // StatusLine stat = response.getStatusLine();
            restCallResponse.setStatusCode(response.getStatusLine().getStatusCode());

            if (restCallResponse.getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                restCallResponse.setResponse(EntityUtils.toString(entity));
            }
        }
        catch(IOException e)
        {
            return (long) -1;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long aLong)
    {
        super.onPostExecute(aLong);
        Intent intent = new Intent();
        intent.setAction(Constants.MONSTER_BOX_KEY);
        intent.putExtra(Constants.REST_CALL_RESPONSE_KEY, restCallResponse);
        context.sendBroadcast(intent);
    }
}
