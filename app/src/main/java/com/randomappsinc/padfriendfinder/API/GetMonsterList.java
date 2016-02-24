package com.randomappsinc.padfriendfinder.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by jman0_000 on 8/27/2015.
 */
public class GetMonsterList extends AsyncTask<String, Integer, Long>
{
    private int statusCode;
    private MaterialDialog progressDialog;

    public GetMonsterList(MaterialDialog progressDialog)
    {
        this.progressDialog = progressDialog;
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + Constants.GET_MONSTERS_KEY;
        try
        {
            HttpGet getMonsterBoxRequest = new HttpGet(endpoint);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(getMonsterBoxRequest);

            // StatusLine stat = response.getStatusLine();
            statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                JSONParser.parseMonsterListResponse(EntityUtils.toString(entity));
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
        progressDialog.dismiss();
        Context context = MyApplication.getAppContext();
        if (statusCode == 200)
        {
            Toast.makeText(context, "Monster list loaded.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Constants.GET_MONSTERS_KEY);
            context.sendBroadcast(intent);
        }
        else
        {
            Toast.makeText(context, "Failed to load monster list. Try using the app later.", Toast.LENGTH_LONG).show();
        }
    }
}
