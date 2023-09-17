package com.example.apprf.data.remote

import com.example.apprf.data.remote.model.GameDetailDto
import com.example.apprf.data.remote.model.GameDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GamesApi {
    @GET
    fun getGames(
        @Url url: String?
    ): Call<List<GameDto>>

/*
    @GET("cm/games/game_detail.php")
    fun getGameDetail(
        @Query("id") id: String?
    ): Call<GameDetailDto>
   // getGameDetail("21347")*/

    //Para Apiari
    /*@GET("games/games_list")
    fun getGamesApiary(): Call<List<GameDto>>*/

    //***********************************
    @GET("products/products_list")
    fun getGamesApiary(): Call<List<GameDto>>
    //**********************************


    //games/game_detail/21357
    /*@GET("games/game_detail/{id}")
    fun getGameDetailApiary(
        @Path("id")id: String?
    ): Call<GameDetailDto>*/


    //*************************
    @GET("products/products_detail/{id}")
    fun getGameDetailApiary(
        @Path("id")id: String?
    ): Call<GameDetailDto>
    //*************************


}