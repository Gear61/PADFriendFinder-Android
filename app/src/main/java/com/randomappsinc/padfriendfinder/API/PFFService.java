package com.randomappsinc.padfriendfinder.API;

import com.randomappsinc.padfriendfinder.API.Models.DeleteMonsterInfo;
import com.randomappsinc.padfriendfinder.API.Models.FriendRequest;
import com.randomappsinc.padfriendfinder.API.Models.IgnoredResponse;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.Models.TopLeader;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public interface PFFService {
    @GET("/changeID/{oldId}/{newId}")
    Call<IgnoredResponse> changePadId(@Path("oldId") String oldId, @Path("newId") String newId);

    @GET("/topLeaders")
    Call<List<TopLeader>> getTopLeaders();

    @GET("/getMonsters")
    Call<List<Monster>> getMonsterList();

    @GET("/monsters/{padId}")
    Call<List<Monster>> getMonsterBox(@Path("padId") String padId);

    @POST("/monsters/delete")
    Call<IgnoredResponse> deleteMonster(@Body DeleteMonsterInfo deleteMonsterInfo);

    @POST("/fetch")
    Call<List<Friend>> findFriends(@Body FriendRequest friendRequest);
}
