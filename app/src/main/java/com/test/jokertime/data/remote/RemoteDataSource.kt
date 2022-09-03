package com.test.jokertime.data.remote

import com.test.jokertime.data.model.JokeModel


interface RemoteDataSource {

    suspend fun getRandomJoke(categories: String): JokeModel

}