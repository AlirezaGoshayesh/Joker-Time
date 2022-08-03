package com.test.jokertime.data

import com.test.jokertime.data.model.JokeModel
import com.test.jokertime.data.remote.RemoteDataSource
import com.test.jokertime.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override suspend fun getRandomJoke(): JokeModel = remoteDataSource.getRandomJoke()

}