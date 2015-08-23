package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by alexanderchiou on 7/24/15.
 */
public class FetchFriends extends AsyncTask<String, Integer, Long>
{
    private Context context;
    private RestCallResponse restCallResponse;
    private MonsterAttributes monster;

    public FetchFriends(Context context, MonsterAttributes monster)
    {
        this.context = context;
        this.restCallResponse = new RestCallResponse();
        this.monster = monster;
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + Constants.FETCH_FRIENDS_KEY;
        try
        {
            HttpPost fetchFriendsRequest = new HttpPost(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            String body = JSONBuilder.fetchFriendsBuilder(monster);
            fetchFriendsRequest.setEntity(new StringEntity(body, "UTF8"));
            HttpResponse response = httpclient.execute(fetchFriendsRequest);

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
        intent.setAction(Constants.FETCH_FRIENDS_KEY);
        intent.putExtra(Constants.REST_CALL_RESPONSE_KEY, restCallResponse);
        context.sendBroadcast(intent);
    }
}
