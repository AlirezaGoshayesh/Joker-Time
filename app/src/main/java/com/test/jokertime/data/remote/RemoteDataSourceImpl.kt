package com.test.jokertime.data.remote

import com.test.jokertime.data.model.JokeModel
import com.test.jokertime.data.remote.connection.MService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val service: MService) :
    RemoteDataSource {
    override suspend fun getRandomJoke(category: String): JokeModel = service.getRandomJoke(category)

}