package com.test.jokertime.data.remote.connection

import com.test.jokertime.data.model.JokeModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MService {

    /**
     * get random programming joke.
     */
    @GET("joke/{categories}")
    suspend fun getRandomJoke(
        @Path("categories") categories: String,
        @Query("type") type: String = "single"
    ): JokeModel

}