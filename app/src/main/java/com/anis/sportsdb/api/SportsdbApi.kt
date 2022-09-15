package com.anis.sportsdb.api

import retrofit2.http.GET
import retrofit2.http.Query


private const val API_KEY = "50130162"
interface SportsdbApi {

    @GET("api/v1/json/$API_KEY/searchplayers.php")
    suspend fun searchPhotos(@Query("p") query: String): TheSportsDbResponse

}
