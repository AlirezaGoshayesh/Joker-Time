package com.test.jokertime.data.remote.connection

import com.test.jokertime.data.model.JokeModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MService {

    /**
     * get random programming joke.
     */
    @GET("joke/Programming")
    suspend fun getRandomJoke(
        @Query("type") type: String = "single"
    ): JokeModel

}