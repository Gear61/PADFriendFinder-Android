package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by alexanderchiou on 7/24/15.
 */
public class UpdateMonster extends AsyncTask<String, Integer, Long>
{
    private Context context;
    private RestCallResponse restCallResponse;
    private Monster monster;

    public UpdateMonster(Context context, Monster monster)
    {
        this.context = context;
        this.restCallResponse = new RestCallResponse();
        this.monster = monster;
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + Constants.UPDATE_KEY;
        try
        {
            HttpPost updateMonsterRequest = new HttpPost(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            String body = JSONBuilder.updateMonsterBuilder(monster);
            updateMonsterRequest.setEntity(new StringEntity(body, "UTF8"));
            HttpResponse response = httpclient.execute(updateMonsterRequest);

            // StatusLine stat = response.getStatusLine();
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
        intent.setAction(Constants.MONSTER_UPDATE_KEY);
        intent.putExtra(Constants.REST_CALL_RESPONSE_KEY, restCallResponse);
        intent.putExtra(Constants.MONSTER_KEY, monster);
        context.sendBroadcast(intent);
    }
}
