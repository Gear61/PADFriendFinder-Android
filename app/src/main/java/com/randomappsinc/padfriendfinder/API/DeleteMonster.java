package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by alexanderchiou on 7/28/15.
 */
public class DeleteMonster extends AsyncTask<String, Integer, Long>
{
    private Context context;
    private RestCallResponse restCallResponse;
    private String monsterName;

    public DeleteMonster(Context context, String monsterName)
    {
        this.context = context;
        this.restCallResponse = new RestCallResponse();
        this.monsterName = monsterName;
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + Constants.MONSTERS_KEY + Constants.DELETE_KEY;
        try
        {
            HttpPost updateMonsterRequest = new HttpPost(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            String body = JSONBuilder.deleteMonsterBuilder(monsterName);
            updateMonsterRequest.setEntity(new StringEntity(body, "UTF8"));
            HttpResponse response = httpclient.execute(updateMonsterRequest);

            restCallResponse.setStatusCode(response.getStatusLine().getStatusCode());
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
        intent.setAction(Constants.DELETE_KEY);
        intent.putExtra(Constants.REST_CALL_RESPONSE_KEY, restCallResponse);
        intent.putExtra(Constants.NAME_KEY, monsterName);
        context.sendBroadcast(intent);
    }
}
