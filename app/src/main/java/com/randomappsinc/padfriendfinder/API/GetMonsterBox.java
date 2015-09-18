package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by alexanderchiou on 7/23/15.
 */
public class GetMonsterBox extends AsyncTask<String, Integer, Long>
{
    private Context context;
    private RestCallResponse restCallResponse;
    private String pad_id;
    private boolean OthersBox;

    public GetMonsterBox(Context context, String pad_id, boolean OthersBox)
    {
        this.context = context;
        this.restCallResponse = new RestCallResponse();
        this.pad_id = pad_id;
        this.OthersBox = OthersBox;
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + Constants.MONSTERS_KEY + pad_id;
        try
        {
            HttpGet getMonsterBoxRequest = new HttpGet(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(getMonsterBoxRequest);

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
        if (!OthersBox)
            intent.setAction(Constants.MONSTER_BOX_KEY);
        else
            intent.setAction(Constants.OTHER_BOX_KEY);
        intent.putExtra(Constants.REST_CALL_RESPONSE_KEY, restCallResponse);
        context.sendBroadcast(intent);
    }
}
