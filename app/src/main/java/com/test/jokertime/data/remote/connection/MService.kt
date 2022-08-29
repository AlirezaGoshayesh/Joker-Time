package com.test.jokertime.data.remote.connection

import com.test.jokertime.data.model.JokeModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MService {

    /**
     * get random programming joke.
     */
    @GET("joke/{category}")
    suspend fun getRandomJoke(
        @Path("category") category: String,
        @Query("type") type: String = "single"
    ): JokeModel

}