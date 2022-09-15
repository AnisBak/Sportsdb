package com.anis.sportsdb.repositories


import com.anis.sportsdb.api.SportsdbApi
import com.anis.sportsdb.model.Player
import com.anis.sportsdb.api.PlayerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class SportdbRepository {
    private val sportsdbApi: SportsdbApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PlayerInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
        sportsdbApi = retrofit.create()
    }



    suspend fun searchPhotos(query: String): List<Player>? =
        sportsdbApi.searchPhotos(query).player

}
