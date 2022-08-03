package com.test.jokertime.domain

import com.test.jokertime.data.model.JokeModel
import com.test.jokertime.domain.base.UseCase
import com.test.jokertime.domain.exceptions.IErrorHandler
import javax.inject.Inject

class GetRandomJoke @Inject constructor(
    private val repository: Repository,
    errorHandler: IErrorHandler
) : UseCase<Unit, JokeModel>(errorHandler) {
    override suspend fun execute(parameters: Unit): JokeModel {
        return repository.getRandomJoke()
    }
}