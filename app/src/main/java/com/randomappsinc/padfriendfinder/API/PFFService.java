package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.API.Models.IgnoredResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public interface PFFService {
    @GET("/changeID/{oldId}/{newId}")
    Call<IgnoredResponse> changePadId(@Path("oldId") String oldId, @Path("newId") String newId);
}
