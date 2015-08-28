package com.randomappsinc.padfriendfinder.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.randomappsinc.padfriendfinder.Misc.Constants;

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
public class GetMonsterList extends AsyncTask<String, Integer, Long> {
    private Context context;
    private ProgressDialog progressDialog;
    private int statusCode;

    public GetMonsterList(Context context, ProgressDialog progressDialog)
    {
        this.context = context;
        this.progressDialog = progressDialog;
    }

    @Override
    protected Long doInBackground(String... strings)
    {
        String endpoint = Constants.SERVER_URL + Constants.GET_MONSTERS;
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
        if (statusCode == 200)
            Toast.makeText(context, "Monster list loaded.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Failed to load monster list. Try using the app later.", Toast.LENGTH_LONG).show();
    }
}
