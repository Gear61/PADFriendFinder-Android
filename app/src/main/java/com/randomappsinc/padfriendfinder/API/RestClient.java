package com.randomappsinc.padfriendfinder.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class RestClient {
    private static RestClient instance;
    private PFFService pffService;

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pffService = retrofit.create(PFFService.class);
    }

    public PFFService getPffService() {
        return pffService;
    }
}
