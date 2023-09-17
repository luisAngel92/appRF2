package com.example.apprf.data

import com.example.apprf.data.remote.GamesApi
import com.example.apprf.data.remote.model.GameDetailDto
import com.example.apprf.data.remote.model.GameDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create

class GameRepository(private val retrofit: Retrofit) {

    private val gamesApi: GamesApi = retrofit.create(GamesApi::class.java)

    fun getGames(url: String): Call<List<GameDto>> =
        gamesApi.getGames(url)


    fun getGameDetail(id: String?): Call<GameDetailDto> =
        gamesApi.getGameDetailApiary(id)

    fun getGamesApiary(): Call<List<GameDto>> =
        gamesApi.getGamesApiary()


    fun getGameDetailApiary(id: String?): Call<GameDetailDto> =
        gamesApi.getGameDetailApiary(id)
}